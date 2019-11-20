package betpawa.grpcclient.service;

import betpawa.grpcclient.generated.UserGrpc;
import betpawa.grpcclient.generated.UserOuterClass;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private Logger logger = LoggerFactory.getLogger(UserService.class);
    private UserGrpc.UserBlockingStub userBlockingStub;
    private ManagedChannel channel;

    public UserService() {
    }

    public UserService(String serverHost, int serverPort) {
        channel = ManagedChannelBuilder.forAddress(serverHost, serverPort).usePlaintext().build();
        userBlockingStub = UserGrpc.newBlockingStub(channel);
    }

    public List<String> createUsers(int users) {
        List<String> userIds = new ArrayList<>(users);
        try {
            int i = 0;

            do {
                String userId = Integer.toString(i);
                createUser(userId);
                userIds.add(userId);
                i++;
            } while (i < users);
        } finally {
            channel.shutdown();
        }

        return userIds;
    }

    private UserOuterClass.UserResponse createUser(String userId) {
        UserOuterClass.UserRequest request = UserOuterClass.UserRequest.newBuilder().setUserId(userId).build();
        return userBlockingStub.user(request);
    }

}
