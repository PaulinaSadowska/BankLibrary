import com.google.inject.AbstractModule;

/**
 * Created by palka on 04.04.2016.
 */
public class InterestModule extends AbstractModule
{
    @Override
    protected void configure()
    {
        bind(IInterestCalculationStrategy.class).to(TimeDependentInterestCalculationStrategy.class);
        bind(Double.class).toInstance(0.5);
    }
}
