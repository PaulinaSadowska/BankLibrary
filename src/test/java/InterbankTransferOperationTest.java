import Bank.*;
import Operations.ICommand;
import Operations.InterbankTransferOperation;
import Products.*;

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
    private RealCentralBank _centralBank;
    private Account sourceAccount;
    private Bank _bank1;
    private Bank _bank2;
    private BigDecimal _sourceAccountBalance;

    @Before
    public void setUp() throws Exception
    {
        _centralBank = new RealCentralBank();
        _bank1 = new Bank(new ProductManager());
        _bank2 = new Bank(new ProductManager());
        _centralBank.registerBank(_bank1);
        _centralBank.registerBank(_bank2);
        _sourceAccountBalance = new BigDecimal(1200);
        sourceAccount = _bank1.createAccount(_sourceAccountBalance, new ProductDuration(1, 2),
                mock(IInterestCalculationStrategy.class), 1.2);
    }

    @Test
    public void makeInterbankTransfer_CorrectAmountAccountAndIds_BothBalancesChanged() throws Exception
    {
        BigDecimal targetAccountBalance = new BigDecimal(1400);
        BigDecimal amount = new BigDecimal(200);
        Account targetAccount = _bank2.createAccount(targetAccountBalance, new ProductDuration(2, 3),
                mock(IInterestCalculationStrategy.class), 1.3);

        InterbankTransferOperation transfer = new InterbankTransferOperation(_centralBank, sourceAccount,
                targetAccount.getOwnerId(), targetAccount.getBankId(), amount);
        transfer.execute();

        BigDecimal expectedTargetBalance = targetAccountBalance.add(amount);
        BigDecimal expectedSourceBalance = _sourceAccountBalance.subtract(amount);

        assertEquals(expectedTargetBalance, targetAccount.getBalance());
        assertEquals(expectedSourceBalance, sourceAccount.getBalance());
    }

    @Test
    public void makeInterbankTransfer_TargetAccountDontExist_BalanceDontChange() throws Exception
    {
        BigDecimal amount = new BigDecimal(200);
        int dummyOwnerId = 134;

        InterbankTransferOperation transfer = new InterbankTransferOperation(_centralBank, sourceAccount,
                dummyOwnerId, _bank2.getId(), amount);
        transfer.execute();

        assertEquals(_sourceAccountBalance, sourceAccount.getBalance());
    }

    @Test
    public void makeInterbankTransfer_WrongBankId_BalanceDontChange() throws Exception
    {
        BigDecimal targetAccountBalance = new BigDecimal(1400);
        BigDecimal amount = new BigDecimal(200);
        Account targetAccount = _bank2.createAccount(targetAccountBalance, new ProductDuration(2, 3),
                mock(IInterestCalculationStrategy.class), 1.3);
        int dummyBankId = 134;

        InterbankTransferOperation transfer = new InterbankTransferOperation(_centralBank, sourceAccount,
                targetAccount.getOwnerId(), dummyBankId, amount);
        transfer.execute();

        assertEquals(_sourceAccountBalance, sourceAccount.getBalance());
        assertEquals(targetAccount.getBalance(), targetAccountBalance);
    }

    @Test(expected = BankException.class)
    public void makeInterbankTransfer_AmountGreaterThanBalanceNoDebit_BothBalancesDoesNotChange() throws Exception
    {
        BigDecimal targetAccountBalance = new BigDecimal(1400);
        BigDecimal amount = _sourceAccountBalance.add(new BigDecimal(100));
        Account targetAccount = _bank2.createAccount(targetAccountBalance, new ProductDuration(2, 3),
                mock(IInterestCalculationStrategy.class), 1.3);

        InterbankTransferOperation transfer = new InterbankTransferOperation(_centralBank, sourceAccount,
                targetAccount.getOwnerId(), targetAccount.getBankId(), amount);
        transfer.execute();

        assertEquals(_sourceAccountBalance, sourceAccount.getBalance());
        assertEquals(targetAccount.getBalance(), targetAccountBalance);
    }

    @Test(expected = NullPointerException.class)
    public void makeInterbankTransfer_NullAccountArgument_ThrowBankException() throws Exception
    {
        int transferValue = 200;
        BigDecimal transferAmount = new BigDecimal(transferValue);


        ICommand operation = new InterbankTransferOperation(_centralBank, null, sourceAccount.getOwnerId(),
                sourceAccount.getBankId(), transferAmount);
        operation.execute();
        assertEquals(sourceAccount.getBalance(), _sourceAccountBalance);
    }

    @Test(expected = NullPointerException.class)
    public void makeInterbankTransfer_NullCentralBankArgument_ThrowBankException() throws Exception
    {
        int transferValue = 200;
        BigDecimal transferAmount = new BigDecimal(transferValue);
        BigDecimal targetAccountBalance = new BigDecimal(1400);
        Account targetAccount = _bank2.createAccount(targetAccountBalance, new ProductDuration(2, 3),
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
        assertEquals(_sourceAccountBalance.add(debitValue), sourceAccount.getBalanceWithDebit());

        BigDecimal targetAccountBalance = new BigDecimal(1400);
        Account targetAccount = _bank2.createAccount(targetAccountBalance, new ProductDuration(2, 3),
                mock(IInterestCalculationStrategy.class), 1.3);
        assertEquals(targetAccount.getBalance(), targetAccountBalance);
        BigDecimal amount = sourceAccount.getBalanceWithDebit().add(new BigDecimal(10));

        ICommand operation = new InterbankTransferOperation(_centralBank, sourceAccount, targetAccount.getOwnerId(),
                targetAccount.getBankId(), amount);

        assertOperationUndone(targetAccountBalance.intValue(), targetAccount, operation);
        assertEquals(_sourceAccountBalance.add(debitValue), sourceAccount.getBalanceWithDebit());
    }

    @Test
    public void makeTransfer_AmountEqualToBalancePlusDebit_BalancesChanges() throws Exception
    {
        BigDecimal debitValue = new BigDecimal(1000);
        assertFalse(sourceAccount.hasDebit());
        sourceAccount.setDebit(new Debit(debitValue));
        assertTrue(sourceAccount.hasDebit());
        assertEquals(_sourceAccountBalance.add(debitValue), sourceAccount.getBalanceWithDebit());

        BigDecimal targetAccountBalance = new BigDecimal(1400);
        Account targetAccount = _bank2.createAccount(targetAccountBalance, new ProductDuration(2, 3),
                mock(IInterestCalculationStrategy.class), 1.3);
        assertEquals(targetAccount.getBalance(), targetAccountBalance);
        BigDecimal amount = sourceAccount.getBalanceWithDebit();

        ICommand operation = new InterbankTransferOperation(_centralBank, sourceAccount, targetAccount.getOwnerId(),
                targetAccount.getBankId(), amount);
        operation.execute();
        assertEquals(_sourceAccountBalance.add(debitValue).subtract(amount), sourceAccount.getBalanceWithDebit());
        assertEquals(targetAccountBalance.add(amount), targetAccount.getBalance());
    }

    @Test
    public void makeTransfer_AmountLessThanBalancePlusDebit_BalancesChanges() throws Exception
    {
        BigDecimal debitValue = new BigDecimal(1000);
        assertFalse(sourceAccount.hasDebit());
        sourceAccount.setDebit(new Debit(debitValue));
        assertTrue(sourceAccount.hasDebit());
        assertEquals(_sourceAccountBalance.add(debitValue), sourceAccount.getBalanceWithDebit());

        BigDecimal targetAccountBalance = new BigDecimal(1400);
        Account targetAccount = _bank2.createAccount(targetAccountBalance, new ProductDuration(2, 3),
                mock(IInterestCalculationStrategy.class), 1.3);
        assertEquals(targetAccount.getBalance(), targetAccountBalance);
        BigDecimal amount = sourceAccount.getBalanceWithDebit().subtract(new BigDecimal(50));

        ICommand operation = new InterbankTransferOperation(_centralBank, sourceAccount, targetAccount.getOwnerId(),
                targetAccount.getBankId(), amount);
        operation.execute();
        assertEquals(_sourceAccountBalance.add(debitValue).subtract(amount), sourceAccount.getBalanceWithDebit());
        assertEquals(targetAccountBalance.add(amount), targetAccount.getBalance());
    }

    @Test(expected = BankException.class)
    public void makeTransfer_AmountLessThanZero_ThrowsException() throws Exception
    {
        BigDecimal targetAccountBalance = new BigDecimal(680);
        int transferValue = -600;

        BigDecimal transferAmount = new BigDecimal(transferValue);

        Account targetAccount = _bank2.createAccount(targetAccountBalance, new ProductDuration(2, 3),
                mock(IInterestCalculationStrategy.class), 1.3);

        ICommand operation = new InterbankTransferOperation(_centralBank, sourceAccount, targetAccount.getOwnerId(),
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
            Assert.assertEquals(_sourceAccountBalance, sourceAccount.getBalance());
            Assert.assertEquals(new BigDecimal(balance), targetAccount.getBalanceWithDebit());
            throw e;
        }
    }

}
