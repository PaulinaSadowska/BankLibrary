package Operations;

import Bank.BankException;

import java.util.Calendar;
import java.util.Date;

/**
 * @author Arasz
 * Reprezentuje operacje wykonana przez bank
 **/
public class Operation
{

    protected OperationType operationType;
    protected Date date;
    protected String description;
    protected boolean executed;


    public Operation(OperationType operationType)
    {
        this.operationType = operationType;
        date = Calendar.getInstance().getTime();
        description = "";
    }

    public Operation(OperationType operationType, String description)
    {
        this.operationType = operationType;
        date = Calendar.getInstance().getTime();
        this.description = description;
    }

    public void checkExecuted(boolean undo) throws BankException
    {
        if(executed && !undo)
            throw new BankException("Operation can't be executed more than once");
        else if(!executed && undo)
            throw new BankException("Operation not executed and undo called");
    }

    public  boolean getExecuted()
    {
        return executed;
    }

    protected void setExecuted(boolean executed)
    {
        this.executed = executed;
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public Date getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

}
