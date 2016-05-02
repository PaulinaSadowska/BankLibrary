import Operations.MakeDebitOperation;
import Products.*;
import Products.Balance.Balance;
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
    private IAccount debitAccount;

    @Before
    public void setUp(){
        debitAccount = new Account(123, mock(Balance.class), mock(Date.class), mock(Interest.class));
    }

    @Test
    public void makeDebitToAccountTest() throws Exception
    {
        assertFalse(debitAccount instanceof DebitAccount);
        MakeDebitOperation operation = new MakeDebitOperation(debitAccount, 1200);
        operation.execute();
        assertTrue(debitAccount.hasDebit());
    }

}
