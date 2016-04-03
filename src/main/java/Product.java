import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by palka on 11.03.2016.
 * Reprezentuje wspólne cechy produktu bankowego ( konta, lokaty, itp. )
 */
public abstract class Product
{

    protected int _ownerId;
    protected BigDecimal _balance;
    protected Interest _interest;
    protected Date _creationDate;
    protected Date _expireDate;
    protected OperationsRegister _history;
    protected Account _baseAccount;



    public  Product(int ownerId, BigDecimal balance, Date expireDate, Interest interest)
    {
        _ownerId = ownerId;
        _balance = balance;
        _creationDate = new Date();
        _expireDate = expireDate;
        _interest = interest;
        _history = new OperationsRegister();
    }

    public  Product(int ownerId, BigDecimal balance, Date expireDate, Interest interest, Account baseAccount)
    {
        this(ownerId, balance, expireDate, interest);
        _baseAccount = baseAccount;
    }


    public void setBalance(BigDecimal newBalance) { _balance = newBalance;}

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
