import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by arasz on 02.04.2016.
 */
public class Bank implements IDebitable
{

    private Map<Integer, Account> _accounts;

    private List<Account> _accountsList;

    private OperationsHistory _operationsHistory;

    public Bank(){
        _accounts = new HashMap<Integer, Account>();
        _accountsList = new ArrayList<Account>();
        _operationsHistory =  OperationsHistory.getGlobalHistory();
    }

    public boolean createDebit(BigDecimal debitValue, Account account)
    {
        if(account.hasDebit()){
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
    public void createAccount(){

    }


    /**
     * Usunięcie konta
     * @pre: account != null
     * @post: account == null
     * @invariant:
     * @return
     * Nieusuniecie konta powinno rzucać wyjatki
     */
    public void deleteAccount(){
    }

}
