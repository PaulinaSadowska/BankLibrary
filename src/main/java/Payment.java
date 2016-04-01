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
    @post: porduct.balance+=amount, _product.balance-=amount
    @invariant: product.balance <= porduct.balance+amount (?)
     */
    public boolean transfer(Product product, BigDecimal amount)
    {

        return false;
    }

    /*
    @pre: amount > 0,
    @post: _product.balance-=amount ;
    @invariant: _product.balance >= _product.balance-amount; _product.balance >= {debit | 0}
     */
    public boolean payment(BigDecimal amount, PaymentDirection direction) throws IllegalArgumentException
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

                BigDecimal productBalance = product.getBalance();
                // Zwraca 1 gdy amount jest wieksza od balance
                if(amount.compareTo(productBalance) > 0)
                {
                    Account account = (Account)product;
                    if(account!= null && account.getDebit()!=null)
                    {
                        Debit debit = account.getDebit();
                        BigDecimal balancePlusDebit = new BigDecimal(0);
                        balancePlusDebit.add(debit.getMaxDebitValue());
                        balancePlusDebit.add(productBalance);

                        if(balancePlusDebit.compareTo(amount) >= 0)
                        {
                            account.setBalance(productBalance.subtract(amount));
                            return true;
                        }
                    }

                    return false;
                }

                BigDecimal newBalance = product.getBalance().subtract(amount);
                product.setBalance(newBalance);
                break;
            }
        }
        return true;
    }
}
