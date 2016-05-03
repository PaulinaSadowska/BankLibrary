import Operations.OpenInvestmentOperation;
import Products.*;
import Products.Balance.Balance;
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
    private ProductDuration duration;
    private ProductManager manager;
    private int ownerId;
    private Interest interest;
    private Balance balance;

    @Before
    public void setUp(){
        duration = new ProductDuration(1, 1);
        manager = new ProductManager();
        balance = new Balance(new BigDecimal(1200));
        interest = new Interest(mock(IInterestCalculationStrategy.class), 0.3);
        ownerId = 1234;
    }

    @Test
    public void createInvestmentToAccountTest() throws Exception
    {

        IAccount account = manager.createNewProduct(Account.class, ownerId, balance, duration, interest,123);
        assertNotNull(account);
        OpenInvestmentOperation openInvestment =
                new OpenInvestmentOperation(account.getOwnerId(), balance, duration, interest, manager);
        openInvestment.execute();
        Investment newInvestment = manager.getInvestment(ownerId).get(0);
        assertNotNull(newInvestment);
        assertEquals(account.getOwnerId(), newInvestment.getOwnerId());
    }

    @Test (expected=IndexOutOfBoundsException.class)
    public void createInvestmentToNonExistingAccountTest() throws Exception
    {
        int ownerId = 999;
        OpenInvestmentOperation openInvestment =
                new OpenInvestmentOperation(ownerId, balance, duration, interest, manager);
        openInvestment.execute();
        assertNull(manager.getInvestment(ownerId).get(0));
    }
}
