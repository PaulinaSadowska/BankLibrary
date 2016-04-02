import java.math.BigDecimal;

/**
 * Created by palka on 11.03.2016.
 */
public interface IDebitable
{
    /**
     * Utworzenie debetu
     * @pre: baseAccount.getDebit() = null
     * @post: baseAccount.getDebit() = Debit
     * @invariant:
     * @return False jezeli konto posiada juz debet
     */
    boolean createDebit(BigDecimal debitValue, Account account);
}
