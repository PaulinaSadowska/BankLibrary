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
    private  OperationManager _expectedOperationManager;
    private Interest _expectedIntrest;

    @Before
    public void setUp()
    {
        _account = new Account(_expectedBalance, _expectedCreationDate, _expectedExpireDate,
                _expectedIntrest, _expectedOwnerId, _expectedOperationManager, _expectedOperationHistory);
    }

    @Test
    public void getIdTest()
    {
        Assert.assertEquals(_expectedOwnerId, _account.getId());
    }

    @After
    public void tearDown()
    {
        _account = null;
    }

}
