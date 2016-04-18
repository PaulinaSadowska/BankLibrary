
import Bank.BankException;
import Operations.ChangeInterestMechanismOperation;
import Products.Interest;
import Utils.IInterestCalculationStrategy;
import Utils.TimeDependentInterestCalculationStrategy;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;
/**
 * Created by Paulina Sadowska on 18.04.2016.
 */
public class ChangeInterestMechanismOperationTest
{
    private Interest _interest;
    @Before
    public void setUp(){
        _interest = new Interest(mock(IInterestCalculationStrategy.class), 0.5);
    }

    @Test
    public void ChangeInterestMechanism() throws BankException
    {
        ChangeInterestMechanismOperation operation = new ChangeInterestMechanismOperation(_interest, new TimeDependentInterestCalculationStrategy());
        operation.execute();
        assertEquals(TimeDependentInterestCalculationStrategy.class, _interest.getStrategy().getClass());
    }

}
