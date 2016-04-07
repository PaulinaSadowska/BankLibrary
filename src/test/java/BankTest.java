
import org.junit.Before;
import org.junit.Test;
import org.mockito.cglib.core.Constants;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.sql.Time;

import static org.mockito.Mockito.mock;
import static org.junit.Assert.*;
/**
 * Created by paulina on 03.04.2016.
 */
public class BankTest
{
    private Bank _bank;
    private BigDecimal _balance;
    private ProductDuration _duration;
    private ProductManager _productManager;

    @Before
    public void setUp(){
        _productManager = new ProductManager();
        _bank = new Bank(_productManager);
        _balance = new BigDecimal(1200);
        _duration = mock(ProductDuration.class);
    }

    @Test
    public void createAccountTest()
    {
        try
        {
            Account account = _bank.createAccount(_balance, _duration, mock(TimeDependentInterestCalculationStrategy.class), 0.5);
            assertNotNull(account);
            assertEquals(_balance, account.getBalance());
        } catch (InvocationTargetException e)
        {
            fail(e.getMessage());
        } catch (NoSuchMethodException e)
        {
            fail(e.getMessage());
        } catch (InstantiationException e)
        {
            fail(e.getMessage());
        } catch (IllegalAccessException e)
        {
            fail(e.getMessage());
        }
    }

    @Test
    public void createLoanTest()
    {
        try
        {
            Loan loan = _bank.createLoan(_balance, _duration, mock(TimeDependentInterestCalculationStrategy.class), 0.5);
            assertNotNull(loan);
            assertEquals(_balance, loan.getBalance());
        } catch (InvocationTargetException e)
        {
            fail(e.getMessage());
        } catch (NoSuchMethodException e)
        {
            fail(e.getMessage());
        } catch (InstantiationException e)
        {
            fail(e.getMessage());
        } catch (IllegalAccessException e)
        {
            fail(e.getMessage());
        }
    }

    @Test
    public void createInvestmentTest()
    {
        try
        {
            Investment investment = _bank.createInvestment(_balance, _duration, new TimeDependentInterestCalculationStrategy(), 0.3);
            assertNotNull(investment);
            assertEquals(_balance, investment.getBalance());
        } catch (InvocationTargetException e)
        {
            fail(e.getMessage());
        } catch (NoSuchMethodException e)
        {
            fail(e.getMessage());
        } catch (InstantiationException e)
        {
            fail(e.getMessage());
        } catch (IllegalAccessException e)
        {
            fail(e.getMessage());
        }
    }


    @Test
    public void createInvestmentToSpecificAccountTest()
    {
        try
        {
            Account account = _bank.createAccount(_balance, _duration, mock(TimeDependentInterestCalculationStrategy.class), 0.5);
            assertNotNull(account);
            Investment investment = _bank.createInvestment(account.getOwnerId(), _balance, _duration, new TimeDependentInterestCalculationStrategy(), 0.3);
            assertNotNull(investment);
            assertEquals(_balance, investment.getBalance());
            assertEquals(account.getOwnerId(), investment.getOwnerId());

        } catch (InvocationTargetException e)
        {
            fail(e.getMessage());
        } catch (NoSuchMethodException e)
        {
            fail(e.getMessage());
        } catch (InstantiationException e)
        {
            fail(e.getMessage());
        } catch (IllegalAccessException e)
        {
            fail(e.getMessage());
        } }


    @Test
    public void createLoanToAccountTest()
    {
        try
        {
            Account account = _bank.createAccount(_balance, _duration, mock(TimeDependentInterestCalculationStrategy.class), 0.5);
            assertNotNull(account);
            Loan loan = _bank.createLoan(account.getOwnerId(), _balance, _duration, new TimeDependentInterestCalculationStrategy(), 0.3);
            assertNotNull(loan);
            assertEquals(_balance, loan.getBalance());
            assertEquals(account.getOwnerId(), loan.getOwnerId());

        } catch (InvocationTargetException e)
        {
            fail(e.getMessage());
        } catch (NoSuchMethodException e)
        {
            fail(e.getMessage());
        } catch (InstantiationException e)
        {
            fail(e.getMessage());
        } catch (IllegalAccessException e)
        {
            fail(e.getMessage());
        }
    }

    @Test
    public void createLoanToNonexistingAccountTest()
    {
        try
        {
            int ownerId = 999;
            _bank.createLoan(ownerId, _balance, _duration, new TimeDependentInterestCalculationStrategy(), 0.3);
            fail("loan should not be created");

        } catch (InvocationTargetException e)
        {
            fail(e.getMessage());
        } catch (NoSuchMethodException e)
        {
            fail(e.getMessage());
        } catch (InstantiationException e)
        {
            fail(e.getMessage());
        } catch (IllegalAccessException e)
        {
            fail(e.getMessage());
        }
        catch(IndexOutOfBoundsException e){
            //loan should not be created because account don't exist
        }
    }


    @Test
    public void createAccountWithDebitTest()
    {
        try
        {
            Account account = _bank.createAccount(_balance, _duration, mock(TimeDependentInterestCalculationStrategy.class), 0.5);
            assertNotNull(account);
            assertEquals(_balance, account.getBalance());
            assertFalse(account.hasDebit());
            _bank.createDebit(new BigDecimal(2000), account.getOwnerId());
            assertTrue(account.hasDebit());
        } catch (InvocationTargetException e)
        {
            fail(e.getMessage());
        } catch (NoSuchMethodException e)
        {
            fail(e.getMessage());
        } catch (InstantiationException e)
        {
            fail(e.getMessage());
        } catch (IllegalAccessException e)
        {
            fail(e.getMessage());
        }
    }
}
