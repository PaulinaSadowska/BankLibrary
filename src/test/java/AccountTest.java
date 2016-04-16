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