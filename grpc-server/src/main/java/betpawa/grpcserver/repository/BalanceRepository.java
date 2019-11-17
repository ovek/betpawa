package betpawa.grpcserver.repository;

import betpawa.grpcserver.model.Balance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BalanceRepository extends JpaRepository<Balance, String> {
    Balance findBalanceByAccountIdAndCcy(String accountId, String ccy);
    List<Balance> findBalanceByAccountId(String accontId);
}
