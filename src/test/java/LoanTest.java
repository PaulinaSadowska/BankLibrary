import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by palka on 31.03.2016.
 */
public class LoanTest {

    Loan _endedLoan;
    Loan _ongoingLoan;
    BigDecimal _expectedBalance;
    Calendar _expectedCreationDate;
    Calendar _weekAgoDate;
    Calendar _nextWeekDate;
    Interest _expectedInterest;

    @Before
    public void setUp(){
        _weekAgoDate  = Calendar.getInstance();
        _weekAgoDate.add(Calendar.DAY_OF_YEAR, -7);

        _nextWeekDate  = Calendar.getInstance();
        _nextWeekDate.add(Calendar.DAY_OF_YEAR, 7);

        _expectedBalance = new BigDecimal(1500);
        _expectedCreationDate = Calendar.getInstance();
        _expectedCreationDate.set(2000, 07, 06);

        _expectedInterest = new Interest(0.5);


        Account account = null;
        try {
            account = (Account)MockFactory.CreateProductMock(_expectedBalance, Account.class);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

        _endedLoan = new Loan(account, _expectedBalance, _expectedCreationDate.getTime(), _weekAgoDate.getTime(), _expectedInterest);
        _ongoingLoan = new Loan(account, _expectedBalance, _expectedCreationDate.getTime(), _nextWeekDate.getTime(), _expectedInterest);
    }

    @Test
    public void expiredTest(){
        assertTrue(_endedLoan.expired());
        assertFalse(_ongoingLoan.expired());
    }

    @Test
    public void getBalanceTest(){
        assertEquals(_expectedBalance, _endedLoan.getBalance());
        assertEquals(_expectedBalance, _ongoingLoan.getBalance());
    }

    @Test
    public void getCreationDateTest(){
        assertEquals(_expectedCreationDate.getTime(), _endedLoan.getCreationDate());
        assertEquals(_expectedCreationDate.getTime(), _ongoingLoan.getCreationDate());
    }

    @Test
    public void getExpirationDateTest(){
        assertEquals(_weekAgoDate.getTime(), _endedLoan.getExpireDate());
        assertEquals(_nextWeekDate.getTime(), _ongoingLoan.getExpireDate());
    }

    @Test
    public void getInterestTest(){
        assertEquals(_expectedInterest, _endedLoan.getInterest());
        assertEquals(_expectedInterest, _ongoingLoan.getInterest());
    }

}
