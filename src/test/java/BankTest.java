import com.google.inject.Guice;
import com.google.inject.Injector;
import com.sun.source.tree.AssertTree;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import static org.junit.Assert.*;
/**
 * Created by arasz on 03.04.2016.
 */
public class BankTest
{
    private Bank _bank;
    private BigDecimal _balance;
    private ProductDuration _duration;
    private ProductManager _productManager;

    @Before
    public void setUp(){
      //  Injector injector = Guice.createInjector(new BankModule());
      //  _bank = injector.getInstance(Bank.class); //TODO - popraw, to tak nie dziala!
        _productManager = new ProductManager();
        _bank = new Bank(_productManager);
        _balance = new BigDecimal(1200);
        _duration = new ProductDuration(0, 5);
    }

    @Test
    public void createAccountTest()
    {
        assertNotEquals(Constants.ERROR_CODE, _bank.createAccount(_balance, _duration, new TimeDependentInterestCalculationStrategy(), 0.5));
    }

    @Test
    public void createLoanTest()
    {
        assertNotEquals(Constants.ERROR_CODE, _bank.createLoan(_balance, _duration, new TimeDependentInterestCalculationStrategy(), 0.5));
    }

    @Test
    public void createInvestmentTest()
    {
        assertNotEquals(Constants.ERROR_CODE, _bank.createInvestment(_balance, _duration, new TimeDependentInterestCalculationStrategy(), 0.3));
    }


    @Test
    public void createInvestmentToSpecificAccountTest()
    {
        int ownerId = _bank.createAccount(_balance, _duration, new TimeDependentInterestCalculationStrategy(), 0.5);
        assertNotEquals(Constants.ERROR_CODE, _bank.createInvestment(ownerId, _balance, _duration, new TimeDependentInterestCalculationStrategy(), 0.5));
    }


    @Test
    public void createLoanToAccountTest()
    {
        int ownerId = _bank.createAccount(_balance, _duration, new TimeDependentInterestCalculationStrategy(), 0.5);
        assertNotEquals(Constants.ERROR_CODE, _bank.createLoan(ownerId, _balance, _duration, new TimeDependentInterestCalculationStrategy(), 0.5));
    }

    @Test
    public void createAccountWithDebitTest()
    {
        int ownerId = _bank.createAccount(_balance, _duration, new TimeDependentInterestCalculationStrategy(), 0.5);
        assertNotEquals(Constants.ERROR_CODE, ownerId);
        assertTrue(_bank.createDebit(new BigDecimal(1800), ownerId));
    }

    @Test
    public void cannotCreateAccountWithTwoDebitsTest()
    {
        int ownerId = _bank.createAccount(_balance, _duration, new TimeDependentInterestCalculationStrategy(), 0.5);
        assertNotEquals(Constants.ERROR_CODE, ownerId);
        assertTrue(_bank.createDebit(new BigDecimal(1800), ownerId));
        assertFalse(_bank.createDebit(new BigDecimal(200), ownerId));
    }
}
