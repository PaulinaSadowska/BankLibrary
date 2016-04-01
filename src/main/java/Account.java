import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by arasz on 18.03.2016.
 */
public class Account extends Product {

    private OperationManager _operationManager;
    private int _ownerId;
    private OperationsHistory _operationHistory;

    public Account(BigDecimal balance, Date creationDate, Date expireDate, Interest interest,
                   int ownerId , OperationManager manager, OperationsHistory history){
        this.balance = balance;
        this.creationDate = creationDate;
        this.expireDate = expireDate;
        this.interest = interest;
        this._ownerId = ownerId;
        this._operationManager = manager;
        this._operationHistory = history;
    }

    public OperationManager getOperationManager() {
        return _operationManager;
    }

    public int getId() {
        return _ownerId;
    }

    public OperationsHistory getOperationHistory() {
        return _operationHistory;
    }
}
