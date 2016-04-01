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

    /*
    @pre: product != null, amount > 0
    @post: porduct.balance+amount, _product.balance-amount
    @invariant: product.balance <= porduct.balance+amount (?)
     */
    public void transfer(Product product, BigDecimal amount)
    {

    }

    public void payment(BigDecimal amount, PaymentDirection direction) throws IllegalArgumentException
    {
        if(amount.longValueExact() < 0)
            throw new IllegalArgumentException("Negative amount.");

        switch (direction)
        {
            case In:
            {
                BigDecimal newBalance = product.getBalance().add(amount);
                product.setBalance(newBalance);
                break;
            }
            case Out:
            {
                BigDecimal newBalance = product.getBalance().subtract(amount);
                product.setBalance(newBalance);
                break;
            }
        }
    }
}
