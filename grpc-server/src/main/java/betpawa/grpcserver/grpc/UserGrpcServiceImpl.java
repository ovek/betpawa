package betpawa.grpcserver.grpc;

import betpawa.grpcserver.generated.UserGrpc;
import betpawa.grpcserver.generated.UserOuterClass;
import betpawa.grpcserver.service.UserService;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@GRpcService
public class UserGrpcServiceImpl extends UserGrpc.UserImplBase {
    public static final Logger logger = LoggerFactory.getLogger(UserGrpcServiceImpl.class);

    private UserService userService;

    public UserGrpcServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void user(UserOuterClass.UserRequest request,
                          StreamObserver<UserOuterClass.UserResponse> responseObserver) {
        logger.info("GRPC create user: UserId = {} and amount = {}{}", request.getUserId());
        userService.createUser(request.getUserId());
        responseObserver.onNext(UserOuterClass.UserResponse.newBuilder().build());
        responseObserver.onCompleted();
    }
}
