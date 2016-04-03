import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by arasz on 02.04.2016.
 */
public class Bank
{
    private ProductManager _productManager;

    public Bank()
    {
        _productManager = new ProductManager();
    }

    public boolean createDebit(BigDecimal debitValue, Account account)
    {
        if(account.hasDebit())
        {
            return false;
        }
        account.createDebit(new Debit(debitValue));
        return true;
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
     * jezeli ownerId != null
     *    znajdz baseAccount i przypisz mu nowy produkt bankowy (lokata lub kredyt)
     *    jezeli chce zalozyc konto - wyjatek
     *
     */
    public IAccount createProduct(Class<Product> productType, Integer ownerId, BigDecimal balance,)
    {
        IAccount account = null;

        //nadaj expireDate <- klient przekazalby jaka chce dlugosc, a na tej podstawie bank obliczalby expireDate
        //nadaj interest <- bank ma swoje pola AccountInterest, loanInterest i InvestmentInterest i nadawalby je. Jak uzaleznic je od trwania?

        if(ownerId != null) //klient posiada juz konto
        {
            account = _productManager.getAccount(ownerId);
        }
        if(account == null) //klient nie posiada konta
        {
            ownerId = _productManager.getAvailableOwnerId();
            account = _productManager.createNewProduct(Account.class, ownerId, balance, x, x);
        }

        if(!productType.getName().equals(Account.class.getName()))
        {
            _productManager.createNewProduct(productType, ownerId, balance, x, x, account);
        }
        else
        {
            throw new UnsupportedOperationException();
        }
        return account;
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
