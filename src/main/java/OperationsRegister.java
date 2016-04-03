import java.util.ArrayList;
import java.util.List;

/**
 * Created by arasz on 18.03.2016.
 */
public class OperationsRegister
{
    final static private OperationsRegister _globalHistory = new OperationsRegister();

    static public OperationsRegister getGlobalHistory()
    {
        return  _globalHistory;
    }

    private List<Operation> _executedOperations;

    public OperationsRegister()
    {
        //TODO: Linked czy Array? // ArrayList is better!
        _executedOperations = new ArrayList<Operation>();
    }

    public void add(Operation operation)
    {
        _executedOperations.add(operation);
        _globalHistory._executedOperations.add(operation);
    }

    public List<Operation> getLastOperations(int numberOfOperations)
    {
        return _executedOperations.subList(_executedOperations.size()-numberOfOperations, _executedOperations.size());
    }
}
