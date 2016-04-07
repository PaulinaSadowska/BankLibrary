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

    /*
     @pre: product != null, amount > 0
     @post: product.balance+=amount, _product.balance-=amount
     @invariant: product.balance <= porduct.balance+amount (?)
      */
    public void transfer(BigDecimal amount, Account account) throws BankException
    {
        if(account == null)
            throw new NullPointerException("Null account");

        try
        {
            this.payment(amount, PaymentDirection.Out);
            account.payment(amount, PaymentDirection.In);
        }
        catch (Exception ex)
        {
            BankException exception = (new BankException("Error during transfer.", OperationType.Transfer));
            exception.initCause(ex);
            throw exception;
        }
        _history.add(new Operation(OperationType.Transfer));
    }

    /*
    @pre: amount > 0,
    @post: _product.balance-=amount ;
    @invariant: _product.balance >= _product.balance-amount; _product.balance >= {debit | 0}
     */
    public void payment(BigDecimal amount, PaymentDirection direction) throws IllegalArgumentException, BankException
    {
        if(amount.longValueExact() < 0)
            throw new IllegalArgumentException("Negative amount.");

        switch (direction)
        {
            case In:
            {
                BigDecimal newBalance = getBalance().add(amount);
                setBalance(newBalance);
                break;
            }
            case Out:
            {

                BigDecimal productBalance = getBalance();
                // Zwraca 1 gdy amount jest wieksza od balance
                if(amount.compareTo(productBalance) > 0)
                {
                    if(hasDebit())
                    {
                        BigDecimal balancePlusDebit = getBalanceWithDebit();

                        if (balancePlusDebit.compareTo(amount) >= 0)
                        {
                            setBalance(productBalance.subtract(amount));
                            return;
                        }
                    }
                }
                else
                {
                    BigDecimal newBalance = getBalance().subtract(amount);
                    setBalance(newBalance);
                    return;
                }
                throw new BankException("Output payment amount grater than account balance", OperationType.Payment);
            }

        }
        _history.add(new Operation(OperationType.Payment));
    }
}