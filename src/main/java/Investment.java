import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by arasz on 18.03.2016.
 * stores information about the investment
 */
public class Investment extends Product{

    protected Account _baseAccount;

    public Investment(BigDecimal balance, Date expireDate, Interest interest, int ownerId, Account baseAccount)
    {
        super(ownerId, balance, expireDate, interest);
        this._baseAccount = baseAccount;

    }

    public Account getBaseAccount() {
        return _baseAccount;
    }

    @Override
    public ProductType getProductType() {
        return ProductType.Investment;
    }
}
