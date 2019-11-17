package betpawa.grpcserver.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "account")
public class Account {

    @Id
    @Column(name = "acco_id", nullable = false)
    @GenericGenerator(name = "account_id_generator", strategy = "uuid")
    @GeneratedValue(generator = "account_id_generator")
    private String accoId;

    @Column(name = "user_id", nullable = false)
    private String userId;

    public String getAccoId() {
        return accoId;
    }

    public void setAccoId(String accoId) {
        this.accoId = accoId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return accoId.equals(account.accoId) &&
                userId.equals(account.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accoId, userId);
    }
}
