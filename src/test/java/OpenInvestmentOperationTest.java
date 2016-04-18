import Operations.MakeLoanOperation;
import Operations.OpenInvestmentOperation;
import Products.*;
import Utils.IInterestCalculationStrategy;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

/**
 * Created by Paulina Sadowska on 18.04.2016.
 */
public class OpenInvestmentOperationTest
{
    private ProductDuration _duration;
    private ProductManager _manager;
    private int _ownerId;
    private Interest _interest;
    private BigDecimal _balance;

    @Before
    public void setUp(){
        _duration = new ProductDuration(1, 1);
        _manager = new ProductManager();
        _balance = new BigDecimal(1200);
        _interest = new Interest(mock(IInterestCalculationStrategy.class), 0.3);
        _ownerId = 1234;
    }

    @Test
    public void createInvestmentToAccountTest() throws Exception
    {
        Account account = _manager.createNewProduct(Account.class, _ownerId, _balance, _duration, _interest);
        assertNotNull(account);
        OpenInvestmentOperation openInvestment =
                new OpenInvestmentOperation(account.getOwnerId(), _balance, _duration, _interest, _manager);
        openInvestment.execute();
        Investment newInvestment = _manager.getInvestment(_ownerId).get(0);
        assertNotNull(newInvestment);
        assertEquals(account.getOwnerId(), newInvestment.getOwnerId());
    }

    @Test (expected=IndexOutOfBoundsException.class)
    public void createInvestmentToNonexistingAccountTest() throws Exception
    {
        int ownerId = 999;
        OpenInvestmentOperation openInvestment =
                new OpenInvestmentOperation(ownerId, _balance, _duration, _interest, _manager);
        openInvestment.execute();
        assertNull(_manager.getInvestment(ownerId).get(0));
    }
}
