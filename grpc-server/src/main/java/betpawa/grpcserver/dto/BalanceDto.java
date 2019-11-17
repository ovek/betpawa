package betpawa.grpcserver.dto;

import betpawa.grpcserver.model.Balance;

public class BalanceDto {
    private String errorCode;
    private Balance balance;

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public Balance getBalance() {
        return balance;
    }

    public void setBalance(Balance balance) {
        this.balance = balance;
    }
}
