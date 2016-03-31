import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by palka on 11.03.2016.
 */
public abstract class Product {

    protected BigDecimal amountOfMoney;
    protected Interest interest;
    protected Date dateStart;
    protected Date dateEnd;

    public BigDecimal getAmountOfMoney() {
        return amountOfMoney;
    }

    public Interest getInterest() {
        return interest;
    }

    public Date getDateStart() {
        return dateStart;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public boolean didEnd(){
        Calendar cal = Calendar.getInstance();
        if(dateEnd.before(cal.getTime())) {
            return true;
        }
        return false;
    }

}
