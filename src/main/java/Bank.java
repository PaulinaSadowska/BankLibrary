import java.math.BigDecimal;

/**
 * Created by arasz on 02.04.2016.
 */
public class Bank implements IDebitable
{

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
