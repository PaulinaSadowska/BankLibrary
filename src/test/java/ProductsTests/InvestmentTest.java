package ProductsTests;

import Products.*;
import Utils.ProductFactory;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by palka on 31.03.2016.
 */
public class InvestmentTest {

    @Test
    public void getInvestmentBaseAccountTest_AccountExists_getsAccount()
    {
        IAccount account = ProductFactory.createAccount(200);
        Investment investment = ProductFactory.createInvestment(1200, (Account)account);
        assertEquals(investment.getBaseAccount(), account);
    }

    @Test
    public void getInvestmentBaseAccountNullTest_AccountExists_getsAccount()
    {
        Investment investment = ProductFactory.createInvestment(1200);
        assertNull(investment.getBaseAccount());
    }
}
