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

    protected int ownerId;
    protected Balance balance;
    protected Interest interest;
    protected Date creationDate;
    protected Date expireDate;
    protected OperationsHistory history;
    protected IAccount baseAccount;



    public  Product(Integer ownerId, Balance balance, Date expireDate, Interest interest)
    {
        this.ownerId = ownerId;
        this.balance = balance;
        this.creationDate = new Date();
        this.expireDate = expireDate;
        this.interest = interest;
        this.history = new OperationsHistory();
    }

    public  Product(int ownerId, Balance balance, Date expireDate, Interest interest, IAccount baseAccount)
    {
        this(ownerId, balance, expireDate, interest);
        this.baseAccount = baseAccount;
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
        return interest;
    }

    @Override
    public int getOwnerId() {
        return ownerId;
    }

    @Override
    public Date getCreationDate() {
        return creationDate;
    }

    @Override
    public Date getExpireDate() {
        return expireDate;
    }

    @Override
    public OperationsHistory getOperationsHistory()
    {
        return history;
    }

    @Override
    public boolean expired(){
        Calendar cal = Calendar.getInstance();
        return expireDate.before(cal.getTime());
    }


    @Override
    public void subtractFromBalance(BigDecimal amount) throws BalanceException
    {
        balance.subtractFromBalance(amount);
    }

}
