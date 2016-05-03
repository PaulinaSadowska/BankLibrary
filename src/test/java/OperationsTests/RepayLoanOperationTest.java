package OperationsTests;

/**
 * Created by Paulina Sadowska on 03.05.2016.
 */

import Bank.BankException;
import Operations.RepayLoanOperation;
import Products.*;
import Products.Balance.Balance;
import Utils.ProductFactory;
import Utils.TimeDependentInterestCalculationStrategy;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RepayLoanOperationTest
{
    private static final int expectedInterestValueInt = 99;
    private static final int loanBalanceValueInt = 1000;
    private static final int accountBalanceInt = 1111;

    private BigDecimal expectedInterestValue = new BigDecimal(expectedInterestValueInt);
    private BigDecimal accountBalanceValue = new BigDecimal(accountBalanceInt);
    private Interest interest;
    private IAccount account;

    @Before
    public void setUp(){
        TimeDependentInterestCalculationStrategy strategyMock = mock(TimeDependentInterestCalculationStrategy.class);
        when(strategyMock.calculateInterest(any(Product.class), any(double.class))).thenReturn(expectedInterestValue);
        interest = new Interest(strategyMock, 0.3);
        account = ProductFactory.createAccount(accountBalanceValue.intValue());
    }

    @Test
    public void CloseLoan_EnoughMoneyOnAccount_getsMoneyFromAccount() throws Exception
    {
        Loan loan = ProductFactory.createLoan(loanBalanceValueInt, account, interest);

        BigDecimal expectedAccountBalance = new BigDecimal(accountBalanceInt - loanBalanceValueInt - expectedInterestValueInt);

        RepayLoanOperation operation = new RepayLoanOperation(loan);
        operation.execute();
        assertEquals(expectedAccountBalance, account.getBalanceValue());
    }

    @Test (expected = BankException.class)
    public void CloseLoan_NotEnoughMoneyOnAccount_throwsException() throws Exception
    {
        Loan loan = ProductFactory.createLoan(account.getBalanceValue().intValue()+100, account, interest);

        RepayLoanOperation operation = new RepayLoanOperation(loan);
        operation.execute();
    }

    @Test (expected = NullPointerException.class)
    public void CloseLoan_NullArgument_throwsException() throws Exception
    {
        RepayLoanOperation operation = new RepayLoanOperation(null);
        operation.execute();
    }


}
