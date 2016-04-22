package Products;

import Utils.OperationsHistory;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by arasz on 22.04.2016.
 */
public interface IProduct
{
    void setBalance(BigDecimal newBalance);

    void addToBalance(BigDecimal amount);

    BigDecimal getBalance();

    Interest getInterest();

    int getOwnerId();

    Date getCreationDate();

    Date getExpireDate();

    OperationsHistory getOperationsHistory();

    boolean expired();
}
