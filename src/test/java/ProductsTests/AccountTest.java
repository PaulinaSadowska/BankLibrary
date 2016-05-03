package ProductsTests;

import Operations.*;
import Products.IAccount;
import Utils.ProductFactory;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;


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

        IAccount account = ProductFactory.createAccount(balance);

        ICommand operation = new PaymentOperation(account, PaymentDirection.In, new BigDecimal(amount));
        account.doOperation(operation);

        Assert.assertEquals(new BigDecimal(amount+balance), account.getBalanceValue());
    }
}