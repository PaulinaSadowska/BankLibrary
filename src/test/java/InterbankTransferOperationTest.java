import Bank.*;
import Operations.ICommand;
import Operations.InterbankTransferOperation;
import Products.*;

import Products.Balance.Balance;
import Utils.IInterestCalculationStrategy;
import Utils.ProductFactory;
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
    private DebitAccount debitSourceAccount;
    private Bank bank;
    private Bank bank2;
    private final int sourceAccountBalanceValue = 1200;
    private final BigDecimal sourceAccountBalance = new BigDecimal(sourceAccountBalanceValue);
    private final int debitBalanceValue = 1000;
    private final BigDecimal debitBalance = new BigDecimal(debitBalanceValue);


    @Before
    public void setUp() throws Exception
    {
        centralBank = new RealCentralBank();
        bank = new Bank(new ProductManager());
        bank2 = new Bank(new ProductManager());
        centralBank.registerBank(bank);
        centralBank.registerBank(bank2);
        sourceAccount = ProductFactory.createAccount(bank, sourceAccountBalanceValue);
        debitSourceAccount = (DebitAccount) ProductFactory.createAccount(bank, sourceAccountBalanceValue, debitBalanceValue);
    }

    @Test
    public void makeInterbankTransfer_CorrectAmountAccountAndIds_BothBalancesChanged() throws Exception
    {
        int targetAccountBalanceValue = 1400;
        int amountToTransferValue = 200;
        BigDecimal amountToTransfer = new BigDecimal(amountToTransferValue);


        IAccount targetAccount = bank2.createAccount(new Balance(targetAccountBalanceValue), new ProductDuration(2, 3),
                mock(IInterestCalculationStrategy.class), 1.3);

        InterbankTransferOperation transfer = new InterbankTransferOperation(centralBank, sourceAccount,
                targetAccount.getOwnerId(), targetAccount.getBankId(), amountToTransfer);
        transfer.execute();

        BigDecimal expectedTargetBalance = new BigDecimal(targetAccountBalanceValue+amountToTransferValue);
        BigDecimal expectedSourceBalance = new BigDecimal(sourceAccountBalanceValue-amountToTransferValue);

        assertEquals(expectedTargetBalance, targetAccount.getBalanceValue());
        assertEquals(expectedSourceBalance, sourceAccount.getBalanceValue());
    }

    @Test
    public void makeInterbankTransfer_TargetAccountDontExist_BalanceDontChange() throws Exception
    {
        BigDecimal amount = new BigDecimal(200);
        int dummyOwnerId = 134;

        InterbankTransferOperation transfer = new InterbankTransferOperation(centralBank, sourceAccount,
                dummyOwnerId, bank2.getId(), amount);
        transfer.execute();

        assertEquals(sourceAccountBalance, sourceAccount.getBalanceValue());
    }

    @Test
    public void makeInterbankTransfer_WrongBankId_BalanceDontChange() throws Exception
    {
        int targetAccountBalanceValue = 1400;
        int amountValue = 200;
        BigDecimal targetAccountBalance = new BigDecimal(targetAccountBalanceValue);
        BigDecimal amount = new BigDecimal(amountValue);

        IAccount targetAccount = bank2.createAccount(new Balance(targetAccountBalanceValue), new ProductDuration(2, 3),
                mock(IInterestCalculationStrategy.class), 1.3);
        int dummyBankId = 134;

        InterbankTransferOperation transfer = new InterbankTransferOperation(centralBank, sourceAccount,
                targetAccount.getOwnerId(), dummyBankId, amount);
        transfer.execute();

        assertEquals(sourceAccountBalance, sourceAccount.getBalanceValue());
        assertEquals(targetAccountBalance, targetAccount.getBalanceValue());
    }

    @Test(expected = BankException.class)
    public void makeInterbankTransfer_AmountGreaterThanBalanceNoDebit_BothBalancesDoesNotChange() throws Exception
    {
        int targetAccountBalanceValue = 1400;
        BigDecimal targetAccountBalance = new BigDecimal(targetAccountBalanceValue);
        BigDecimal amount = sourceAccountBalance.add(new BigDecimal(100));

        IAccount targetAccount = bank2.createAccount(new Balance(targetAccountBalanceValue), new ProductDuration(2, 3),
                mock(IInterestCalculationStrategy.class), 1.3);

        InterbankTransferOperation transfer = new InterbankTransferOperation(centralBank, sourceAccount,
                targetAccount.getOwnerId(), targetAccount.getBankId(), amount);
        transfer.execute();

        assertEquals(sourceAccountBalance, sourceAccount.getBalanceValue());
        assertEquals(targetAccountBalance, targetAccount.getBalanceValue());
    }

    @Test(expected = NullPointerException.class)
    public void makeInterbankTransfer_NullAccountArgument_ThrowBankException() throws Exception
    {
        int transferValue = 200;
        BigDecimal transferAmount = new BigDecimal(transferValue);


        ICommand operation = new InterbankTransferOperation(centralBank, null, sourceAccount.getOwnerId(),
                sourceAccount.getBankId(), transferAmount);
        operation.execute();
        assertEquals(sourceAccount.getBalanceValue(), sourceAccountBalance);
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
        assertOperationUndone(targetAccountBalance.getBalanceValue().intValue(), targetAccount, operation);
    }


    @Test(expected = BankException.class)
    public void makeTransfer_AmountGreaterThanBalancePlusDebit_ThrowBankException() throws Exception
    {
        int targetAccountBalanceValue = 1400;

        IAccount targetAccount = bank2.createAccount(new Balance(targetAccountBalanceValue), new ProductDuration(2, 3),
                mock(IInterestCalculationStrategy.class), 1.3);
        assertEquals(targetAccount.getBalanceValue(), new BigDecimal(targetAccountBalanceValue));


        BigDecimal amount = debitSourceAccount.getBalanceWithDebit().add(new BigDecimal(10));

        ICommand operation = new InterbankTransferOperation(centralBank, sourceAccount, targetAccount.getOwnerId(),
                targetAccount.getBankId(), amount);

        assertOperationUndone(targetAccountBalanceValue, targetAccount, operation);
        assertEquals(sourceAccountBalance.add(debitBalance), debitSourceAccount.getDebit().getBalanceValue());
    }

    @Test
    public void makeTransfer_AmountEqualToBalancePlusDebit_BalancesChanges() throws Exception
    {
        int targetAccountBalanceValue = 1400;
        BigDecimal targetAccountBalance = new BigDecimal(targetAccountBalanceValue);

        IAccount targetAccount = bank2.createAccount(new Balance(targetAccountBalanceValue), new ProductDuration(2, 3),
                mock(IInterestCalculationStrategy.class), 1.3);
        assertEquals(targetAccount.getBalanceValue(), new BigDecimal(targetAccountBalanceValue));
        BigDecimal amount = debitSourceAccount.getBalanceWithDebit();

        ICommand operation = new InterbankTransferOperation(centralBank, debitSourceAccount, targetAccount.getOwnerId(),
                targetAccount.getBankId(), amount);
        operation.execute();

        assertEquals(sourceAccountBalance.add(debitBalance).subtract(amount), debitSourceAccount.getBalanceWithDebit());
        assertEquals(targetAccountBalance.add(amount), targetAccount.getBalanceValue());
    }

    @Test(expected = BankException.class)
    public void makeTransfer_AmountLessThanBalancePlusDebit_BalancesChanges() throws Exception
    {
        int targetAccountBalanceValue = 1400;
        IAccount targetAccount = bank2.createAccount(new Balance(targetAccountBalanceValue), new ProductDuration(2, 3),
                mock(IInterestCalculationStrategy.class), 1.3);
        assertEquals(targetAccount.getBalanceValue(), new BigDecimal(targetAccountBalanceValue));
        BigDecimal amount = debitSourceAccount.getBalanceWithDebit().subtract(new BigDecimal(50));

        ICommand operation = new InterbankTransferOperation(centralBank, sourceAccount, targetAccount.getOwnerId(),
                targetAccount.getBankId(), amount);
        operation.execute();

        assertEquals(sourceAccountBalance.add(debitBalance).subtract(amount), debitSourceAccount.getBalanceWithDebit());
        assertEquals(new BigDecimal(targetAccountBalanceValue).add(amount), targetAccount.getBalanceValue());
    }

    @Test(expected = BankException.class)
    public void makeTransfer_AmountLessThanZero_ThrowsException() throws Exception
    {
        int targetAccountBalanceValue = 680;
        int transferValue = -600;

        BigDecimal transferAmount = new BigDecimal(transferValue);

        IAccount targetAccount = bank2.createAccount(new Balance(targetAccountBalanceValue), new ProductDuration(2, 3),
                mock(IInterestCalculationStrategy.class), 1.3);

        ICommand operation = new InterbankTransferOperation(centralBank, sourceAccount, targetAccount.getOwnerId(),
                targetAccount.getBankId(), transferAmount);
        assertOperationUndone(targetAccountBalanceValue, targetAccount, operation);
    }

    private void assertOperationUndone(int balance, IAccount targetAccount, ICommand operation) throws Exception
    {
        try
        {
            operation.execute();
        }
        catch (BankException e)
        {
            Assert.assertEquals(sourceAccountBalance, sourceAccount.getBalanceValue());
            Assert.assertEquals(new BigDecimal(balance), targetAccount.getBalanceValue());
            throw e;
        }
    }

}
