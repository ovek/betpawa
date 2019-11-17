package betpawa.grpcserver.service;

import betpawa.grpcserver.dto.BalanceDto;
import betpawa.grpcserver.model.Balance;
import betpawa.grpcserver.repository.BalanceRepository;
import betpawa.grpcserver.utils.ReturnMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static betpawa.grpcserver.utils.ReturnMessage.INSUFFICIENT_FUNDS;

@Service
@Transactional
public class WithdrawalService {
    private static final Logger LOGGER = LoggerFactory.getLogger(WithdrawalService.class);

    private BalanceRepository balanceRepository;
    private BalanceService balanceService;

    public WithdrawalService(BalanceRepository balanceRepository, BalanceService balanceService) {
        this.balanceRepository = balanceRepository;
        this.balanceService = balanceService;
    }

    public String withdraw(String userId, BigDecimal amount, String currencyCode) {
        LOGGER.debug("User {} withdraws {}{}", userId, amount, currencyCode);

        BalanceDto balanceDto = balanceService.getBalance(userId, currencyCode);

        if(balanceDto.getErrorCode() != null)
            return balanceDto.getErrorCode();

        Balance balance = balanceDto.getBalance();

        BigDecimal endBalance = balance.getAmount().subtract(amount);

        if(endBalance.compareTo(BigDecimal.ZERO) < 0) {
            return INSUFFICIENT_FUNDS;
        }

        balance.setAmount(endBalance);
        balanceRepository.save(balance);
        return ReturnMessage.OK;
    }
}
