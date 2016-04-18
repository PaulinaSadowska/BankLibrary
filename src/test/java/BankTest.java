
import Bank.Bank;
import Products.*;
import Utils.TimeDependentInterestCalculationStrategy;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
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

        Account account = _bank.createAccount(new BigDecimal(1200), _duration,
                mock(TimeDependentInterestCalculationStrategy.class), 0.5);
        assertNotNull(account);
    }
/*
    @Test
    public void createLoanTest() throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException
    {
        Loan loan = _bank.createLoan(new BigDecimal(1200), _duration, mock(TimeDependentInterestCalculationStrategy.class), 0.5);
        assertNotNull(loan);
    }

    @Test
    public void createInvestmentTest() throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException
    {
        Investment investment = _bank.createInvestment(new BigDecimal(1200), _duration, new TimeDependentInterestCalculationStrategy(), 0.3);
        assertNotNull(investment);
    }


    @Test
    public void createInvestmentToSpecificAccountTest() throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException
    {

        Account account = _bank.createAccount(new BigDecimal(1200), _duration, mock(TimeDependentInterestCalculationStrategy.class), 0.5);
        assertNotNull(account);
        Investment investment = _bank.createInvestment(account.getOwnerId(), new BigDecimal(1200), _duration, new TimeDependentInterestCalculationStrategy(), 0.3);
        assertNotNull(investment);
        assertEquals(account.getOwnerId(), investment.getOwnerId());
    }


    @Test
    public void createLoanToAccountTest() throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException
    {
        Account account = _bank.createAccount(new BigDecimal(1200), _duration, mock(TimeDependentInterestCalculationStrategy.class), 0.5);
        assertNotNull(account);
        Loan loan = _bank.createLoan(account.getOwnerId(), new BigDecimal(1200), _duration, new TimeDependentInterestCalculationStrategy(), 0.3);
        assertNotNull(loan);
        assertEquals(account.getOwnerId(), loan.getOwnerId());
    }

    @Test (expected=IndexOutOfBoundsException.class)
    public void createLoanToNonexistingAccountTest() throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException
    {
        int ownerId = 999;
        _bank.createLoan(ownerId, new BigDecimal(1200), mock(ProductDuration.class), new TimeDependentInterestCalculationStrategy(), 0.3);
    }


    @Test
    public void createAccountWithDebitTest() throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException
    {
         Account account = _bank.createAccount(new BigDecimal(1200), _duration, mock(TimeDependentInterestCalculationStrategy.class), 0.5);
        assertNotNull(account);
        assertFalse(account.hasDebit());
        _bank.createDebit(mock(BigDecimal.class), account.getOwnerId());
        assertTrue(account.hasDebit());
    }*/
}
