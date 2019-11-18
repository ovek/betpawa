package betpawa.grpcclient.grpcclient;

import betpawa.grpcclient.generated.UserGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@SpringBootApplication
public class GrpcClientApplication implements ApplicationRunner {
    private static final Logger logger = LoggerFactory.getLogger(GrpcClientApplication.class);
    @Value("${grpc.server.host}")
    private String serverHost;
    @Value("${grpc.server.port}")
    private int serverPort;

    public static void main(String[] args) {
        SpringApplication.run(GrpcClientApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        ManagedChannel channel = ManagedChannelBuilder.forAddress(serverHost, serverPort).build();
        UserGrpc.UserBlockingStub userBlockingStub = UserGrpc.newBlockingStub(channel);
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
    }

}
