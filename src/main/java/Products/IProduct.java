package Products;

import Products.Balance.Balance;
import Utils.OperationsHistory;

import java.util.Date;

/**
 * Created by arasz on 22.04.2016.
 */
public interface IProduct
{
    Interest getInterest();

    int getOwnerId();

    Date getCreationDate();

    Date getExpireDate();

    OperationsHistory getOperationsHistory();

    boolean expired();
}
