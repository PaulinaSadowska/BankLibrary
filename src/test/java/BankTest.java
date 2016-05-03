
import Bank.Bank;
import Products.*;
import Products.Balance.Balance;
import Utils.TimeDependentInterestCalculationStrategy;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.mockito.Mockito.mock;
import static org.junit.Assert.*;
/**
 * Created by paulina on 03.04.2016.
 */
public class BankTest
{
    private Bank _bank;
    private ProductDuration _duration;

    @Before
    public void setUp(){
        _duration = new ProductDuration(1,1);
        _bank = new Bank(new ProductManager());
    }

    @Test
    public void createAccountTest() throws Exception
    {

        IAccount account = _bank.createAccount(new Balance(new BigDecimal(1200)), _duration,
                mock(TimeDependentInterestCalculationStrategy.class), 0.5);
        assertNotNull(account);
    }
}
