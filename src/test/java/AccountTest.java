import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by arasz on 01.04.2016.
 */
public class AccountTest
{
    private Account _account;

    private int _expectedOwnerId;
    private BigDecimal _expectedBalance;
    private Date _expectedCreationDate;
    private Date _expectedExpireDate;
    private OperationsHistory _expectedOperationHistory;
    private Interest _expectedIntrest;

    @Before
    public void setUp()
    {
        _account = new Account(_expectedBalance, _expectedCreationDate, _expectedExpireDate,
                _expectedIntrest, _expectedOwnerId, _expectedOperationHistory);
    }

    @Test
    public void getIdTest()
    {
        Assert.assertEquals(_expectedOwnerId, _account.getId());
    }

    @Test
    public void getBalanceTest()
    {
        Assert.assertEquals(_expectedBalance, _account.getBalance());
    }

    @Test
    public void getCreationDateTest()
    {
        Assert.assertEquals(_expectedCreationDate, _account.getCreationDate());
    }

    @Test
    public void getExpireDateTest()
    {
        Assert.assertEquals(_expectedExpireDate, _account.getExpireDate());
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
