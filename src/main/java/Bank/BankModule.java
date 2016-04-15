package Bank;

import com.google.inject.AbstractModule;

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
    }
}
