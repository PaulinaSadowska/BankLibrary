package ProductsTests;

import Products.*;
import Utils.ProductFactory;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;

/**
 * Created by Paulina Sadowska on 31.03.2016.
 * */
public class LoanTest {

    @Test
    public void getLoanBaseAccountTest_AccountExists_getsAccount()
    {
        IAccount account = ProductFactory.createAccount(200);
        Loan loan = ProductFactory.createLoan(1200, account);
        Assert.assertEquals(loan.getBaseAccount(), account);
    }

    @Test
    public void getLoanBaseAccountNullTest_AccountExists_getsAccount()
    {
        Loan loan = ProductFactory.createLoan(1200);
        assertNull(loan.getBaseAccount());
    }
}

