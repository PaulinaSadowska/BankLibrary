import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Calendar;

/**
 * Created by arasz on 03.04.2016.
 */
public class BankTest
{
    private Bank _bank;
    private BigDecimal _balance;
    private ProductDuration _duration;

    @Before
    public void setUp(){
        Injector injector = Guice.createInjector(new BankModule());
        _bank = injector.getInstance(Bank.class); //TODO - popraw, to tak nie dziala!
        _balance = new BigDecimal(1200);
        _duration = new ProductDuration(0, 5);
    }

    @Test
    public void createAccountTest()
    {
      //  _bank.createAccount(_balance, _duration, _bank.)
    }

}
