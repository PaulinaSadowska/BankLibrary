package Operations;

import Bank.BankException;
import Operations.OperationType;

import java.util.Calendar;
import java.util.Date;

/**
 * @author Arasz
 * Reprezentuje operacje wykonana przez bank
 **/
public class Operation
{

    protected OperationType _operationType;
    protected Date _date;
    protected String _description;
    protected boolean _executed;


    public Operation(OperationType operationType)
    {
        _operationType = operationType;
        _date = Calendar.getInstance().getTime();
        _description = "";
    }

    public Operation(OperationType operationType, String description)
    {
        _operationType = operationType;
        _date = Calendar.getInstance().getTime();
        _description = description;
    }

    public void checkExecuted(boolean undo) throws BankException
    {
        if(_executed && !undo)
            throw new BankException("Operation can't be executed more than once");
        else if(!_executed && undo)
            throw new BankException("Operation not executed and undo called");
    }

    public  boolean getExecuted()
    {
        return _executed;
    }

    protected void setExecuted(boolean executed)
    {
        _executed = executed;
    }

    public OperationType getOperationType() {
        return _operationType;
    }

    public Date getDate() {
        return _date;
    }

    public String getDescription() {
        return _description;
    }

}
