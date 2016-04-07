import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.*;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by arasz on 18.03.2016.
 */
public class AccountTest
{
    private Account _account;

    private Account createInstance(int balance)
    {
        Interest intrestMock = mock(Interest.class);
        when(intrestMock.calculateInterest(mock(Account.class))).thenReturn(new BigDecimal(100));

        return new Account(12, new BigDecimal(balance), mock(Date.class), intrestMock);
    }

    private Account createInstance(int balance, int debitValue)
    {
        Interest intrestMock = mock(Interest.class);
        when(intrestMock.calculateInterest(mock(Account.class))).thenReturn(new BigDecimal(100));

        Debit debitMock = mock(Debit.class);
        when(debitMock.getDebitValue()).thenReturn(new BigDecimal(debitValue));

        return new Account(12, new BigDecimal(balance), mock(Date.class), intrestMock, debitMock);
    }

    @Test
    public void  paymentIn500_ThenBalanceIncreasedBy500() throws IllegalAccessException, InstantiationException, InvocationTargetException, BankException
    {
        int balance = 500;
        int paymentValue = 100;
        int expectedValue = balance+paymentValue;

        //Inicjalizacja
        _account = createInstance(balance);

        BigDecimal expectedAmount = new BigDecimal(expectedValue);
        BigDecimal paymentAmount = new BigDecimal(paymentValue);

        //Test
        _account.payment(paymentAmount, PaymentDirection.In);
        //Sprawdzenie
        Assert.assertEquals(expectedAmount, _account.getBalance());
    }

    @Test(expected = IllegalArgumentException.class)
    public void paymentInNegativeAmount_ThenThrowsException() throws IllegalAccessException, InstantiationException, InvocationTargetException, BankException
    {
        int balance = 0;
        int paymentValue = -1000;

        //Inicjalizacja
        BigDecimal paymentAmount = new BigDecimal(paymentValue);

        _account = createInstance(balance);
        _account.payment(paymentAmount, PaymentDirection.In);
    }

    @Test
    public void paymentOutAmountLessThanBalance_ThenBalanceDecreasedByAmount() throws IllegalAccessException, InstantiationException, InvocationTargetException, BankException
    {
        int balance = 500;
        int paymentValue = 400;
        int expectedValue = balance - paymentValue;

        //Inicjalizacja

        BigDecimal expectedAmount = new BigDecimal(expectedValue);
        BigDecimal paymentAmount = new BigDecimal(paymentValue);

        _account = createInstance(balance);

        _account.payment(paymentAmount, PaymentDirection.Out);

        Assert.assertEquals(expectedAmount, _account.getBalance());
    }

    @Test(expected = BankException.class)
    public void paymentOutAmountGreaterThanBalanceNoDebit_ThenThrowsBankException() throws IllegalAccessException, InvocationTargetException, InstantiationException, BankException
    {
        int balance = 500;
        int paymentValue = 600;
        int expectedValue = balance;

        //Inicjalizacja

        BigDecimal expectedAmount = new BigDecimal(expectedValue);
        BigDecimal paymentAmount = new BigDecimal(paymentValue);

        _account = createInstance(balance);

        _account.payment(paymentAmount, PaymentDirection.Out);
    }

    @Test
    public void paymentOutAmountLessThanBalancePlusDebit_ThenBalanceDecreasedByAmount() throws IllegalAccessException, InvocationTargetException, InstantiationException, BankException
    {
        int debit = 200;
        int balance = 500;
        int paymentValue = 600;
        int expectedValue = balance - paymentValue;

        BigDecimal paymentAmount = new BigDecimal(paymentValue);
        BigDecimal expectedAmount = new BigDecimal(expectedValue);

        _account = createInstance(balance, debit);

        _account.payment(paymentAmount, PaymentDirection.Out);

        Assert.assertEquals(expectedAmount, _account.getBalance());
    }


    @Test
    public void paymentOutAmountEqualToBalancePlusDebit_ThenBalanceEqualsMinusDebit() throws IllegalAccessException, InvocationTargetException, InstantiationException, BankException
    {
        int debit = 100;
        int balance = 500;
        int paymentValue = 600;

        BigDecimal paymentAmount = new BigDecimal(paymentValue);
        BigDecimal expectedAmount = new BigDecimal(-debit);

        _account = createInstance(balance, debit);

        _account.payment(paymentAmount, PaymentDirection.Out);

        Assert.assertEquals(expectedAmount, _account.getBalance());
    }


    @Test(expected = BankException.class)
    public void paymentOutAmountGreaterThanBalancePlusDebit_ThenThrowsBankException() throws IllegalAccessException, InvocationTargetException, InstantiationException, BankException
    {
        int debit = 50;
        int balance = 500;
        int paymentValue = 600;
        int expectrdValue = balance ;

        BigDecimal paymentAmount = new BigDecimal(paymentValue);

        _account = createInstance(balance, debit);

        _account.payment(paymentAmount, PaymentDirection.Out);
    }

    @Test(expected = NullPointerException.class)
    public void makeTransferWithNullAccountArgument_ThenThrow() throws BankException
    {
        int balance = 500;
        int transferValue = 200;

        BigDecimal transferAmount = new BigDecimal(transferValue);

        _account = createInstance(balance);

        _account.transfer(transferAmount, null);
    }

    @Test(expected = BankException.class)
    public void makeTransferForAmountGreaterThanBalanceNoDebit_ThenThrowBankException() throws BankException
    {
        int balance = 500;
        int transferValue = 600;

        BigDecimal transferAmount = new BigDecimal(transferValue);

        _account = createInstance(balance);
        Account targetAccount = createInstance(balance);

        _account.transfer(transferAmount, targetAccount);
    }

    @Test(expected = BankException.class)
    public void makeTransferForAmountGreaterThanBalancePlusDebit_ThenThrowBankException() throws BankException
    {
        int debit = 100;
        int balance = 500;
        int transferValue = 610;

        BigDecimal transferAmount = new BigDecimal(transferValue);

        _account = createInstance(balance, debit);
        Account targetAccount = createInstance(balance);

        _account.transfer(transferAmount, targetAccount);
    }

    @Test
    public void makeTransferForAmountEqualToBalancePlusDebit_ThenBalancesChanges() throws BankException
    {
        int debit = 100;
        int balance = 500;
        int transferValue = 600;
        int localBalanceAfterTransfer = -debit;
        int targetBalanceAfterTransfer = transferValue + balance;

        BigDecimal transferAmount = new BigDecimal(transferValue);
        BigDecimal expectedTargetBalance = new BigDecimal(targetBalanceAfterTransfer);
        BigDecimal expectedLocalBalance = new BigDecimal(localBalanceAfterTransfer);

        _account = createInstance(balance, debit);
        Account targetAccount = createInstance(balance);

        _account.transfer(transferAmount, targetAccount);

        Assert.assertEquals(expectedLocalBalance, _account.getBalance());
        Assert.assertEquals(expectedTargetBalance, targetAccount.getBalance());
    }

    @Test
    public void makeTransferForAmountLessThanBalancePlusDebit_ThenBalancesChanges() throws BankException
    {
        int debit = 100;
        int balance = 680;
        int transferValue = 600;
        int localBalanceAfterTransfer = balance - transferValue;
        int targetBalanceAfterTransfer = transferValue + balance;

        BigDecimal transferAmount = new BigDecimal(transferValue);
        BigDecimal expectedTargetBalance = new BigDecimal(targetBalanceAfterTransfer);
        BigDecimal expectedLocalBalance = new BigDecimal(localBalanceAfterTransfer);

        _account = createInstance(balance, debit);
        Account targetAccount = createInstance(balance);

        _account.transfer(transferAmount, targetAccount);

        Assert.assertEquals(expectedLocalBalance, _account.getBalance());
        Assert.assertEquals(expectedTargetBalance, targetAccount.getBalance());
    }

    @Test(expected = BankException.class)
    public void makeTransferForWrongAmount_ThenThrowsException() throws BankException
    {
        int debit = 100;
        int balance = 680;
        int transferValue = -600;

        BigDecimal transferAmount = new BigDecimal(transferValue);

        _account = createInstance(balance, debit);
        Account targetAccount = createInstance(balance);

        _account.transfer(transferAmount, targetAccount);

    }


}