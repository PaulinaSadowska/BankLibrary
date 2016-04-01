import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by arasz on 18.03.2016.
 */
public class Account extends Product {

    private int _ownerId;
    private OperationsHistory _operationHistory;

    public Account(BigDecimal balance, Date creationDate, Date expireDate, Interest interest,
                   int ownerId , OperationsHistory history){
        this._balance = balance;
        this._creationDate = creationDate;
        this._expireDate = expireDate;
        this._interest = interest;
        this._ownerId = ownerId;
        this._operationHistory = history;
    }

    public int getId() {
        return _ownerId;
    }

    public OperationsHistory getOperationHistory() {
        return _operationHistory;
    }
}
