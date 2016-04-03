import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by arasz on 18.03.2016.
 * Konto (rachunek bankowy)
 */
public class Account extends Product
{
    private  Debit _debit;
    private OperationsHistory _operationHistory;
    private List<Product> _productList;

    public Account(BigDecimal balance, Date expireDate, Interest interest,
                   int ownerId , OperationsHistory history)
    {
        super(ownerId, balance, expireDate, interest);
        this._operationHistory = history;
    }

    public Account(BigDecimal balance, Date expireDate, Interest interest,
                   int ownerId , OperationsHistory history, Debit debit)
    {
        this(balance, expireDate, interest, ownerId, history);
        _debit = debit;
    }

    public boolean hasDebit() {return _debit!=null;}

    public Debit getDebit()  {return  _debit;}

    public void createDebit(Debit debit)  {
        this._debit = debit;
    }

    public OperationsHistory getOperationHistory() {
        return _operationHistory;
    }


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
     @post: porduct.balance+=amount, _product.balance-=amount
     @invariant: product.balance <= porduct.balance+amount (?)
      */
    public boolean transfer(Product product, BigDecimal amount)
    {

        return false;
    }

    /*
    @pre: amount > 0,
    @post: _product.balance-=amount ;
    @invariant: _product.balance >= _product.balance-amount; _product.balance >= {debit | 0}
     */
    public boolean payment(BigDecimal amount, PaymentDirection direction) throws IllegalArgumentException
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
                    if(getDebit()!=null)
                    {
                        Debit debit = getDebit();
                        BigDecimal balancePlusDebit = new BigDecimal(0);

                        balancePlusDebit = balancePlusDebit.add(debit.getDebitValue());
                        balancePlusDebit = balancePlusDebit.add(productBalance);


                        if(balancePlusDebit.compareTo(amount) >= 0)
                        {
                            setBalance(productBalance.subtract(amount));
                            return true;
                        }
                    }

                    return false;
                }

                BigDecimal newBalance = getBalance().subtract(amount);
                setBalance(newBalance);
                break;
            }
        }
        return true;
    }

    public void openInvestment(Investment newInvestment)
    {
        _productList.add(newInvestment);
    }

    private <T extends Product> List<T> getElements()
    {
        List<T> list = new ArrayList<T>();

        for(Product product: _productList)
        {
            T castedProduct = (T) product;
            if( castedProduct != null)
                list.add(castedProduct);
        }
        return  list;
    }

    public  List<Investment> getInvestments()
    {
        return  getElements();
    }

    public void makeLoan(Loan newLoan)
    {
        _productList.add(newLoan);
    }

    public List<Loan> getLoans()
    {
        return getElements();
    }


}
