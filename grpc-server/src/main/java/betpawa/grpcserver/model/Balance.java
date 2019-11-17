package betpawa.grpcserver.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "balance")
public class Balance {
    @Id
    @Column(name = "bala_id", nullable = false)
    @GenericGenerator(name = "balance_id_generator", strategy = "uuid")
    @GeneratedValue(generator = "balance_id_generator")
    private String balanceId;

    @Column(name = "acco_id", nullable = false)
    private String accountId;

    @Column(name = "amt", nullable = false)
    private BigDecimal amount;

    @Column(name = "ccy", nullable = false)
    private String ccy;

    public String getBalanceId() {
        return balanceId;
    }

    public void setBalanceId(String balanceId) {
        this.balanceId = balanceId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCcy() {
        return ccy;
    }

    public void setCcy(String ccy) {
        this.ccy = ccy;
    }
}
