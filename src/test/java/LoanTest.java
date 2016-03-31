package test.java;

import main.java.Account;
import main.java.Interest;
import main.java.Investment;
import main.java.Loan;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by palka on 31.03.2016.
 */
public class LoanTest {

    Loan endedLoan;
    Loan ongoingLoan;
    @Before
    public void setUp(){
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, -5);
        endedLoan = new Loan(new Account(), new BigDecimal(1500), new Date(2000, 1,1), cal.getTime(), new Interest(0.5));
        cal.add(Calendar.DAY_OF_YEAR, +10);
        ongoingLoan = new Loan(new Account(), new BigDecimal(1500), new Date(2000, 1,1), cal.getTime(), new Interest(0.5));
    }

    @Test
    public void wykonajTest(){
        assertTrue(endedLoan.didEnd());
        assertFalse(ongoingLoan.didEnd());
    }

    @After
    public void tearDown(){

    }

}
