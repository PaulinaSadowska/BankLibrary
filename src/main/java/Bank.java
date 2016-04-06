import com.google.inject.Inject;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by arasz on 02.04.2016.
 */
public class Bank
{
    private ProductManager _productManager;
    private OperationsHistory _globalHistory;

    @Inject
    public Bank(ProductManager productManager)
    {
        _globalHistory = new OperationsHistory();
        _productManager = productManager;
    }

    public void createDebit(BigDecimal debitValue, int ownerId)
    {
        Account account = _productManager.getAccount(ownerId).get(0);
        account.setDebit(new Debit(debitValue));

        _globalHistory.add(new Operation(OperationType.MakeDebit));
    }

   /* public boolean pay(BigDecimal amount, int ownerId)
    {
        Account account = _productManager.getAccount(ownerId).get(0);
        if(account!=null)
        {
            if(!account.payment(amount, PaymentDirection.Out))
            {
                return false;
            }
        }
        return true;
    }

    public boolean deposit(BigDecimal amount, int ownerId)
    {
        Account account = _productManager.getAccount(ownerId).get(0);
        if(account!=null)
        {
            if(!account.payment(amount, PaymentDirection.In))
            {
                return false;
            }
            return true;
        }
        return false;
    }

    public boolean transfer(BigDecimal amount, int ownerId, int targetOwnerId)
    {
        Account account = _productManager.getAccount(ownerId).get(0);
        Account targetAccount = _productManager.getAccount(targetOwnerId).get(0);
        if(account!=null){
            if(!account.transfer(amount, targetAccount)){
                return false;
            }
            return true;
        }
        return false;
    }*/

    public BigDecimal getAccountBalance(int ownerId)
    {
        Account account = _productManager.getAccount(ownerId).get(0);
        return account.getBalance();
    }

    /**
     * Utworzenie konta
     * @pre:
     * @post:
     * @invariant:
     * @return
     *jezeli ownerId == null
     *   jezeli chce stworzyc konto - nadaj nowe id i stworz konto
     *   jezeli chce stworzyc lokate lub kredyt - stworz konto i przypisana do niego lokate lub kredyt.
     *
     * jezeli ownerId != null lub nie znaleziono przypisanego do niego konta
     *    znajdz baseAccount i przypisz mu nowy produkt bankowy (lokata lub kredyt)
     *    jezeli chce zalozyc konto - wyjatek
     *
     */
    private <T extends Product> T createProduct(Class<T> productType, Integer ownerId, BigDecimal balance,
                                 ProductDuration duration, IInterestCalculationStrategy interestStrategy, double interestPercent)
            throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException
    {
        Account account = null;
        Interest interest = new Interest(interestStrategy, interestPercent);

        Date expireDate = getExpireDate(duration);
        if(ownerId != null) //klient posiada juz konto
        {
            account = _productManager.getAccount(ownerId).get(0);
        }
        if(account == null) //klient nie posiada konta
        {
            ownerId = _productManager.getAvailableOwnerId();
            if (productType.getName().equals(Account.class.getName())){
                return  _productManager.createNewProduct(Account.class, ownerId, balance, expireDate, interest))
            }
            account = _productManager.createNewProduct(Account.class, ownerId, balance, expireDate, interest));
        }
        account = _productManager.getAccount(ownerId).get(0);
        return _productManager.createNewProduct(productType, ownerId, balance, expireDate, interest, account))
    }

    public Account createAccount(BigDecimal balance, ProductDuration duration, IInterestCalculationStrategy interestStrategy, double interestPercent)
            throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException
    {
        return createProduct(Account.class, null, balance, duration, interestStrategy, interestPercent);

    }

    public Loan createLoan(Integer ownerId, BigDecimal balance,
                                 ProductDuration duration, IInterestCalculationStrategy interestStrategy, double interestPercent)
            throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException
    {
        return createProduct(Loan.class, ownerId, balance, duration, interestStrategy, interestPercent);

    }

    public Loan createLoan(BigDecimal balance,
                              ProductDuration duration, IInterestCalculationStrategy interestStrategy, double interestPercent)
            throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException
    {
        return createProduct(Loan.class, null, balance, duration, interestStrategy, interestPercent);
    }

    public Investment createInvestment(Integer ownerId, BigDecimal balance,
                                 ProductDuration duration, IInterestCalculationStrategy interestStrategy, double interestPercent)
            throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException
    {
        return createProduct(Investment.class, ownerId, balance, duration, interestStrategy, interestPercent);
    }

    public Investment createInvestment(BigDecimal balance,
                                    ProductDuration duration, IInterestCalculationStrategy interestStrategy, double interestPercent)
            throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException
    {
        return createProduct(Investment.class, null, balance, duration, interestStrategy, interestPercent);
    }

    private Date getExpireDate(ProductDuration duration)
    {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, duration.getNumOfMonths());
        cal.add(Calendar.YEAR, duration.getNumOfYears());
        return cal.getTime();
    }

    /**
     * Usunięcie konta
     * @pre: account != null
     * @post: account == null
     * @invariant:
     * @return
     * Nieusuniecie konta powinno rzucać wyjatki
     */
    public void deleteAccount()
    {

    }
}
