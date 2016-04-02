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
    protected Product _product; // Czy po usunięciu produktu zerujemy tą referencję?


    public Operation(OperationType operationType)
    {
        _operationType = operationType;
        _date = new Date();
        _description = "";
        _product = null;
    }

    public Operation(OperationType operationType, Product product)
    {
        _operationType = operationType;
        _date = new Date();
        _description = "";
        _product = product;
    }

    public Operation(OperationType operationType, Product product, String description)
    {
        _operationType = operationType;
        _date = new Date();
        _description = description;
        _product = product;
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
