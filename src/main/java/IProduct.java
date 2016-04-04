import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by arasz on 04.04.2016.
 */
public interface IProduct
{
    BigDecimal getBalance();

    Interest getInterest();

    int getOwnerId();

    Date getCreationDate();

    Date getExpireDate();

    boolean expired();
}
