package betpawa.grpcserver.grpc;

import betpawa.grpcserver.generated.BalanceOuterClass;
import betpawa.grpcserver.generated.BalanceServiceGrpc;
import betpawa.grpcserver.model.Balance;
import betpawa.grpcserver.service.BalanceService;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@GRpcService
public class BalanceGrpcServiceImpl extends BalanceServiceGrpc.BalanceServiceImplBase {
    public static final Logger logger = LoggerFactory.getLogger(BalanceGrpcServiceImpl.class);

    private BalanceService balanceService;

    public BalanceGrpcServiceImpl(BalanceService balanceService) {
        this.balanceService = balanceService;
    }

    @Override
    public void balance(BalanceOuterClass.BalanceRequest request,
                        StreamObserver<BalanceOuterClass.BalanceResponse> responseObserver) {
        logger.debug("Balance: userId={}", request.getUserId());
        List<Balance> balances = balanceService.getBalances(request.getUserId());
        BalanceOuterClass.BalanceResponse response = BalanceOuterClass.BalanceResponse.newBuilder()
                .addAllBalances(build(balances)).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    private List<BalanceOuterClass.Balance> build(List<Balance> balances) {
        List<BalanceOuterClass.Balance> grpcBalances = new ArrayList();

        balances.forEach(balance -> {
            grpcBalances.add(BalanceOuterClass.Balance.newBuilder()
                    .setBalanceId(balance.getBalanceId())
                    .setAmount(balance.getAmount().doubleValue())
                    .setCcy(balance.getCcy()).build());
        });

        return grpcBalances;
    }
}
