import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by palka on 11.03.2016.
 */
public abstract class Product
{

    protected BigDecimal balance;
    protected Interest interest;
    protected Date creationDate;
    protected Date expireDate;

    public BigDecimal getBalance() {
        return balance;
    }

    public Interest getInterest() {
        return interest;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public boolean didEnd(){
        Calendar cal = Calendar.getInstance();
        if(expireDate.before(cal.getTime())) {
            return true;
        }
        return false;
    }

}
