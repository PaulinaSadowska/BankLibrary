import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by arasz on 01.04.2016.
 */
public class AccountTest
{
    private Account _account;

    private int _expectedOwnerId;
    private BigDecimal _expectedBalance;
    private Calendar _expectedCreationDate;
    private Calendar _expectedExpireDate;
    private OperationsHistory _expectedOperationHistory;
    private Interest _expectedIntrest;

    @Before
    public void setUp()
    {
        _expectedCreationDate  = Calendar.getInstance();

        _expectedExpireDate  = Calendar.getInstance();
        _expectedExpireDate.add(Calendar.DAY_OF_YEAR, 7);

        _expectedBalance = new BigDecimal(1700);
        _expectedOwnerId = 123456;
        _expectedOperationHistory = new OperationsHistory();
        _expectedIntrest = new Interest(0.5);

        _account = new Account(_expectedBalance, _expectedExpireDate.getTime(),
                _expectedIntrest, _expectedOwnerId, _expectedOperationHistory);
    }

    @Test
    public void getIdTest()
    {
        Assert.assertEquals(_expectedOwnerId, _account.getOwnerId());
    }

    @Test
    public void getBalanceTest()
    {
        Assert.assertEquals(_expectedBalance, _account.getBalance());
    }

    @Test
    public void getCreationDateTest()
    {
        Assert.assertEquals(_expectedCreationDate.getTime(), _account.getCreationDate());
    }

    @Test
    public void getExpireDateTest()
    {
        Assert.assertEquals(_expectedExpireDate.getTime(), _account.getExpireDate());
    }

    @Test
    public void getInterestTest()
    {
        Assert.assertEquals(_expectedIntrest, _account.getInterest());
    }

    @Test
    public void getOperationHistoryTest()
    {
        Assert.assertEquals(_expectedOperationHistory, _account.getOperationHistory());
    }




    @After
    public void tearDown()
    {
        _account = null;
    }

}
