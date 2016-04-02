import com.google.inject.Inject;

import java.math.BigDecimal;

/**
 * Created by palka on 11.03.2016.
 * Oprocentowanie. Oblicza
 */
public class Interest implements IInterestable
{
    private IInterestCalculationStrategy _interestCalculationStrategy;

    private double _percent;

    @Inject
    public Interest(IInterestCalculationStrategy calculationStrategy, double percent)
    {
        _interestCalculationStrategy = calculationStrategy;
        _percent = percent;
    }

    public Interest()
    {
        _percent = 0;
    }

    public void setStrategy(IInterestCalculationStrategy strategy)
    {
        _interestCalculationStrategy = strategy;
    }

    public BigDecimal calculateInterest(Product product)
    {
        return _interestCalculationStrategy.calculateInterest(product, _percent);
    }
}
