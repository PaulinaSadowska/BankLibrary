import java.math.BigDecimal;

/**
 * Created by palka on 11.03.2016.
 */
public interface IPaymentable {

    void transfer(Product product, BigDecimal amount);
    void payment(BigDecimal amount, PaymentDirection direction);

}
