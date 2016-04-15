package Bank;

import Operations.Operation;
import Operations.OperationType;
import Products.Account;
import Products.Investment;
import Products.Loan;
import Products.Product;
import com.google.inject.Inject;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

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
            throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException, IndexOutOfBoundsException
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
                return  _productManager.createNewProduct(productType, ownerId, balance, expireDate, interest);
            }
            _productManager.createNewProduct(Account.class, ownerId, balance, expireDate, interest);
        }
        account = _productManager.getAccount(ownerId).get(0);
        return _productManager.createNewProduct(productType, ownerId, balance, expireDate, interest, account);
    }

    public Account createAccount(BigDecimal balance, ProductDuration duration, IInterestCalculationStrategy interestStrategy,
                                 double interestPercent)
            throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException
    {
        return createProduct(Account.class, null, balance, duration, interestStrategy, interestPercent);

    }

    public Loan createLoan(Integer ownerId, BigDecimal balance,
                           ProductDuration duration, IInterestCalculationStrategy interestStrategy, double interestPercent)
            throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, IndexOutOfBoundsException
    {
        return createProduct(Loan.class, ownerId, balance, duration, interestStrategy, interestPercent);

    }

    public Loan createLoan(BigDecimal balance,
                           ProductDuration duration, IInterestCalculationStrategy interestStrategy, double interestPercent)
            throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, IndexOutOfBoundsException
    {
        return createProduct(Loan.class, null, balance, duration, interestStrategy, interestPercent);
    }

    public Investment createInvestment(Integer ownerId, BigDecimal balance,
                                       ProductDuration duration, IInterestCalculationStrategy interestStrategy, double interestPercent)
            throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, IndexOutOfBoundsException
    {
        return createProduct(Investment.class, ownerId, balance, duration, interestStrategy, interestPercent);
    }

    public Investment createInvestment(BigDecimal balance,
                                       ProductDuration duration, IInterestCalculationStrategy interestStrategy, double interestPercent)
            throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, IndexOutOfBoundsException
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