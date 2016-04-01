import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by arasz on 18.03.2016.
 */
public class Account extends Product {

    private int _ownerId;
    private  Debit _debit;
    private OperationsHistory _operationHistory;

    public Account(BigDecimal balance, Date expireDate, Interest interest,
                   int ownerId , OperationsHistory history)
    {
        super(balance, expireDate, interest);
        this._ownerId = ownerId;
        this._operationHistory = history;
    }

    public Account(BigDecimal balance, Date expireDate, Interest interest,
                   int ownerId , OperationsHistory history, Debit debit)
    {
        this(balance, expireDate, interest, ownerId, history);
        _debit = debit;
    }

    public Debit getDebit()  {return  _debit;}

    public int getId() {
        return _ownerId;
    }

    public OperationsHistory getOperationHistory() {
        return _operationHistory;
    }
}
