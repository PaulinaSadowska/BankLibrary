package Products;

import Products.Balance.Balance;
import Products.Balance.BalanceException;
import Utils.OperationsHistory;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by palka on 11.03.2016.
 * Reprezentuje wspólne cechy produktu bankowego ( konta, lokaty, itp. )
 */
public abstract class Product implements IProduct
{

    protected int _ownerId;
    protected Balance balance;
    protected Interest _interest;
    protected Date _creationDate;
    protected Date _expireDate;
    protected OperationsHistory _history;
    protected Account _baseAccount;



    public  Product(Integer ownerId, Balance balance, Date expireDate, Interest interest)
    {
        _ownerId = ownerId;
        this.balance = balance;
        _creationDate = new Date();
        _expireDate = expireDate;
        _interest = interest;
        _history = new OperationsHistory();
    }

    public  Product(int ownerId, Balance balance, Date expireDate, Interest interest, Account baseAccount)
    {
        this(ownerId, balance, expireDate, interest);
        _baseAccount = baseAccount;
    }

    public void addToBalance(BigDecimal amount) throws BalanceException
    {
        balance.addToBalance(amount);
    }

    public BigDecimal getBalanceValue()
    {
        return balance.getBalanceValue();
    }

    @Override
    public Interest getInterest() {
        return _interest;
    }

    @Override
    public int getOwnerId() {
        return _ownerId;
    }

    @Override
    public Date getCreationDate() {
        return _creationDate;
    }

    @Override
    public Date getExpireDate() {
        return _expireDate;
    }

    @Override
    public OperationsHistory getOperationsHistory()
    {
        return _history;
    }

    @Override
    public boolean expired(){
        Calendar cal = Calendar.getInstance();
        return _expireDate.before(cal.getTime());
    }


    @Override
    public void substractFromBalance(BigDecimal amount) throws BalanceException
    {
        balance.substractFromBalance(amount);
    }

}
