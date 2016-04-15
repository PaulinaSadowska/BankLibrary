package Bank;

import Utils.DefaultReportCreationStrategy;
import Utils.IInterestCalculationStrategy;
import Utils.IReportCreationStrategy;
import Utils.TimeDependentInterestCalculationStrategy;
import com.google.inject.AbstractModule;

import java.util.logging.Logger;

/**
 * Created by arasz on 03.04.2016.
 */
public class BankModule extends AbstractModule
{
    @Override
    protected void configure()
    {
        bind(IInterestCalculationStrategy.class).to(TimeDependentInterestCalculationStrategy.class);
        bind(IReportCreationStrategy.class).to(DefaultReportCreationStrategy.class);
        bind(Logger.class).to(Logger.getLogger("BankAPI").getClass());
    }
}
