import com.google.inject.Inject;

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

    public boolean createDebit(BigDecimal debitValue, int ownerId)
    {
        Account account = _productManager.getAccount(ownerId).get(0);
        if(account.hasDebit())
        {
            return false;
        }
        account.createDebit(new Debit(debitValue));
        _globalHistory.add(new Operation(OperationType.MakeDebit, account));
        return true;
    }

    public boolean pay(BigDecimal amount, int ownerId)
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
    }

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
    private <T extends Product> int createProduct(Class<T> productType, Integer ownerId, BigDecimal balance,
                                 ProductDuration duration, IInterestCalculationStrategy interestStrategy, double interestPercent)
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
            if(!_productManager.createNewProduct(Account.class, ownerId, balance, expireDate, interest))
            {
                return Constants.ERROR_CODE;
            }
            if (productType.getName().equals(Account.class.getName())){
                return ownerId;
            }
        }
        account = _productManager.getAccount(ownerId).get(0);
        if(_productManager.createNewProduct(productType, ownerId, balance, expireDate, interest, account))
        {
            return ownerId;
        }
        return Constants.ERROR_CODE;
    }

    public int createAccount(BigDecimal balance, ProductDuration duration, IInterestCalculationStrategy interestStrategy, double interestPercent)
    {
        return createProduct(Account.class, null, balance, duration, interestStrategy, interestPercent);

    }

    public int createLoan(Integer ownerId, BigDecimal balance,
                                 ProductDuration duration, IInterestCalculationStrategy interestStrategy, double interestPercent)
    {
        int id = createProduct(Loan.class, ownerId, balance, duration, interestStrategy, interestPercent);
        if(ownerId != Constants.ERROR_CODE && ownerId == id)
        {
            List<Loan> loans = _productManager.getLoan(ownerId);
            Loan newLoan = loans.get(loans.size()-1);
        }
        return id;

    }

    public int createLoan(BigDecimal balance,
                              ProductDuration duration, IInterestCalculationStrategy interestStrategy, double interestPercent)
    {
        int id = createProduct(Loan.class, null, balance, duration, interestStrategy, interestPercent);
        if(id != Constants.ERROR_CODE)
        {
            List<Loan> loans = _productManager.getLoan(id);
            Loan newLoan = loans.get(loans.size()-1);
            new Operation(OperationType.MakeLoan, newLoan);
        }
        return id;
    }

    public int createInvestment(Integer ownerId, BigDecimal balance,
                                 ProductDuration duration, IInterestCalculationStrategy interestStrategy, double interestPercent)
    {
        int id = createProduct(Investment.class, ownerId, balance, duration, interestStrategy, interestPercent);
        if(ownerId != Constants.ERROR_CODE && ownerId == id)
        {
            List<Investment> investments = _productManager.getInvestment(ownerId);
            Investment newInvestment = investments.get(investments.size()-1);
            _globalHistory.add(new Operation(OperationType.OpenInvestment, newInvestment));
        }
        return id;
    }

    public int createInvestment(BigDecimal balance,
                                    ProductDuration duration, IInterestCalculationStrategy interestStrategy, double interestPercent)
    {
        int id = createProduct(Investment.class, null, balance, duration, interestStrategy, interestPercent);
        if(id != Constants.ERROR_CODE)
        {
            List<Investment> investments = _productManager.getInvestment(id);
            Investment newInvestment = investments.get(investments.size()-1);
        }
        return id;
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
