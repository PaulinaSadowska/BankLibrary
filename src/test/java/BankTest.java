import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by arasz on 03.04.2016.
 */
public class BankTest
{
    private Bank _bank;

    @Before
    public void setUp(){
        Injector injector = Guice.createInjector(new BankModule());
        _bank = injector.getInstance(Bank.class);
    }

    @Test
    public void createAccountTest()
    {

    }

}
