import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by arasz on 18.03.2016.
 */
public class Account extends Product {

    private  Debit _debit;
    private OperationsHistory _operationHistory;

    public Account(BigDecimal balance, Date expireDate, Interest interest,
                   int ownerId , OperationsHistory history)
    {
        super(ownerId, balance, expireDate, interest);
        this._operationHistory = history;
    }

    public Account(BigDecimal balance, Date expireDate, Interest interest,
                   int ownerId , OperationsHistory history, Debit debit)
    {
        this(balance, expireDate, interest, ownerId, history);
        _debit = debit;
    }

    public Debit getDebit()  {return  _debit;}

    public OperationsHistory getOperationHistory() {
        return _operationHistory;
    }

    @Override
    public ProductType getProductType() {
        return ProductType.Account;
    }
}
