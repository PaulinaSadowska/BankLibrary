import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by arasz on 02.04.2016.
 */
public class Bank
{
    private ProductManager _productMenager;

    private OperationsHistory _operationsHistory;

    public Bank()
    {
        _operationsHistory =  OperationsHistory.getGlobalHistory();
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
     * Nieutworznenie konta powinno rzucać wyjatki
     */
    public void createAccount()
    {

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
