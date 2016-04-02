import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by palka on 11.03.2016.
 */
public class Loan extends Product{

    protected Account _baseAccount;

    public Loan(BigDecimal balance, Date expireDate, Interest interest, int ownerId, Account baseAccount)
    {
        super(ownerId, balance, expireDate, interest);
        _baseAccount = baseAccount;
    }

    public Account getBaseAccount() {
        return _baseAccount;
    }


    @Override
    public ProductType getProductType() {
        return ProductType.Loan;
    }
}
