import java.math.BigDecimal;

/**
 * Created by arasz on 03.04.2016.
 * Definiuje interfejs przez który klient ma dostęp do rachunku
 */
public interface IAccount extends IHistory
{
    boolean hasDebit();

    Debit getDebit();

    boolean transfer(Product product, BigDecimal amount);

    boolean payment(BigDecimal amount, PaymentDirection direction) throws IllegalArgumentException;
}
