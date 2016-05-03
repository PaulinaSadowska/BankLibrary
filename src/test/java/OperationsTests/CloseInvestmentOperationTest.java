package OperationsTests;

import Bank.BankException;
import Operations.CloseInvestmentOperation;
import Products.*;
import Products.Balance.Balance;
import Products.Balance.BalanceException;
import Utils.ProductFactory;
import Utils.TimeDependentInterestCalculationStrategy;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;
/**
 * Created by Paulina Sadowska on 18.04.2016.
 */
public class CloseInvestmentOperationTest
{

    private Balance balance;
    private BigDecimal expectedInterestValue = new BigDecimal(99);
    private int ownerId;
    private Interest interest;
    private IAccount account;
    private BigDecimal investmentBalanceValue = new BigDecimal(1111);
    private BigDecimal accountBalanceValue = new BigDecimal(1000);

    @Before
    public void setUp(){
        balance = new Balance(investmentBalanceValue);
        ownerId = 1234;
        TimeDependentInterestCalculationStrategy strategyMock = mock(TimeDependentInterestCalculationStrategy.class);
        when(strategyMock.calculateInterest(any(Product.class), any(double.class))).thenReturn(expectedInterestValue);
        interest = new Interest(strategyMock, 0.3);
        account = ProductFactory.createAccount(accountBalanceValue.intValue());

    }

    @Test
    public void CloseInvestment() throws BankException, BalanceException
    {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        Date expireDate = calendar.getTime();
        Investment investment = new Investment(ownerId, balance, expireDate, interest, account);

        BigDecimal expectedAccountBalance = investmentBalanceValue.add(expectedInterestValue).add(accountBalanceValue);

        CloseInvestmentOperation operation = new CloseInvestmentOperation(investment);
        operation.execute();
        assertEquals(expectedAccountBalance, account.getBalanceValue());
    }

    @Test
    public void CloseInvestmentBeforeExpireDate() throws BankException
    {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 1);
        Date expireDate = calendar.getTime();
        Investment investment = new Investment(ownerId, balance, expireDate, interest, account);

        BigDecimal expectedBalance = accountBalanceValue.add(investmentBalanceValue);

        CloseInvestmentOperation operation = new CloseInvestmentOperation(investment);
        operation.execute();
        assertEquals(expectedBalance, account.getBalanceValue());
    }

}
