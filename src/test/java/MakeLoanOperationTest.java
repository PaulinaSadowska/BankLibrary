import Bank.Bank;
import Operations.MakeLoanOperation;
import Products.*;
import Utils.IInterestCalculationStrategy;
import Utils.TimeDependentInterestCalculationStrategy;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

/**
 * Created by Paulina Sadowska on 18.04.2016.
 */
public class MakeLoanOperationTest
{
    private ProductDuration _duration;
    private ProductManager _manager;
    private int _ownerId;
    private Interest _interest;
    private BigDecimal _balance;

    @Before
    public void setUp(){
        _duration = new ProductDuration(1, 1);
        _manager = new ProductManager();
        _balance = new BigDecimal(1200);
        _interest = new Interest(mock(IInterestCalculationStrategy.class), 0.3);
    }

    @Test
    public void createLoanToAccountTest() throws Exception
    {
        Account account = _manager.createNewProduct(Account.class, _ownerId, _balance, _duration, _interest);
        assertNotNull(account);
        MakeLoanOperation createLoan =
                new MakeLoanOperation(account.getOwnerId(), _balance, _duration, _interest, _manager);
        createLoan.execute();
        Loan newLoan = _manager.getLoan(_ownerId).get(0);
        assertNotNull(newLoan);
        assertEquals(account.getOwnerId(), newLoan.getOwnerId());
    }

    @Test (expected=IndexOutOfBoundsException.class)
    public void createLoanToNonexistingAccountTest() throws Exception
    {
        int ownerId = 999;
        MakeLoanOperation createLoan =
                new MakeLoanOperation(ownerId, _balance, _duration, _interest, _manager);
        createLoan.execute();
        assertNull(_manager.getLoan(ownerId).get(0));
    }
}
