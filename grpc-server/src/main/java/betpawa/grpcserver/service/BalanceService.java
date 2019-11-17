package betpawa.grpcserver.service;

import betpawa.grpcserver.dto.BalanceDto;
import betpawa.grpcserver.model.Account;
import betpawa.grpcserver.model.Balance;
import betpawa.grpcserver.repository.AccountRepository;
import betpawa.grpcserver.repository.BalanceRepository;
import betpawa.grpcserver.utils.Currency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static betpawa.grpcserver.utils.ReturnMessage.*;

@Service
public class BalanceService {

    @Autowired
    private BalanceRepository balanceRepository;

    @Autowired
    private AccountRepository accountRepository;

    public BalanceDto getBalance(String userId, String currencyCode) {
        Account account = accountRepository.findAccountByUserId(userId);

        BalanceDto balanceDto = new BalanceDto();

        if(!Currency.SUPPORTED_CURRENCY.contains(currencyCode))
            balanceDto.setErrorCode(UNKNOWN_CURRENCY);

        if(account == null)
            balanceDto.setErrorCode(NO_ACCOUNT_FOUND);

        Balance balance = balanceRepository.findBalanceByAccountIdAndCcy(account.getAccoId(), currencyCode);
        balanceDto.setBalance(balance);
        return balanceDto;
    }

    public List<Balance> getBalances(String userId) {
        Account account = accountRepository.findAccountByUserId(userId);
        return balanceRepository.findBalanceByAccountId(account.getAccoId());
    }
}
