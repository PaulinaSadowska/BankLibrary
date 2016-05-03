package OperationsTests; /**
 * Created by Paulina Sadowska on 30.04.2016.
 */

import Bank.BankException;
import Operations.ICommand;
import Operations.RefusedTransferPayback;
import Products.IAccount;
import Utils.ProductFactory;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import static org.junit.Assert.*;


public class RefusedTransferPaybackTest
{
    private IAccount _account;
    private int _accountBalance;

    @Before
    public void setUp() throws Exception
    {
        _accountBalance = 1200;
        _account = ProductFactory.createAccount(_accountBalance);
    }

    @Test
    public void makeRefusedTransferPayback_CorrectAccountAndAmount_BalanceChanges() throws Exception
    {
        BigDecimal amount = new BigDecimal(100);
        ICommand operation = new RefusedTransferPayback(_account, amount);
        operation.execute();
        assertEquals(amount.add(new BigDecimal(_accountBalance)), _account.getBalanceValue());
    }

    @Test(expected = BankException.class)
    public void makeRefusedTransferPayback_NullAccount_ThrowsException() throws Exception
    {
        BigDecimal amount = new BigDecimal(100);
        ICommand operation = new RefusedTransferPayback(null, amount);
        operation.execute();
    }

    @Test(expected = BankException.class)
    public void makeRefusedTransferPayback_AmountLessThanZero_ThrowsException() throws Exception
    {
        BigDecimal amount = new BigDecimal(-100);
        ICommand operation = new RefusedTransferPayback(_account, amount);
        operation.execute();
    }

    @Test(expected = BankException.class)
    public void makeRefusedTransferPayback_NullAmount_ThrowsException() throws Exception
    {
        ICommand operation = new RefusedTransferPayback(_account, null);
        operation.execute();
    }
}
