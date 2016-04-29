import Bank.*;
import Operations.InterbankTransferOperation;
import Products.Account;

import Products.Interest;
import Products.ProductDuration;
import Products.ProductManager;
import Utils.IInterestCalculationStrategy;
import Utils.ProductFactory;
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
}
