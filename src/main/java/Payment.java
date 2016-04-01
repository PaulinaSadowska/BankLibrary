import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by arasz on 18.03.2016.
 */
public class Payment extends Operation implements IPaymentable{

    public Payment(OperationType operationType, Date date, String description, Product product)
    {
        super(operationType, date, description, product);
    }

    public void transfer(Product product, BigDecimal amount) {

    }

    public void payment(BigDecimal amount, PaymentDirection direction) {

    }
}
