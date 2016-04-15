import Bank.BankException;
import Operations.OperationType;
import Operations.PaymentOperation;
import Operations.PaymentDirection;
import Operations.TransferOperation;
import Products.Account;
import Products.Debit;
import Products.Interest;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.*;

import static Utils.ProductFactory.createAccount;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by arasz on 18.03.2016.
 */
public class AccountTest
{
    private Account _account;

    @Test
    public void  paymentIn500_ThenBalanceIncreasedBy500() throws IllegalAccessException, InstantiationException, InvocationTargetException, BankException
    {
        int balance = 500;
        int paymentValue = 100;
        int expectedValue = balance+paymentValue;

        //Inicjalizacja
        _account = createAccount(balance);

        BigDecimal expectedAmount = new BigDecimal(expectedValue);
        BigDecimal paymentAmount = new BigDecimal(paymentValue);

        //Test
        _account.doOperation(new PaymentOperation(_account, PaymentDirection.In, paymentAmount, OperationType.Payment));
        //Sprawdzenie
        Assert.assertEquals(expectedAmount, _account.getBalance());
    }

    @Test(expected = BankException.class)
    public void paymentInNegativeAmount_ThenThrowsException() throws IllegalAccessException, InstantiationException, InvocationTargetException, BankException
    {
        int balance = 0;
        int paymentValue = -1000;

        //Inicjalizacja
        BigDecimal paymentAmount = new BigDecimal(paymentValue);

        _account = createAccount(balance);
        _account.doOperation(new PaymentOperation(_account, PaymentDirection.In, paymentAmount, OperationType.Payment));
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

        _account = createAccount(balance);

        _account.doOperation(new PaymentOperation(_account, PaymentDirection.Out, paymentAmount, OperationType.Payment));

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

        _account = createAccount(balance);

        _account.doOperation(new PaymentOperation(_account, PaymentDirection.Out, paymentAmount, OperationType.Payment));
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

        _account = createAccount(balance, debit);

        _account.doOperation(new PaymentOperation(_account, PaymentDirection.Out, paymentAmount, OperationType.Payment));

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

        _account = createAccount(balance, debit);

        _account.doOperation(new PaymentOperation(_account, PaymentDirection.Out, paymentAmount, OperationType.Payment));

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

        _account = createAccount(balance, debit);

        _account.doOperation(new PaymentOperation(_account, PaymentDirection.Out, paymentAmount, OperationType.Payment));
    }

    @Test(expected = NullPointerException.class)
    public void makeTransferWithNullAccountArgument_ThenThrow() throws BankException
    {
        int balance = 500;
        int transferValue = 200;

        BigDecimal transferAmount = new BigDecimal(transferValue);

        _account = createAccount(balance);

        _account.doOperation(new TransferOperation(_account, null, transferAmount, OperationType.Payment));
    }

    @Test(expected = BankException.class)
    public void makeTransferForAmountGreaterThanBalanceNoDebit_ThenThrowBankException() throws BankException
    {
        int balance = 500;
        int transferValue = 600;

        BigDecimal transferAmount = new BigDecimal(transferValue);

        _account = createAccount(balance);
        Account targetAccount = createAccount(balance);

        _account.doOperation(new TransferOperation(_account, targetAccount, transferAmount, OperationType.Payment));
    }

    @Test(expected = BankException.class)
    public void makeTransferForAmountGreaterThanBalancePlusDebit_ThenThrowBankException() throws BankException
    {
        int debit = 100;
        int balance = 500;
        int transferValue = 610;

        BigDecimal transferAmount = new BigDecimal(transferValue);

        _account = createAccount(balance, debit);
        Account targetAccount = createAccount(balance);

        _account.doOperation(new TransferOperation(_account, targetAccount, transferAmount, OperationType.Payment));
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

        _account = createAccount(balance, debit);
        Account targetAccount = createAccount(balance);

        _account.doOperation(new TransferOperation(_account, targetAccount, transferAmount, OperationType.Payment));

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

        _account = createAccount(balance, debit);
        Account targetAccount = createAccount(balance);

        _account.doOperation(new TransferOperation(_account, targetAccount, transferAmount, OperationType.Payment));

        Assert.assertEquals(expectedLocalBalance, _account.getBalance());
        Assert.assertEquals(expectedTargetBalance, targetAccount.getBalance());
    }

    @Test(expected = BankException.class)
    public void makeTransfer_wrongAmount_ThrowsException() throws BankException
    {
        int debit = 100;
        int balance = 680;
        int transferValue = -600;

        BigDecimal transferAmount = new BigDecimal(transferValue);

        _account = createAccount(balance, debit);
        Account targetAccount = createAccount(balance);

        _account.doOperation(new TransferOperation(_account, targetAccount, transferAmount, OperationType.Payment));
    }


}