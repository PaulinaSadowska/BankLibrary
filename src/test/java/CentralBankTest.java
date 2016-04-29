import Bank.*;
import Operations.ICommand;
import Operations.OperationType;
import Operations.TransferOperation;
import Products.Account;
import Products.ProductManager;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Hashtable;

import static Utils.ProductFactory.createAccount;
import static org.junit.Assert.*;

/**
 * Created by Paulina Sadowska on 29.04.2016.
 */
public class CentralBankTest
{

    private Bank _bank1;
    private Bank _bank2;
    private RealCentralBank _centralBank;

    @Before
    public void setUp(){
        _centralBank = new RealCentralBank();
        _bank1 = new Bank(new ProductManager());
        _bank2 = new Bank(new ProductManager());
    }

    @Test
    public void banksRegistration_BanksToCentralBankListAdded() throws NoSuchFieldException, IllegalAccessException
    {
        _centralBank.registerBank(_bank1);
        _centralBank.registerBank(_bank2);
        Field f = _centralBank.getClass().getDeclaredField("banks"); //NoSuchFieldException
        f.setAccessible(true);
        HashMap banksList = (HashMap) f.get(_centralBank); //IllegalAccessException
        assertEquals(banksList.size(), 2);
    }

    @Test
    public void makeTransferBetweenBanks_CorrectAmountAndAccounts_BalancesChanges() throws Exception
    {
        _centralBank.registerBank(_bank1);
        _centralBank.registerBank(_bank2);

        int balance = 500;
        int transferValue = 100;

        BigDecimal transferAmount = new BigDecimal(transferValue);

        Account account = createAccount(balance, 0, _bank1.getId());
        Account targetAccount = createAccount(balance, 0, _bank2.getId());

        ICommand operation = new TransferOperation(account, targetAccount, transferAmount, OperationType.Transfer);
        operation.execute();

        operation.undo();

        BigDecimal balanceValue = new BigDecimal(balance);

        Assert.assertEquals(balanceValue, targetAccount.getBalance());
        Assert.assertEquals(balanceValue, account.getBalance());
    }

}
