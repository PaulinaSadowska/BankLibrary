import java.math.BigDecimal;

/**
 * Created by palka on 02.04.2016.
 */
public class TimeDependentCalculationStrategy implements IInterestCalculationStrategy {
    public BigDecimal calculateInterest(Product product, double percent) {
        int oneDay = 1000 * 60 * 60 * 24;
        double numOfDays = (1.0 * product.getCreationDate().getTime())/ oneDay;
        double interest = product.getBalance().doubleValue() * numOfDays * percent;
        return new BigDecimal(interest);
    }
}
