
import Bank.BankException;
import Products.Account;
import Products.Interest;
import Products.Investment;
import Products.Product;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by palka on 31.03.2016.
 */
public class InvestmentTest {

    private Account createAccountInstance(BigDecimal balance)
    {

        return new Account(12, balance, mock(Date.class), mock(Interest.class));
    }

    @Test
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
    }
}
