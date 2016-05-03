package OperationsTests;

import Operations.MakeLoanOperation;
import Products.*;
import Products.Balance.Balance;
import Utils.IInterestCalculationStrategy;
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
    private ProductDuration duration;
    private ProductManager manager;
    private int ownerId;
    private Interest interest;
    private Balance balance;

    @Before
    public void setUp(){
        duration = new ProductDuration(1, 1);
        manager = new ProductManager();
        balance = new Balance(new BigDecimal(1200));
        interest = new Interest(mock(IInterestCalculationStrategy.class), 0.3);
        ownerId = 1234;
    }

    @Test
    public void createLoanToAccountTest() throws Exception
    {
        IAccount account = manager.createNewProduct(Account.class, ownerId, balance, duration, interest, 123);
        assertNotNull(account);
        MakeLoanOperation createLoan =
                new MakeLoanOperation(account.getOwnerId(), balance, duration, interest, manager);
        createLoan.execute();
        Loan newLoan = manager.getLoan(ownerId).get(0);
        assertNotNull(newLoan);
        assertEquals(account.getOwnerId(), newLoan.getOwnerId());
    }

    @Test (expected=IndexOutOfBoundsException.class)
    public void createLoanToNonexistingAccountTest() throws Exception
    {
        int ownerId = 999;
        MakeLoanOperation createLoan =
                new MakeLoanOperation(ownerId, balance, duration, interest, manager);
        createLoan.execute();
        assertNull(manager.getLoan(ownerId).get(0));
    }
}
