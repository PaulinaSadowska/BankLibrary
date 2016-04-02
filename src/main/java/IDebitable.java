import java.math.BigDecimal;

/**
 * Created by palka on 11.03.2016.
 */
public interface IDebitable
{
    void createDebit(BigDecimal debitValue, Account account);
}
