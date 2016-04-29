import Bank.Bank;
import Bank.BankException;
import Bank.RealCentralBank;
import Operations.ICommand;
import Operations.OperationType;
import Operations.TransferOperation;
import Products.Account;
import Products.ProductManager;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

import static Utils.ProductFactory.createAccount;

/**
 * Created by arasz on 16.04.2016.
 */
public class TransferOperationTest
{
    private Account account;

    @Test
    public void undoTransfer_CorrectAmountAndAccounts_BothBalancesDoNotChanges() throws Exception
    {
        int balance = 500;
        int transferValue = 100;

        BigDecimal transferAmount = new BigDecimal(transferValue);

        account = createAccount(balance);
        Account targetAccount = createAccount(balance);

        ICommand operation = new TransferOperation(account, targetAccount, transferAmount, OperationType.Transfer);
        operation.execute();

        operation.undo();

        BigDecimal balanceValue = new BigDecimal(balance);

        Assert.assertEquals(balanceValue, targetAccount.getBalance());
        Assert.assertEquals(balanceValue, account.getBalance());
    }

    @Test(expected = NullPointerException.class)
    public void makeTransfer_NullAccountArgument_ThrowBankException() throws Exception
    {
        int balance = 500;
        int transferValue = 200;

        BigDecimal transferAmount = new BigDecimal(transferValue);

        account = createAccount(balance);

        ICommand operation = new TransferOperation(account, null, transferAmount, OperationType.Payment);
        operation.execute();
    }

    @Test(expected = BankException.class)
    public void makeTransfer_AmountGreaterThanBalanceNoDebit_BalancesNotChanged() throws Exception
    {
        int balance = 500;
        int transferValue = 600;

        BigDecimal transferAmount = new BigDecimal(transferValue);

        account = createAccount(balance);
        Account targetAccount = createAccount(balance);

        ICommand operation = new TransferOperation(account, targetAccount, transferAmount, OperationType.Transfer);
        assertOperationUndone(balance, targetAccount, operation);
    }

    @Test(expected = BankException.class)
    public void makeTransfer_AmountGreaterThanBalancePlusDebit_ThrowBankException() throws Exception
    {
        int debit = 100;
        int balance = 500;
        int transferValue = 610;

        BigDecimal transferAmount = new BigDecimal(transferValue);

        account = createAccount(balance, debit);
        Account targetAccount = createAccount(balance);

        ICommand operation = new TransferOperation(account, targetAccount, transferAmount, OperationType.Transfer);
        assertOperationUndone(balance, targetAccount, operation);
    }

    @Test
    public void makeTransfer_AmountEqualToBalancePlusDebit_BalancesChanges() throws Exception
    {
        int debit = 100;
        int balance = 500;
        int transferValue = 600;
        int localBalanceAfterTransfer = -debit;
        int targetBalanceAfterTransfer = transferValue + balance;

        BigDecimal transferAmount = new BigDecimal(transferValue);
        BigDecimal expectedTargetBalance = new BigDecimal(targetBalanceAfterTransfer);
        BigDecimal expectedLocalBalance = new BigDecimal(localBalanceAfterTransfer);

        account = createAccount(balance, debit);
        Account targetAccount = createAccount(balance);

        ICommand operation = new TransferOperation(account, targetAccount, transferAmount, OperationType.Transfer);
        operation.execute();

        Assert.assertEquals(expectedLocalBalance, account.getBalance());
        Assert.assertEquals(expectedTargetBalance, targetAccount.getBalance());
    }

    @Test
    public void makeTransfer_AmountLessThanBalancePlusDebit_BalancesChanges() throws Exception
    {
        int debit = 100;
        int balance = 680;
        int transferValue = 600;
        int localBalanceAfterTransfer = balance - transferValue;
        int targetBalanceAfterTransfer = transferValue + balance;

        BigDecimal transferAmount = new BigDecimal(transferValue);
        BigDecimal expectedTargetBalance = new BigDecimal(targetBalanceAfterTransfer);
        BigDecimal expectedLocalBalance = new BigDecimal(localBalanceAfterTransfer);

        account = createAccount(balance, debit);
        Account targetAccount = createAccount(balance);

        ICommand operation = new TransferOperation(account, targetAccount, transferAmount, OperationType.Transfer);
        operation.execute();

        Assert.assertEquals(expectedLocalBalance, account.getBalance());
        Assert.assertEquals(expectedTargetBalance, targetAccount.getBalance());
    }

    @Test(expected = BankException.class)
    public void makeTransfer_AmountLessThanZero_ThrowsException() throws Exception
    {
        int debit = 100;
        int balance = 680;
        int transferValue = -600;

        BigDecimal transferAmount = new BigDecimal(transferValue);

        account = createAccount(balance, debit);
        Account targetAccount = createAccount(balance);

        ICommand operation = new TransferOperation(account, targetAccount, transferAmount, OperationType.Payment);
        assertOperationUndone(balance, targetAccount, operation);
    }

    private void assertOperationUndone(int balance, Account targetAccount, ICommand operation) throws Exception
    {
        try
        {
            operation.execute();
        }
        catch (BankException e)
        {
            Assert.assertEquals(new BigDecimal(balance), account.getBalance());
            Assert.assertEquals(new BigDecimal(balance), targetAccount.getBalance());
            throw e;
        }
    }
}
