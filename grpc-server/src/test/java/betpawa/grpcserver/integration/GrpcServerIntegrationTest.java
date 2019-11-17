package betpawa.grpcserver.integration;


import betpawa.grpcserver.GrpcServerApplication;
import betpawa.grpcserver.generated.*;

import betpawa.grpcserver.grpc.BalanceGrpcServiceImpl;
import betpawa.grpcserver.grpc.DepositGrpcServiceImpl;
import betpawa.grpcserver.grpc.UserGrpcServiceImpl;
import betpawa.grpcserver.grpc.WithdrawalGrpcServiceImpl;
import betpawa.grpcserver.model.Account;
import betpawa.grpcserver.model.Balance;
import betpawa.grpcserver.repository.AccountRepository;
import betpawa.grpcserver.repository.BalanceRepository;
import betpawa.grpcserver.service.BalanceService;
import betpawa.grpcserver.service.DepositService;
import betpawa.grpcserver.service.UserService;
import betpawa.grpcserver.service.WithdrawalService;
import betpawa.grpcserver.utils.Currency;
import betpawa.grpcserver.utils.ReturnMessage;
import io.grpc.inprocess.InProcessChannelBuilder;
import io.grpc.inprocess.InProcessServerBuilder;
import io.grpc.testing.GrpcCleanupRule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.MOCK;

import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;


@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = GrpcServerApplication.class,
        webEnvironment = MOCK
)
@Transactional
public class GrpcServerIntegrationTest {
    private BalanceServiceGrpc.BalanceServiceBlockingStub balanceServiceBlockingStub;
    private DepositGrpc.DepositBlockingStub depositBlockingStub;
    private WithdrawGrpc.WithdrawBlockingStub withdrawBlockingStub;
    private UserGrpc.UserBlockingStub userBlockingStub;
    private final String userId = "1";

    @Autowired
    private UserService userService;

    @Autowired
    private BalanceService balanceService;

    @Autowired
    private DepositService depositService;

    @Autowired
    private WithdrawalService withdrawalService;

    @Rule
    public final GrpcCleanupRule grpcCleanup = new GrpcCleanupRule();

    @Before
    public void setup() throws Exception {
        final String userServerName = InProcessServerBuilder.generateName();
        grpcCleanup.register(InProcessServerBuilder
                .forName(userServerName).directExecutor().addService(new UserGrpcServiceImpl(userService)).build().start());
        userBlockingStub = UserGrpc.newBlockingStub(grpcCleanup.register(InProcessChannelBuilder.forName(userServerName).directExecutor().build()));

        final String balanceServerName = InProcessServerBuilder.generateName();
        grpcCleanup.register(InProcessServerBuilder
                .forName(balanceServerName).directExecutor().addService(new BalanceGrpcServiceImpl(balanceService)).build().start());
        balanceServiceBlockingStub = BalanceServiceGrpc.newBlockingStub(grpcCleanup.register(InProcessChannelBuilder.forName(balanceServerName).directExecutor().build()));

        final String depositServerName = InProcessServerBuilder.generateName();
        grpcCleanup.register(InProcessServerBuilder
                .forName(depositServerName).directExecutor().addService(new DepositGrpcServiceImpl(depositService)).build().start());
        depositBlockingStub = DepositGrpc.newBlockingStub(grpcCleanup.register(InProcessChannelBuilder.forName(depositServerName).directExecutor().build()));

        final String withdrawServerName = InProcessServerBuilder.generateName();
        grpcCleanup.register(InProcessServerBuilder
                .forName(withdrawServerName).directExecutor().addService(new WithdrawalGrpcServiceImpl(withdrawalService)).build().start());
        withdrawBlockingStub = WithdrawGrpc.newBlockingStub(grpcCleanup.register(InProcessChannelBuilder.forName(withdrawServerName).directExecutor().build()));
    }

    @Test
    public void integrationTest() {
        userBlockingStub.user(UserOuterClass.UserRequest.newBuilder().setUserId(userId).build());

        /* Withdraw 200 USD - must fail with insufficient funds*/
        WithdrawOuterClass.WithdrawalRequest withdrawalRequest = WithdrawOuterClass.WithdrawalRequest.newBuilder()
                .setAmount(200)
                .setCcy(Currency.USD)
                .setUserId(userId)
                .build();

        WithdrawOuterClass.WithdrawalResponse response = withdrawBlockingStub.withdraw(withdrawalRequest);
        assertEquals(ReturnMessage.INSUFFICIENT_FUNDS, response.getMessage());

        /* Deposit 100 USD */
        DepositOuterClass.DepositRequest depositRequest = DepositOuterClass.DepositRequest.newBuilder()
                .setAmount(100)
                .setCcy(Currency.USD)
                .setUserId(userId).build();

        DepositOuterClass.DepositResponse depositResponse = depositBlockingStub.deposit(depositRequest);

        /* Validate balance is 100 USD */
        BalanceOuterClass.BalanceRequest balanceRequest = BalanceOuterClass.BalanceRequest.newBuilder()
                .setUserId(userId).build();

        BalanceOuterClass.BalanceResponse balanceResponse = balanceServiceBlockingStub.balance(balanceRequest);

        BalanceOuterClass.Balance balance = filterBalances(balanceResponse, Currency.USD);
        assertEquals(balance.getAmount(), 100, 0);

        balance = filterBalances(balanceResponse, Currency.EUR);
        assertEquals(balance.getAmount(), 0, 0);

        balance = filterBalances(balanceResponse, Currency.GBP);
        assertEquals(balance.getAmount(), 0, 0);

        /* Withdrawal 200 USD - must fail with insufficient funds */
        withdrawalRequest = WithdrawOuterClass.WithdrawalRequest.newBuilder()
                .setAmount(200)
                .setCcy(Currency.USD)
                .setUserId(userId)
                .build();

        response = withdrawBlockingStub.withdraw(withdrawalRequest);
        assertEquals(ReturnMessage.INSUFFICIENT_FUNDS, response.getMessage());

        /* Deposit 100 EUR */
        depositRequest = DepositOuterClass.DepositRequest.newBuilder()
                .setAmount(100)
                .setCcy(Currency.EUR)
                .setUserId(userId).build();
        depositResponse = depositBlockingStub.deposit(depositRequest);

        /* Validate balances - must be 100 USD and 100 EUR */
        balanceResponse = balanceServiceBlockingStub.balance(balanceRequest);

        balance = filterBalances(balanceResponse, Currency.USD);
        assertEquals(balance.getAmount(), 100, 0);

        balance = filterBalances(balanceResponse, Currency.EUR);
        assertEquals(balance.getAmount(), 100, 0);

        balance = filterBalances(balanceResponse, Currency.GBP);
        assertEquals(balance.getAmount(), 0, 0);

        /* Make a withdrawal of 200 USD - must return insufficient funds */
        response = withdrawBlockingStub.withdraw(withdrawalRequest);
        assertEquals(ReturnMessage.INSUFFICIENT_FUNDS, response.getMessage());

        /* Deposit 100 USD */
        depositRequest = DepositOuterClass.DepositRequest.newBuilder()
                .setAmount(100)
                .setCcy(Currency.USD)
                .setUserId(userId).build();

        depositResponse = depositBlockingStub.deposit(depositRequest);

        /* Validate balances - must be 200 USD and 100 EUR */
        balanceResponse = balanceServiceBlockingStub.balance(balanceRequest);

        balance = filterBalances(balanceResponse, Currency.USD);
        assertEquals(balance.getAmount(), 200, 0);

        balance = filterBalances(balanceResponse, Currency.EUR);
        assertEquals(balance.getAmount(), 100, 0);

        balance = filterBalances(balanceResponse, Currency.GBP);
        assertEquals(balance.getAmount(), 0, 0);

        /* Withdrawal 200 USD - must succeed */
        withdrawalRequest = WithdrawOuterClass.WithdrawalRequest.newBuilder()
                .setAmount(200)
                .setCcy(Currency.USD)
                .setUserId(userId)
                .build();

        response = withdrawBlockingStub.withdraw(withdrawalRequest);
        assertEquals(response.getMessage(), ReturnMessage.OK);

        /* Validate balances - must be 0 USD and 100 EUR */
        balanceResponse = balanceServiceBlockingStub.balance(balanceRequest);

        balance = filterBalances(balanceResponse, Currency.USD);
        assertEquals(balance.getAmount(), 0, 0);

        balance = filterBalances(balanceResponse, Currency.EUR);
        assertEquals(balance.getAmount(), 100, 0);

        balance = filterBalances(balanceResponse, Currency.GBP);
        assertEquals(balance.getAmount(), 0, 0);

        /* Withdrawal 200 USD - must fail with insufficient funds */
        withdrawalRequest = WithdrawOuterClass.WithdrawalRequest.newBuilder()
                .setAmount(200)
                .setCcy(Currency.USD)
                .setUserId(userId)
                .build();

        response = withdrawBlockingStub.withdraw(withdrawalRequest);
        assertEquals(ReturnMessage.INSUFFICIENT_FUNDS, response.getMessage());
    }

    private BalanceOuterClass.Balance filterBalances(BalanceOuterClass.BalanceResponse response, String currencyCode) {
        return response.getBalancesList()
                .stream()
                .filter(bal -> currencyCode.equals(bal.getCcy()))
                .findFirst().get();
    }

}
