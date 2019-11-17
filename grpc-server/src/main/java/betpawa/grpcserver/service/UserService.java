package betpawa.grpcserver.service;

import betpawa.grpcserver.model.Account;
import betpawa.grpcserver.model.Balance;
import betpawa.grpcserver.repository.AccountRepository;
import betpawa.grpcserver.repository.BalanceRepository;
import betpawa.grpcserver.utils.Currency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@Transactional
public class UserService {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private BalanceRepository balanceRepository;

    public void createUser(String userId) {
        Account account = new Account();
        account.setUserId(userId);

        account = accountRepository.save(account);

        Balance usdBalance = new Balance();
        usdBalance.setAccountId(account.getAccoId());
        usdBalance.setAmount(BigDecimal.ZERO);
        usdBalance.setCcy(Currency.USD);
        balanceRepository.save(usdBalance);

        Balance euroBalance = new Balance();
        euroBalance.setAccountId(account.getAccoId());
        euroBalance.setAmount(BigDecimal.ZERO);
        euroBalance.setCcy(Currency.EUR);
        balanceRepository.save(euroBalance);

        Balance gbpBalance = new Balance();
        gbpBalance.setAccountId(account.getAccoId());
        gbpBalance.setAmount(BigDecimal.ZERO);
        gbpBalance.setCcy(Currency.GBP);
        balanceRepository.save(gbpBalance);
    }
}
