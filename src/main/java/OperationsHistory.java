import java.util.ArrayList;
import java.util.List;

/**
 * Created by arasz on 18.03.2016.
 */
public class OperationsHistory
{
    final static private OperationsHistory _globalHistory = new OperationsHistory();

    static public OperationsHistory getGlobalHistory() {return  _globalHistory;}

    private List<Operation> _executedOperations;

    public OperationsHistory()
    {
        //TODO: Linked czy Array?
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
