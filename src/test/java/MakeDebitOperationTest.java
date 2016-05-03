import Operations.MakeDebitOperation;
import Products.*;
import Products.Balance.Balance;
import Utils.ProductFactory;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Date;
import java.util.concurrent.atomic.AtomicReference;

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

        debitAccount = ProductFactory.createAccount(1234);
    }

    @Test
    public void makeDebitToAccountTest() throws Exception
    {
        assertFalse(debitAccount instanceof DebitAccount);
        AtomicReference<IAccount> refrence = new AtomicReference<IAccount>(debitAccount);
        MakeDebitOperation operation = new MakeDebitOperation(refrence, new BigDecimal(100));
        operation.execute();
        debitAccount = refrence.get();
        assertTrue(debitAccount instanceof DebitAccount);
    }

}
