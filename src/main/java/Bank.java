import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Created by arasz on 02.04.2016.
 */
public class Bank implements IDebitable
{

    private Map<Integer, Account> _accounts = new ;

    private List<Account> _accountsList;

    private OperationsHistory _operationsHistory = OperationsHistory.getGlobalHistory();

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
     * @return False jesli nie utworzono poprawnie konta
     * Nie utworznenie i nie usunięcie konta powinno rzucać wyjatki
     */
    public boolean createAccount(){
        return false;
    }


    /**
     * Usunięcie konta
     * @pre: account != null
     * @post: account == null
     * @invariant:
     * @return false jeżeli nie usunięto poprawnie
     */
    public boolean deleteAccount(){
        return false;
    }

}
