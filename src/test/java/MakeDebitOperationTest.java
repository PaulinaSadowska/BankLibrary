import Operations.MakeDebitOperation;
import Operations.MakeLoanOperation;
import Products.*;
import Utils.IInterestCalculationStrategy;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Date;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;
/**
 * Created by Paulina Sadowska on 18.04.2016.
 */
public class MakeDebitOperationTest
{
    private Account _account;

    @Before
    public void setUp(){
        _account = new Account(123, mock(BigDecimal.class), mock(Date.class), mock(Interest.class), 123);
    }

    @Test
    public void makeDebitToAccountTest() throws Exception
    {
        assertFalse(_account.hasDebit());
        MakeDebitOperation operation = new MakeDebitOperation(_account, 1200);
        operation.execute();
        assertTrue(_account.hasDebit());
    }

}
