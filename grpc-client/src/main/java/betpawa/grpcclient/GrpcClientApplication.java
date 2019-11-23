package betpawa.grpcclient;

import betpawa.grpcclient.generated.*;
import betpawa.grpcclient.service.UserService;

import betpawa.grpcclient.util.Currency;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootApplication
public class GrpcClientApplication implements ApplicationRunner {
    private static final Logger logger = LoggerFactory.getLogger(GrpcClientApplication.class);
    @Value("${grpc.server.host}")
    private String serverHost;
    @Value("${grpc.server.port}")
    private int serverPort;

    private BalanceServiceGrpc.BalanceServiceBlockingStub balanceServiceBlockingStub;
    private DepositGrpc.DepositBlockingStub depositBlockingStub;
    private WithdrawGrpc.WithdrawBlockingStub withdrawBlockingStub;
    private static ManagedChannel balanceChannel;
    private static ManagedChannel depositChannel;
    private static ManagedChannel withdrawChannel;

    private static ExecutorService executorService;

    public static void main(String[] args) {
        SpringApplication.run(GrpcClientApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        balanceChannel = ManagedChannelBuilder.forAddress(serverHost, serverPort).usePlaintext().build();
        balanceServiceBlockingStub = BalanceServiceGrpc.newBlockingStub(balanceChannel);

        depositChannel = ManagedChannelBuilder.forAddress(serverHost, serverPort).usePlaintext().build();
        depositBlockingStub = DepositGrpc.newBlockingStub(depositChannel);

        withdrawChannel = ManagedChannelBuilder.forAddress(serverHost, serverPort).usePlaintext().build();
        withdrawBlockingStub = WithdrawGrpc.newBlockingStub(withdrawChannel);

        if (args.getNonOptionArgs().size() < 3) {
            logger.error("Usage: <number of users> <number of threads per user> <number of round per thread>");
            System.exit(1);
        }

        int users = 0;
        int concurrentThreadsPerUser = 0;
        int roundsPerThread = 0;
        try {
            users = Integer.parseInt(args.getSourceArgs()[0]);
            concurrentThreadsPerUser = Integer.parseInt(args.getSourceArgs()[1]);
            roundsPerThread = Integer.parseInt(args.getSourceArgs()[2]);
        } catch (NumberFormatException e) {
            logger.error("Error parsing integer arguments", e);
            System.exit(1);
        }

        logger.info("User={}, concurrentThreadsPerUser={}, roundsPerThread={}", users, concurrentThreadsPerUser, roundsPerThread);

        UserService userService = new UserService(serverHost, serverPort);
        List<String> userIds = userService.createUsers(users);

        executorService = Executors.newFixedThreadPool(users + users * concurrentThreadsPerUser);
        final CountDownLatch countDownLatch = new CountDownLatch(users);

        for (String userId : userIds) {
            executorService.submit(new IndividualUserThread(userId, concurrentThreadsPerUser, roundsPerThread, countDownLatch));
        }

        countDownLatch.await();
        executorService.shutdown();

        long startTime = System.currentTimeMillis();

        /* stops the thread for measuring total time */
        while(!executorService.isTerminated()) {}

        long endTime = System.currentTimeMillis() - startTime;

        logger.info("Total time - {}ms", endTime);
    }

    public DepositOuterClass.DepositResponse deposit(String userId,
                                                     double amount,
                                                     String currencyCode) {
        DepositOuterClass.DepositRequest depositRequest = DepositOuterClass.DepositRequest.newBuilder()
                .setAmount(amount)
                .setCcy(currencyCode)
                .setUserId(userId)
                .build();

        return depositBlockingStub.deposit(depositRequest);
    }

    public WithdrawOuterClass.WithdrawalResponse withdraw(String userId,
                                                          double amount,
                                                          String currencyCode) {
        WithdrawOuterClass.WithdrawalRequest withdrawalRequest = WithdrawOuterClass.WithdrawalRequest.newBuilder()
                .setAmount(amount)
                .setCcy(currencyCode)
                .setUserId(userId)
                .build();

        return withdrawBlockingStub.withdraw(withdrawalRequest);
    }

    public BalanceOuterClass.BalanceResponse getBalance(String userId) {
        BalanceOuterClass.BalanceRequest balanceRequest = BalanceOuterClass.BalanceRequest.newBuilder()
                .setUserId(userId)
                .build();

        return balanceServiceBlockingStub.balance(balanceRequest);
    }

    class IndividualUserThread implements Runnable {
        private String userId;
        private int concurrentThreadsPerUser;
        private int roundsPerThread;
        private CountDownLatch countDownLatch;

        public IndividualUserThread(String userId, int concurrentThreadsPerUser, int roundsPerThread, CountDownLatch countDownLatch) {
            this.userId = userId;
            this.concurrentThreadsPerUser = concurrentThreadsPerUser;
            this.roundsPerThread = roundsPerThread;
            this.countDownLatch = countDownLatch;
        }

        public void run() {
            final CountDownLatch roundsCountDown = new CountDownLatch(roundsPerThread);
            while (concurrentThreadsPerUser > 0) {
                //logger.info("Thread - {} for userId - {}", concurrentThreadsPerUser, userId);
                executorService.submit(new ConcurrentSingleUserThread(userId, roundsPerThread, concurrentThreadsPerUser));
                countDownLatch.countDown();
                concurrentThreadsPerUser--;
            }
        }
    }

    class ConcurrentSingleUserThread implements Runnable {
        private String userId;
        private int roundsPerThread;
        private int userThread;

        public ConcurrentSingleUserThread(String userId, int roundsPerThread, int userThread) {
            this.userId = userId;
            this.roundsPerThread = roundsPerThread;
            this.userThread = userThread;
        }

        public void run() {
            final CountDownLatch roundsCountDown = new CountDownLatch(roundsPerThread);
            while (roundsPerThread > 0) {
                logger.info("userId - {}: Thread - {}, Round - {}", userId, userThread, roundsPerThread);
                chooseRound(userId);
                roundsCountDown.countDown();
                roundsPerThread--;
            }
        }
    }



    public void chooseRound(String userId) {
        int round = RandomUtils.nextInt(1,4);
        //logger.info("round - {}, userId - {}", round, userId);
        switch (round) {
            case 1:
                roundA(userId);
                break;
            case 2:
                roundB(userId);
                break;
            case 3:
                roundC(userId);
        }
    }

    public void roundA(String userId) {
        deposit(userId, 100, Currency.USD);
        withdraw(userId, 200, Currency.USD);
        deposit(userId, 100, Currency.EUR);
        getBalance(userId);
        withdraw(userId, 100, Currency.USD);
        getBalance(userId);
        withdraw(userId, 100, Currency.USD);
    }

    public void roundB(String userId) {
        withdraw(userId, 100, Currency.GBP);
        deposit(userId, 300, Currency.GBP);
        withdraw(userId, 100, Currency.GBP);
        withdraw(userId, 100, Currency.GBP);
        withdraw(userId, 100, Currency.GBP);
    }

    public void roundC(String userId) {
        getBalance(userId);
        deposit(userId, 100, Currency.USD);
        deposit(userId, 100, Currency.USD);
        withdraw(userId, 100, Currency.USD);
        deposit(userId, 100, Currency.USD);
        getBalance(userId);
        withdraw(userId, 200, Currency.USD);
        getBalance(userId);
    }

}
