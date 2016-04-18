import Bank.BankException;
import Products.Account;
import Products.Interest;
import Products.Loan;
import Products.Product;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Date;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Paulina Sadowska on 31.03.2016.
 * */
public class LoanTest {

    private Account createAccountInstance(BigDecimal balance)
    {
        return new Account(12, balance, mock(Date.class), mock(Interest.class));
    }

   /* @Test
    public void repayLoanTest() throws BankException
    {
        BigDecimal accountBalance = new BigDecimal(1200);
        BigDecimal loanBalance = new BigDecimal(200);

        Account account = createAccountInstance(accountBalance);

        Interest interest = mock(Interest.class);
        when(interest.calculateInterest(any(Product.class))).thenReturn(new BigDecimal(0));

        Loan loan = new Loan(12, loanBalance, mock(Date.class), interest, account);
        loan.repay();
        assertEquals(account.getBalance(), accountBalance.subtract(loanBalance));
    }

    @Test (expected = BankException.class)
    public void failToRepayLoanTest() throws BankException
    {
        BigDecimal accountBalance = new BigDecimal(1200);
        BigDecimal loanBalance = new BigDecimal(2000);

        Account account = createAccountInstance(accountBalance);

        Interest interest = mock(Interest.class);
        when(interest.calculateInterest(any(Product.class))).thenReturn(new BigDecimal(0));

        Loan loan = new Loan(12, loanBalance, mock(Date.class), interest, account);
        loan.repay();
    }*/
}

