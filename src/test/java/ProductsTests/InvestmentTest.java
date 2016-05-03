package ProductsTests;

import Products.*;
import Utils.ProductFactory;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;

/**
 * Created by palka on 31.03.2016.
 */
public class InvestmentTest {

    private IAccount createAccountInstance(BigDecimal balance)
    {

        return ProductFactory.createAccount(balance.intValue());
    }

  /*  @Test
    public void closeInvestmentTest() throws BankException
    {
        BigDecimal accountBalance = new BigDecimal(1200);
        BigDecimal loanBalance = new BigDecimal(200);
        BigDecimal interestValue = new BigDecimal(10);

        Account account = createAccountInstance(accountBalance);

        Interest interest = mock(Interest.class);
        when(interest.calculateInterest(any(Product.class))).thenReturn(interestValue);

        Calendar expireDate = Calendar.getInstance();
        expireDate.add(Calendar.MONTH, -1);
        Investment investment = new Investment(12, loanBalance, expireDate.getTime(), interest, account);
        investment.close();
        assertEquals(account.getBalance(), interestValue.add(accountBalance.add(loanBalance)));
    }

    @Test
    public void closeInvestmentBeforeExpireDateTest() throws BankException
    {
        BigDecimal accountBalance = new BigDecimal(1200);
        BigDecimal loanBalance = new BigDecimal(200);
        BigDecimal interestValue = new BigDecimal(10);

        Account account = createAccountInstance(accountBalance);

        Interest interest = mock(Interest.class);
        when(interest.calculateInterest(any(Product.class))).thenReturn(interestValue);

        Calendar expireDate = Calendar.getInstance();
        expireDate.add(Calendar.MONTH, 1);
        Investment investment = new Investment(12, loanBalance, expireDate.getTime(), interest, account);
        investment.close();
        assertEquals(account.getBalance(), accountBalance.add(loanBalance));
    }*/
}
