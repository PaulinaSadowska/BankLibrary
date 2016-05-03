import Bank.*;
import Operations.ICommand;
import Operations.InterbankTransferOperation;
import Products.*;

import Products.Balance.Balance;
import Utils.IInterestCalculationStrategy;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;


/**
 * Created by Paulina Sadowska on 29.04.2016.
 */
public class InterbankTransferOperationTest
{
    private RealCentralBank centralBank;
    private IAccount sourceAccount;
    private Bank bank;
    private Bank bank2;
    private BigDecimal sourceAccountBalance;

    @Before
    public void setUp() throws Exception
    {
        centralBank = new RealCentralBank();
        bank = new Bank(new ProductManager());
        bank2 = new Bank(new ProductManager());
        centralBank.registerBank(bank);
        centralBank.registerBank(bank2);
        sourceAccountBalance = new BigDecimal(1200);
        sourceAccount = bank.createAccount(new Balance(sourceAccountBalance), new ProductDuration(1, 2),
                mock(IInterestCalculationStrategy.class), 1.2);
    }

    @Test
    public void makeInterbankTransfer_CorrectAmountAccountAndIds_BothBalancesChanged() throws Exception
    {
        Balance targetAccountBalance = new Balance(new BigDecimal(1400));
        BigDecimal amount = new BigDecimal(200);
        IAccount targetAccount = bank2.createAccount(targetAccountBalance, new ProductDuration(2, 3),
                mock(IInterestCalculationStrategy.class), 1.3);

        InterbankTransferOperation transfer = new InterbankTransferOperation(centralBank, sourceAccount,
                targetAccount.getOwnerId(), targetAccount.getBankId(), amount);
        transfer.execute();

        BigDecimal expectedTargetBalance = targetAccountBalance.add(amount);
        BigDecimal expectedSourceBalance = sourceAccountBalance.subtract(amount);

        assertEquals(expectedTargetBalance, targetAccount.getBalance());
        assertEquals(expectedSourceBalance, sourceAccount.getBalance());
    }

    @Test
    public void makeInterbankTransfer_TargetAccountDontExist_BalanceDontChange() throws Exception
    {
        BigDecimal amount = new BigDecimal(200);
        int dummyOwnerId = 134;

        InterbankTransferOperation transfer = new InterbankTransferOperation(centralBank, sourceAccount,
                dummyOwnerId, bank2.getId(), amount);
        transfer.execute();

        assertEquals(sourceAccountBalance, sourceAccount.getBalance());
    }

    @Test
    public void makeInterbankTransfer_WrongBankId_BalanceDontChange() throws Exception
    {
        Balance targetAccountBalance = new Balance(new BigDecimal(1400));
        BigDecimal amount = new BigDecimal(200);
        IAccount targetAccount = bank2.createAccount(targetAccountBalance, new ProductDuration(2, 3),
                mock(IInterestCalculationStrategy.class), 1.3);
        int dummyBankId = 134;

        InterbankTransferOperation transfer = new InterbankTransferOperation(centralBank, sourceAccount,
                targetAccount.getOwnerId(), dummyBankId, amount);
        transfer.execute();

        assertEquals(sourceAccountBalance, sourceAccount.getBalance());
        assertEquals(targetAccount.getBalance(), targetAccountBalance);
    }

    @Test(expected = BankException.class)
    public void makeInterbankTransfer_AmountGreaterThanBalanceNoDebit_BothBalancesDoesNotChange() throws Exception
    {
        Balance targetAccountBalance = new Balance(new BigDecimal(1400));
        BigDecimal amount = sourceAccountBalance.add(new BigDecimal(100));
        IAccount targetAccount = bank2.createAccount(targetAccountBalance, new ProductDuration(2, 3),
                mock(IInterestCalculationStrategy.class), 1.3);

        InterbankTransferOperation transfer = new InterbankTransferOperation(centralBank, sourceAccount,
                targetAccount.getOwnerId(), targetAccount.getBankId(), amount);
        transfer.execute();

        assertEquals(sourceAccountBalance, sourceAccount.getBalance());
        assertEquals(targetAccount.getBalance(), targetAccountBalance);
    }

    @Test(expected = NullPointerException.class)
    public void makeInterbankTransfer_NullAccountArgument_ThrowBankException() throws Exception
    {
        int transferValue = 200;
        BigDecimal transferAmount = new BigDecimal(transferValue);


        ICommand operation = new InterbankTransferOperation(centralBank, null, sourceAccount.getOwnerId(),
                sourceAccount.getBankId(), transferAmount);
        operation.execute();
        assertEquals(sourceAccount.getBalance(), sourceAccountBalance);
    }

    @Test(expected = NullPointerException.class)
    public void makeInterbankTransfer_NullCentralBankArgument_ThrowBankException() throws Exception
    {
        int transferValue = 200;
        BigDecimal transferAmount = new BigDecimal(transferValue);
        Balance targetAccountBalance = new Balance(new BigDecimal(1400));
        IAccount targetAccount = bank2.createAccount(targetAccountBalance, new ProductDuration(2, 3),
                mock(IInterestCalculationStrategy.class), 1.3);


        ICommand operation = new InterbankTransferOperation(null, sourceAccount, targetAccount.getOwnerId(),
                targetAccount.getBankId(), transferAmount);
        assertOperationUndone(targetAccountBalance.intValue(), targetAccount, operation);
    }


    @Test(expected = BankException.class)
    public void makeTransfer_AmountGreaterThanBalancePlusDebit_ThrowBankException() throws Exception
    {
        BigDecimal debitValue = new BigDecimal(1000);
        assertFalse(sourceAccount.hasDebit());
        sourceAccount.setDebit(new Debit(debitValue));
        assertTrue(sourceAccount.hasDebit());
        assertEquals(sourceAccountBalance.add(debitValue), sourceAccount.getBalanceWithDebit());

        BigDecimal targetAccountBalance = new BigDecimal(1400);
        Account targetAccount = bank2.createAccount(targetAccountBalance, new ProductDuration(2, 3),
                mock(IInterestCalculationStrategy.class), 1.3);
        assertEquals(targetAccount.getBalance(), targetAccountBalance);
        BigDecimal amount = sourceAccount.getBalanceWithDebit().add(new BigDecimal(10));

        ICommand operation = new InterbankTransferOperation(centralBank, sourceAccount, targetAccount.getOwnerId(),
                targetAccount.getBankId(), amount);

        assertOperationUndone(targetAccountBalance.intValue(), targetAccount, operation);
        assertEquals(sourceAccountBalance.add(debitValue), sourceAccount.getBalanceWithDebit());
    }

    @Test
    public void makeTransfer_AmountEqualToBalancePlusDebit_BalancesChanges() throws Exception
    {
        BigDecimal debitValue = new BigDecimal(1000);
        assertFalse(sourceAccount.hasDebit());
        sourceAccount.setDebit(new Debit(debitValue));
        assertTrue(sourceAccount.hasDebit());
        assertEquals(sourceAccountBalance.add(debitValue), sourceAccount.getBalanceWithDebit());

        BigDecimal targetAccountBalance = new BigDecimal(1400);
        IAccount targetAccount = bank2.createAccount(targetAccountBalance, new ProductDuration(2, 3),
                mock(IInterestCalculationStrategy.class), 1.3);
        assertEquals(targetAccount.getBalance(), targetAccountBalance);
        BigDecimal amount = sourceAccount.getBalanceWithDebit();

        ICommand operation = new InterbankTransferOperation(centralBank, sourceAccount, targetAccount.getOwnerId(),
                targetAccount.getBankId(), amount);
        operation.execute();
        assertEquals(sourceAccountBalance.add(debitValue).subtract(amount), sourceAccount.getBalanceWithDebit());
        assertEquals(targetAccountBalance.add(amount), targetAccount.getBalance());
    }

    @Test
    public void makeTransfer_AmountLessThanBalancePlusDebit_BalancesChanges() throws Exception
    {
        BigDecimal debitValue = new BigDecimal(1000);
        assertFalse(sourceAccount.hasDebit());
        sourceAccount.setDebit(new Debit(debitValue));
        assertTrue(sourceAccount.hasDebit());
        assertEquals(sourceAccountBalance.add(debitValue), sourceAccount.getBalanceWithDebit());

        Balance targetAccountBalance = new Balance(new BigDecimal(1400));
        IAccount targetAccount = bank2.createAccount(targetAccountBalance, new ProductDuration(2, 3),
                mock(IInterestCalculationStrategy.class), 1.3);
        assertEquals(targetAccount.getBalance(), targetAccountBalance);
        BigDecimal amount = sourceAccount.getBalanceWithDebit().subtract(new BigDecimal(50));

        ICommand operation = new InterbankTransferOperation(centralBank, sourceAccount, targetAccount.getOwnerId(),
                targetAccount.getBankId(), amount);
        operation.execute();
        assertEquals(sourceAccountBalance.add(debitValue).subtract(amount), sourceAccount.getBalanceWithDebit());
        assertEquals(targetAccountBalance.add(amount), targetAccount.getBalance());
    }

    @Test(expected = BankException.class)
    public void makeTransfer_AmountLessThanZero_ThrowsException() throws Exception
    {
        Balance targetAccountBalance = new Balance(new BigDecimal(680));
        int transferValue = -600;

        BigDecimal transferAmount = new BigDecimal(transferValue);

        IAccount targetAccount = bank2.createAccount(targetAccountBalance, new ProductDuration(2, 3),
                mock(IInterestCalculationStrategy.class), 1.3);

        ICommand operation = new InterbankTransferOperation(centralBank, sourceAccount, targetAccount.getOwnerId(),
                targetAccount.getBankId(), transferAmount);
        assertOperationUndone(targetAccountBalance.intValue(), targetAccount, operation);
    }

    private void assertOperationUndone(int balance, Account targetAccount, ICommand operation) throws Exception
    {
        try
        {
            operation.execute();
        }
        catch (BankException e)
        {
            Assert.assertEquals(sourceAccountBalance, sourceAccount.getBalance());
            Assert.assertEquals(new BigDecimal(balance), targetAccount.getBalanceWithDebit());
            throw e;
        }
    }

}
