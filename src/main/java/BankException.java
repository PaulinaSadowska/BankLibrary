/**
 * Created by arasz on 06.04.2016.
 */
public class BankException extends Exception
{
    private  OperationType _operationType;

    public BankException(String message)
    {
        this(message, OperationType.None);
    }

    public BankException(String message, OperationType operationType)
    {
        super(message);
        _operationType  = operationType;
    }

    @Override
    public String getMessage()
    {
        return super.getMessage() + "\n Operation which caused exception: "+ _operationType.toString();
    }
}
