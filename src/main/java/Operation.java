import java.util.Date;

/**
 * Created by palka on 11.03.2016.
 */
public abstract class Operation {

    protected OperationType operationType;
    protected Date date;
    protected String description;
    protected Product product;

    public Operation(OperationType operationType, Date date, String description, Product product)
    {
        this.operationType = operationType;
        this.date = date;
        this.description = description;
        this.product = product;
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
