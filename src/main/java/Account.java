import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by arasz on 18.03.2016.
 * Konto (rachunek bankowy)
 */
public class Account extends Product
{
    private  Debit _debit;

    public Account(Integer ownerId, BigDecimal balance, Date expireDate, Interest interest)
    {
        super(ownerId, balance, expireDate, interest);
    }

    public Account(Integer ownerId, BigDecimal balance, Date expireDate, Interest interest, Debit debit)
    {
        this(ownerId, balance, expireDate, interest);
        _debit = debit;
    }

    public boolean hasDebit()
    {
        return _debit!=null;
    }

    public Debit getDebit()
    {
        return  _debit;
    }

    public void setDebit(Debit debit) { _debit = debit;}




    /**
     * @return Saldo konta powiększone o debet
     */
    public BigDecimal getBalanceWithDebit()
    {
        BigDecimal initialBalance;
        if(hasDebit())
            initialBalance = getDebit().getDebitValue().add(getBalance());
        else
            initialBalance = getBalance();

        return initialBalance;
    }

    public void doOperation(ICommand operation) throws BankException
    {
        operation.execute();
        _history.add((Operation) operation);
    }

}