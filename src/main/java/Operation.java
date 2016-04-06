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
