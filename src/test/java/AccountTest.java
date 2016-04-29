import Operations.*;
import Products.Account;
import Utils.ProductFactory;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

import static Utils.ProductFactory.createAccount;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;

/**
 * Created by arasz on 18.03.2016.
 */
public class AccountTest
{
    @Test
    public void doOperation_InPaymentOperation_BalanceChanged() throws Exception
    {
        int balance = 100;
        int amount = 100;

        Account account = ProductFactory.createAccount(balance);

        ICommand operation = new PaymentOperation(account, PaymentDirection.In, new BigDecimal(amount),
                OperationType.Payment);
        account.doOperation(operation);

        Assert.assertEquals(new BigDecimal(amount+balance), account.getBalanceValue());
    }
}