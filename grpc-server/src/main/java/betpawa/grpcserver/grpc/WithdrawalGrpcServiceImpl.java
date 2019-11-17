package betpawa.grpcserver.grpc;

import betpawa.grpcserver.generated.WithdrawGrpc;
import betpawa.grpcserver.generated.WithdrawOuterClass;
import betpawa.grpcserver.service.WithdrawalService;
import org.lognet.springboot.grpc.GRpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

@GRpcService
public class WithdrawalGrpcServiceImpl extends WithdrawGrpc.WithdrawImplBase {
    public static final Logger logger = LoggerFactory.getLogger(WithdrawalGrpcServiceImpl.class);
    private WithdrawalService withdrawalService;

    public WithdrawalGrpcServiceImpl(WithdrawalService withdrawalService) {
        this.withdrawalService = withdrawalService;
    }

    @Override
    public void withdraw(WithdrawOuterClass.WithdrawalRequest request,
                         io.grpc.stub.StreamObserver<WithdrawOuterClass.WithdrawalResponse> responseObserver) {
        logger.info("GRPC witdraw: UserId = {} and amount = {}{}", request.getUserId(), request.getAmount(), request.getCcy());

        String result = withdrawalService.withdraw(request.getUserId(), new BigDecimal(request.getAmount()),
                request.getCcy());
        responseObserver.onNext(WithdrawOuterClass.WithdrawalResponse.newBuilder().setMessage(result).build());
        responseObserver.onCompleted();
    }
}
