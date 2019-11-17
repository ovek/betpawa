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

@Service
@Transactional
public class DepositService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DepositService.class);

    @Autowired
    private BalanceRepository balanceRepository;

    @Autowired
    private BalanceService balanceService;

    public String deposit(String userId, BigDecimal amount, String currencyCode) {
        LOGGER.debug("User {} deposits {}{}", userId, amount, currencyCode);

        BalanceDto balanceDto = balanceService.getBalance(userId, currencyCode);

        if(balanceDto.getErrorCode() != null)
            return balanceDto.getErrorCode();

        Balance balance = balanceDto.getBalance();

        balance.setAmount(balance.getAmount().add(amount));
        balanceRepository.save(balance);
        return ReturnMessage.OK;
    }
}
