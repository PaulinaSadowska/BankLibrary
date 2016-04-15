import Bank.BankException;
import Operations.ICommand;
import Operations.OperationType;
import Operations.PaymentOperation;
import Operations.PaymentDirection;
import Products.Account;
import Products.Interest;
import Utils.ProductFactory;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Date;

import static org.mockito.Mockito.mock;

/**
 * Created by arasz on 15.04.2016.
 */
public class PaymentOperationTest
{
    @Test
    public void makePayment_directionInAmountCorrect_increaseAmountBalance() throws BankException
    {
        Account account = ProductFactory.createAccount(0);
        BigDecimal amount = new BigDecimal(10);

        ICommand command = new PaymentOperation(account, PaymentDirection.In, amount, OperationType.Payment);

        command.execute();

        Assert.assertEquals(account.getBalance(), amount);
    }

    @Test
    public void makeAndUndoPayment_directionOutCorrectAmount_balanceNotChanged() throws BankException
    {
        int balance = 100;
        Account account = ProductFactory.createAccount(balance);
        BigDecimal amount = new BigDecimal(50);
        ICommand command = new PaymentOperation(account, PaymentDirection.Out, amount, OperationType.Payment);
        command.execute();
        Assert.assertEquals(amount, account.getBalance());
        command.undo();
        Assert.assertEquals(new BigDecimal(balance), account.getBalance());
    }
}
