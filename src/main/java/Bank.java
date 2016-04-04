import com.google.inject.Inject;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by arasz on 02.04.2016.
 */
public class Bank
{
    private ProductManager _productManager;

    @Inject
    public Bank(ProductManager productManager)
    {
        _productManager = productManager;
    }

   /* public boolean createDebit(BigDecimal debitValue, int ownerId)
    {
        Account account = _productManager.getAccount(ownerId);
        if(account.hasDebit())
        {
            return false;
        }
        account.createDebit(new Debit(debitValue));
        return true;
    }*/
    

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
    public Product createProduct(Class<Product> productType, Integer ownerId, BigDecimal balance,
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
            if(_productManager.createNewProduct(Account.class, ownerId, balance, expireDate, interest))
            {
                account = _productManager.getAccount(ownerId).get(0);
            }
            if(account == null)
            {
                throw new UnknownError();
            }
        }

        if(!productType.getName().equals(Account.class.getName()))
        {
            _productManager.createNewProduct(productType, ownerId, balance, expireDate, interest, (Account)account);
        }
        else
        {
            throw new UnsupportedOperationException();
        }
        return account;
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
