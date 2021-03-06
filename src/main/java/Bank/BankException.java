package Bank;

import Operations.Operation;
import Operations.OperationType;

/**
 * Created by arasz on 06.04.2016.
 */
public class BankException extends Exception
{
    private OperationType _operationType;

    public BankException(Exception innerException)
    {
        super(innerException);
    }

    public BankException(String message, Exception innerException)
    {
        this(message, OperationType.None, innerException);
    }

    public BankException(String message)
    {
        this(message, OperationType.None);
    }

    public BankException(String message, OperationType operationType)
    {
        this(message, operationType, null);
    }

    public BankException(String message, OperationType operationType, Exception innerException)
    {
        super(message, innerException);
        _operationType  = operationType;
    }

    @Override
    public String getMessage()
    {
        return super.getMessage() + "\n Operation which caused exception: "+ _operationType.toString();
    }
}
