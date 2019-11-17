package betpawa.grpcserver.repository;

import betpawa.grpcserver.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {
    Account findAccountByUserId(String userId);
}
