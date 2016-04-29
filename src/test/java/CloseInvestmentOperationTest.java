import Bank.BankException;
import Operations.CloseInvestmentOperation;
import Products.Account;
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

    private BigDecimal _accountBalance;
    private BigDecimal _balance;
    private  BigDecimal _expectedInterestValue;
    private int _ownerId;
    private Interest _interest;
    private Account _account;

    @Before
    public void setUp(){
        _balance = new BigDecimal(1111);
        _accountBalance = new BigDecimal(1000);
        _expectedInterestValue = new BigDecimal(99);
        _ownerId = 1234;
        TimeDependentInterestCalculationStrategy strategyMock = mock(TimeDependentInterestCalculationStrategy.class);
        when(strategyMock.calculateInterest(any(Product.class), any(double.class))).thenReturn(_expectedInterestValue);
        _interest = new Interest(strategyMock, 0.3);
        _account = new Account(1234, _accountBalance, mock(Date.class), mock(Interest.class));
    }

    @Test
    public void CloseInvestment() throws BankException
    {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        Date expireDate = calendar.getTime();
        Investment investment = new Investment(_ownerId, _balance, expireDate, _interest, _account);

        CloseInvestmentOperation operation = new CloseInvestmentOperation(investment);
        operation.execute();
        BigDecimal newInterestBalance = _balance.add(_expectedInterestValue);
        assertEquals(newInterestBalance.add(_accountBalance), _account.getBalanceValue());
    }

    @Test
    public void CloseInvestmentBeforeExpireDate() throws BankException
    {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 1);
        Date expireDate = calendar.getTime();
        Investment investment = new Investment(_ownerId, _balance, expireDate, _interest, _account);

        CloseInvestmentOperation operation = new CloseInvestmentOperation(investment);
        operation.execute();
        assertEquals(_balance.add(_accountBalance), _account.getBalanceValue());
    }

}
