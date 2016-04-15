package Utils;

import Products.Product;

import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.util.Calendar;

/**
 * Created by palka on 02.04.2016.
 */
public class TimeDependentInterestCalculationStrategy implements IInterestCalculationStrategy {
    public BigDecimal calculateInterest(Product product, double percent) {
        if(percent>100 || percent<0)
            throw new InvalidParameterException();

        int oneDay = 1000 * 60 * 60 * 24;
        long creation = product.getCreationDate().getTime();
        long now = Calendar.getInstance().getTime().getTime();
        long numOfDays = (now - creation)/oneDay;
        double interest = product.getBalance().doubleValue() * numOfDays * percent * 0.01;
        return new BigDecimal(interest);
    }
}
