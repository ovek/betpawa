package betpawa.grpcserver.grpc;

import betpawa.grpcserver.generated.DepositGrpc;
import betpawa.grpcserver.generated.DepositOuterClass;
import betpawa.grpcserver.service.DepositService;
import com.google.protobuf.RpcCallback;
import com.google.protobuf.RpcController;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

@GRpcService
public class DepositGrpcServiceImpl extends DepositGrpc.DepositImplBase {
    public static final Logger logger = LoggerFactory.getLogger(DepositGrpcServiceImpl.class);

    private DepositService depositService;

    public DepositGrpcServiceImpl(DepositService depositService) {
        this.depositService = depositService;
    }

    @Override
    public void deposit(DepositOuterClass.DepositRequest request,
                        StreamObserver<DepositOuterClass.DepositResponse> responseObserver) {
        logger.info("GRPC deposit: UserId = {} and amount = {}{}", request.getUserId(), request.getAmount(), request.getCcy());
        String result = depositService.deposit(request.getUserId(), new BigDecimal(request.getAmount()),
                request.getCcy());
        responseObserver.onNext(DepositOuterClass.DepositResponse.newBuilder().setMessage(result).build());
        responseObserver.onCompleted();
    }
}
