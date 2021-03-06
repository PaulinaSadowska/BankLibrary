package Products;

import Operations.Operation;
import Operations.OperationType;
import Products.Product;
import Utils.IInterestCalculationStrategy;
import Utils.OperationsHistory;
import com.google.inject.Inject;

import java.math.BigDecimal;

/**
 * Created by palka on 11.03.2016.
 * Oprocentowanie. Oblicza
 */
public class Interest
{
    OperationsHistory _history = OperationsHistory.getGlobalHistory();

    private IInterestCalculationStrategy _interestCalculationStrategy;

    public double getPercent()
    {
        return _percent;
    }

    private double _percent;

    @Inject
    public Interest(IInterestCalculationStrategy calculationStrategy, double percent)
    {
        _interestCalculationStrategy = calculationStrategy;
        _percent = percent;
    }

    public void _setStrategy(IInterestCalculationStrategy strategy)
    {
        _interestCalculationStrategy = strategy;
    }

    public IInterestCalculationStrategy getStrategy(){
        return _interestCalculationStrategy;
    }
}
