import Operations.Operation;
import Utils.OperationsHistory;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.mockito.Mockito.mock;

/**
 * Created by arasz on 03.04.2016.
 */
public class OperationsHistoryTest
{

    private OperationsHistory _history;

    @Before
    public void setUp()
    {
        _history = new OperationsHistory();
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void tryGetOperationsWithoutInserting_ThenFail()
    {
        int numberOfOperations = 4;
        _history.getOperations(numberOfOperations);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void tryGetMoreOperationsThanInserted_ThenFail()
    {
        int numberOfOperations = 4;

        for (int i=0; i< numberOfOperations-1; i++)
            _history.add(mock(Operation.class));

        _history.getOperations(numberOfOperations);
    }

    @Test
    public void addOneOperation_ThenGetTheSameOperation()
    {
        Operation insertedMock = mock(Operation.class);
        _history.add(insertedMock );
        Operation expectedMock =_history.getLastOperation();

        Assert.assertEquals(expectedMock, insertedMock);
    }

    @Test
    public void addFourOperations_ThenGetFourOperations()
    {
        int numberOfOperations = 4;

        for (int i=0; i< numberOfOperations; i++)
            _history.add(mock(Operation.class));

        List<Operation> list = _history.getOperations(numberOfOperations);

        Assert.assertEquals(numberOfOperations, list.size());
    }

    @Test
    public void addFourOperations_ThenGet2Operations()
    {
        int numberOfOperations = 4;
        int numberOfOperationsToGet =2;

        for (int i=0; i< numberOfOperations; i++)
            _history.add(mock(Operation.class));

        List<Operation> list = _history.getOperations(numberOfOperationsToGet);

        Assert.assertEquals(numberOfOperationsToGet, list.size());
    }

    @After
    public void destroy()
    {
        _history = null;
    }

}
