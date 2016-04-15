import Bank.BankException;
import Operations.ICommand;
import Operations.OperationType;
import Operations.PaymentOperation;
import Operations.PaymentDirection;
import Products.Account;
import Products.Interest;
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
    //TODO: move it to separate class because DRY
    private Account createInstance(int balance)
    {
        Interest interestMock = mock(Interest.class);
        return new Account(12, new BigDecimal(balance), mock(Date.class), interestMock);
    }

    @Test
    public void testExecute_directionInAmmountCorrect_increaseAmountBalance() throws BankException
    {
        Account account = createInstance(0);
        BigDecimal ammount = new BigDecimal(10);

        ICommand commmand = new PaymentOperation(account, PaymentDirection.In, ammount, OperationType.Payment);

        commmand.execute();

        Assert.assertEquals(account.getBalance(), ammount);
    }
}