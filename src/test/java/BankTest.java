import com.google.inject.Guice;
import com.google.inject.Injector;
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
      //  _bank = injector.getInstance(Bank.class); //TODO - to tak nie dziala
        _productManager = new ProductManager();
        _bank = new Bank(_productManager);
        _balance = new BigDecimal(1200);
        _duration = new ProductDuration(0, 5);
    }
/*
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

    @Test
    public void createAccountAndPayTest()
    {
        BigDecimal amount = new BigDecimal(200);
        int ownerId = _bank.createAccount(_balance, _duration, new TimeDependentInterestCalculationStrategy(), 0.5);
        assertNotEquals(Constants.ERROR_CODE, ownerId);
        assertTrue(_bank.pay(amount, ownerId));
        assertEquals(_balance.subtract(amount), _bank.getAccountBalance(ownerId));
    }

    @Test
    public void createAccountAndDepositTest()
    {
        BigDecimal amount = new BigDecimal(200);
        int ownerId = _bank.createAccount(_balance, _duration, new TimeDependentInterestCalculationStrategy(), 0.5);
        assertNotEquals(Constants.ERROR_CODE, ownerId);
        assertTrue(_bank.deposit(amount, ownerId));
        assertEquals(_balance.add(amount), _bank.getAccountBalance(ownerId));
    }

    @Test
    public void createAccountAndFailToPayTest()
    {
        BigDecimal amount = _balance.add(new BigDecimal(100));
        int ownerId = _bank.createAccount(_balance, _duration, new TimeDependentInterestCalculationStrategy(), 0.5);
        assertNotEquals(Constants.ERROR_CODE, ownerId);
        assertFalse(_bank.pay(amount, ownerId));
        assertEquals(_balance, _bank.getAccountBalance(ownerId));
    }

    @Test
    public void createAccountWithDebitAndPayTest()
    {
        //try to pay 100 more than banlance on account with debit value = 200

        BigDecimal amount = _balance.add(new BigDecimal(100));
        BigDecimal debitValue = new BigDecimal(200);
        int ownerId = _bank.createAccount(_balance, _duration, new TimeDependentInterestCalculationStrategy(), 0.5);
        assertNotEquals(Constants.ERROR_CODE, ownerId);
        _bank.createDebit(debitValue, ownerId);
        assertTrue(_bank.pay(amount, ownerId));
        assertEquals(_balance.subtract(amount), _bank.getAccountBalance(ownerId));
    }

    @Test
    public void createAccountWithDebitAndFailToPayTest()
    {
        //try to pay 100 more than banlance on account with debit value = 50

        BigDecimal amount = _balance.add(new BigDecimal(100));
        BigDecimal debitValue = new BigDecimal(50);
        int ownerId = _bank.createAccount(_balance, _duration, new TimeDependentInterestCalculationStrategy(), 0.5);
        assertNotEquals(Constants.ERROR_CODE, ownerId);
        _bank.createDebit(debitValue, ownerId);
        assertFalse(_bank.pay(amount, ownerId));
        assertEquals(_balance, _bank.getAccountBalance(ownerId));
    }

    @Test
    public void createAccountsAndTransferTest()
    {
        BigDecimal amount = new BigDecimal(200);
        int ownerId = _bank.createAccount(_balance, _duration, new TimeDependentInterestCalculationStrategy(), 0.5);
        int targetOwnerId = _bank.createAccount(_balance, _duration, new TimeDependentInterestCalculationStrategy(), 0.5);
        assertNotEquals(Constants.ERROR_CODE, ownerId);
        assertNotEquals(Constants.ERROR_CODE, targetOwnerId);
        assertTrue(_bank.transfer(amount, ownerId, targetOwnerId));
        assertEquals(_balance.subtract(amount), _bank.getAccountBalance(ownerId));
        assertEquals(_balance.add(amount), _bank.getAccountBalance(targetOwnerId));
    }

    @Test
    public void createAccountsAndFailToTransferTest()
    {
        BigDecimal amount = _balance.add(new BigDecimal(100));
        int ownerId = _bank.createAccount(_balance, _duration, new TimeDependentInterestCalculationStrategy(), 0.5);
        int targetOwnerId = _bank.createAccount(_balance, _duration, new TimeDependentInterestCalculationStrategy(), 0.5);
        assertNotEquals(Constants.ERROR_CODE, ownerId);
        assertNotEquals(Constants.ERROR_CODE, targetOwnerId);
        assertFalse(_bank.transfer(amount, ownerId, targetOwnerId));
        assertEquals(_balance, _bank.getAccountBalance(ownerId));
        assertEquals(_balance, _bank.getAccountBalance(targetOwnerId));
    }*/
}
