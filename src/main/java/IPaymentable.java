import java.math.BigDecimal;

/**
 * Created by palka on 11.03.2016.
 */
public interface IPaymentable
{

    boolean transfer(Product product, BigDecimal amount);
    boolean payment(BigDecimal amount, PaymentDirection direction);

}
