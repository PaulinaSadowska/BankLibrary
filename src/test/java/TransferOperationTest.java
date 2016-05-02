import Bank.BankException;
import Operations.ICommand;
import Operations.OperationType;
import Operations.TransferOperation;
import Products.Account;
import Products.Debit;
import Products.DebitAccount;
import Products.IAccount;
import com.sun.org.apache.bcel.internal.generic.IADD;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

import static Utils.ProductFactory.createAccount;

/**
 * Created by arasz on 16.04.2016.
 */
public class TransferOperationTest
{
    private IAccount account;

    @Test
    public void undoTransfer_CorrectAmountAndAccounts_BothBalancesDoNotChanges() throws Exception
    {
        int balance = 500;
        int transferValue = 100;

        BigDecimal transferAmount = new BigDecimal(transferValue);

        account = createAccount(balance);
        IAccount targetAccount = createAccount(balance);

        ICommand operation = new TransferOperation(account, targetAccount, transferAmount, OperationType.Transfer);
        operation.execute();

        operation.undo();

        BigDecimal balanceValue = new BigDecimal(balance);

        Assert.assertEquals(balanceValue, targetAccount.getBalanceValue());
        Assert.assertEquals(balanceValue, account.getBalanceValue());
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
        IAccount targetAccount = createAccount(balance);

        ICommand operation = new TransferOperation(account, targetAccount, transferAmount, OperationType.Transfer);
        asserOperationUndone(balance, targetAccount, operation);
    }

    @Test(expected = BankException.class)
    public void makeTransfer_AmountGreaterThanBalancePlusDebit_ThrowBankException() throws Exception
    {
        int debit = 100;
        int balance = 500;
        int transferValue = 610;

        BigDecimal transferAmount = new BigDecimal(transferValue);

        account = createAccount(balance, debit);
        IAccount targetAccount = createAccount(balance);

        ICommand operation = new TransferOperation(account, targetAccount, transferAmount, OperationType.Transfer);
        asserOperationUndone(balance, targetAccount, operation);
    }

    @Test
    public void makeTransfer_AmountEqualToBalancePlusDebit_BalancesChanges() throws Exception
    {
        int debit = 100;
        int balance = 500;
        int transferValue = 600;
        int localBalanceAfterTransfer = 0;
        int targetBalanceAfterTransfer = transferValue + balance;

        BigDecimal transferAmount = new BigDecimal(transferValue);
        BigDecimal expectedTargetBalance = new BigDecimal(targetBalanceAfterTransfer);
        BigDecimal expectedLocalBalance = new BigDecimal(localBalanceAfterTransfer);
        BigDecimal expectedLocalDebit = new BigDecimal(debit+balance - transferValue);

        account = createAccount(balance, debit);
        Debit localDebit = ((DebitAccount)account).getDebit();
        IAccount targetAccount = createAccount(balance);

        ICommand operation = new TransferOperation(account, targetAccount, transferAmount, OperationType.Transfer);
        operation.execute();

        Assert.assertEquals(expectedLocalBalance, account.getBalanceValue());
        Assert.assertEquals(expectedTargetBalance, targetAccount.getBalanceValue());
        Assert.assertEquals(expectedLocalDebit, localDebit.getBalanceValue());
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
        IAccount targetAccount = createAccount(balance);

        ICommand operation = new TransferOperation(account, targetAccount, transferAmount, OperationType.Transfer);
        operation.execute();

        Assert.assertEquals(expectedLocalBalance, account.getBalanceValue());
        Assert.assertEquals(expectedTargetBalance, targetAccount.getBalanceValue());
    }

    @Test(expected = BankException.class)
    public void makeTransfer_AmountLessThanZero_ThrowsException() throws Exception
    {
        int debit = 100;
        int balance = 680;
        int transferValue = -600;

        BigDecimal transferAmount = new BigDecimal(transferValue);

        account = createAccount(balance, debit);
        IAccount targetAccount = createAccount(balance);

        ICommand operation = new TransferOperation(account, targetAccount, transferAmount, OperationType.Payment);
        asserOperationUndone(balance, targetAccount, operation);
    }

    private void asserOperationUndone(int balance, IAccount targetAccount, ICommand operation) throws Exception
    {
        try
        {
            operation.execute();
        }
        catch (BankException e)
        {
            Assert.assertEquals(new BigDecimal(balance) ,account.getBalanceValue());
            Assert.assertEquals(new BigDecimal(balance) ,targetAccount.getBalanceValue());
            throw e;
        }
    }
}
