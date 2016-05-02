import Bank.BankException;
import Operations.CloseInvestmentOperation;
import Products.Account;
import Products.Balance.Balance;
import Products.Balance.BalanceException;
import Products.Interest;
import Products.Investment;
import Products.Product;
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

    private Balance accountBalance;
    private Balance balance;
    private BigDecimal expectedInterestValue;
    private int ownerId;
    private Interest interest;
    private Account account;

    @Before
    public void setUp(){
        balance = new Balance(new BigDecimal(1111));
        accountBalance = new Balance(new BigDecimal(1000));
        expectedInterestValue = new BigDecimal(99);
        ownerId = 1234;
        TimeDependentInterestCalculationStrategy strategyMock = mock(TimeDependentInterestCalculationStrategy.class);
        when(strategyMock.calculateInterest(any(Product.class), any(double.class))).thenReturn(expectedInterestValue);
        interest = new Interest(strategyMock, 0.3);
        account = new Account(1234, accountBalance, mock(Date.class), mock(Interest.class));
    }

    @Test
    public void CloseInvestment() throws BankException, BalanceException
    {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        Date expireDate = calendar.getTime();
        Investment investment = new Investment(ownerId, balance, expireDate, interest, account);

        CloseInvestmentOperation operation = new CloseInvestmentOperation(investment);
        operation.execute();
        balance.addToBalance(expectedInterestValue);
        BigDecimal newInterestBalance = balance.getBalanceValue();
        assertEquals(newInterestBalance.add(accountBalance.getBalanceValue()), account.getBalanceValue());
    }

    @Test
    public void CloseInvestmentBeforeExpireDate() throws BankException
    {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 1);
        Date expireDate = calendar.getTime();
        Investment investment = new Investment(ownerId, balance, expireDate, interest, account);

        CloseInvestmentOperation operation = new CloseInvestmentOperation(investment);
        operation.execute();
        assertEquals(balance.getBalanceValue().add(accountBalance.getBalanceValue()), account.getBalanceValue());
    }

}
