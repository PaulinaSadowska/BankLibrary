import Bank.BankException;
import Operations.ICommand;
import Operations.OperationType;
import Operations.TransferOperation;
import Products.Account;
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
    public void undoTransfer_CorrectAmountAndAccounts_BothBalancesDoNotChanges() throws BankException
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
    public void makeTransfer_NullAccountArgument_ThrowBankException() throws BankException
    {
        int balance = 500;
        int transferValue = 200;

        BigDecimal transferAmount = new BigDecimal(transferValue);

        account = createAccount(balance);

        ICommand operation = new TransferOperation(account, null, transferAmount, OperationType.Payment);
        operation.execute();
    }

    @Test(expected = BankException.class)
    public void makeTransfer_AmountGreaterThanBalanceNoDebit_BalancesNotChanged() throws BankException
    {
        int balance = 500;
        int transferValue = 600;

        BigDecimal transferAmount = new BigDecimal(transferValue);

        account = createAccount(balance);
        Account targetAccount = createAccount(balance);

        ICommand operation = new TransferOperation(account, targetAccount, transferAmount, OperationType.Transfer);
        asserOperationUndone(balance, targetAccount, operation);
    }

    @Test(expected = BankException.class)
    public void makeTransfer_AmountGreaterThanBalancePlusDebit_ThrowBankException() throws BankException
    {
        int debit = 100;
        int balance = 500;
        int transferValue = 610;

        BigDecimal transferAmount = new BigDecimal(transferValue);

        account = createAccount(balance, debit);
        Account targetAccount = createAccount(balance);

        ICommand operation = new TransferOperation(account, targetAccount, transferAmount, OperationType.Transfer);
        asserOperationUndone(balance, targetAccount, operation);
    }

    @Test
    public void makeTransfer_AmountEqualToBalancePlusDebit_BalancesChanges() throws BankException
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
    public void makeTransfer_AmountLessThanBalancePlusDebit_BalancesChanges() throws BankException
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
    public void makeTransfer_AmountLessThanZero_ThrowsException() throws BankException
    {
        int debit = 100;
        int balance = 680;
        int transferValue = -600;

        BigDecimal transferAmount = new BigDecimal(transferValue);

        account = createAccount(balance, debit);
        Account targetAccount = createAccount(balance);

        ICommand operation = new TransferOperation(account, targetAccount, transferAmount, OperationType.Payment);
        asserOperationUndone(balance, targetAccount, operation);
    }

    private void asserOperationUndone(int balance, Account targetAccount, ICommand operation) throws BankException
    {
        try
        {
            operation.execute();
        }
        catch (BankException e)
        {
            Assert.assertEquals(new BigDecimal(balance) ,account.getBalance());
            Assert.assertEquals(new BigDecimal(balance) ,targetAccount.getBalance());
            throw e;
        }
    }
}
