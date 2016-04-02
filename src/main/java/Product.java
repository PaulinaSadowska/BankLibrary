import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by palka on 11.03.2016.
 */
public abstract class Product
{

    protected int _ownerId;
    protected BigDecimal _balance;
    protected Interest _interest;
    protected Date _creationDate;
    protected Date _expireDate;

    public  Product(int ownerId, BigDecimal balance, Date expireDate, Interest interest)
    {
        this._ownerId = ownerId;
        this._balance = balance;
        this._creationDate = new Date();
        this._expireDate = expireDate;
        this._interest = interest;
    }

    public void setBalance(BigDecimal newBalance) { _balance = newBalance;}

    public abstract ProductType getProductType();

    public BigDecimal getBalance() {
        return _balance;
    }

    public Interest getInterest() {
        return _interest;
    }

    public int getOwnerId() {
        return _ownerId;
    }

    public Date getCreationDate() {
        return _creationDate;
    }

    public Date getExpireDate() {
        return _expireDate;
    }

    public boolean expired(){
        Calendar cal = Calendar.getInstance();
        if(_expireDate.before(cal.getTime())) {
            return true;
        }
        return false;
    }

}
