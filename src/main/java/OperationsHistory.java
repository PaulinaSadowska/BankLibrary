import java.util.LinkedList;
import java.util.List;

/**
 * Created by arasz on 18.03.2016.
 */
public class OperationsHistory
{
    final static private OperationsHistory _globalHistory = new OperationsHistory();

    static public OperationsHistory getGlobalHistory()
    {
        return  _globalHistory;
    }

    private List<Operation> _executedOperations;

    public OperationsHistory()
    {
        //TODO: Linked czy Array?
        // http://beginnersbook.com/2013/12/difference-between-arraylist-and-linkedlist-in-java/
        // "if there is a requirement of frequent addition and deletion in application then LinkedList is a best choice."
        _executedOperations = new LinkedList<Operation>();
    }

    public void add(Operation operation)
    {
        _executedOperations.add(operation);
        _globalHistory._executedOperations.add(operation);
    }

    public Operation getLastOperation()
    {
        return _executedOperations.get(_executedOperations.size()-1);
    }

    public List<Operation> getOperations(int numberOfOperations)
    {
        return _executedOperations.subList(_executedOperations.size()-numberOfOperations, _executedOperations.size());
    }
}
