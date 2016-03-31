package main.java;
import java.math.BigDecimal;

/**
 * Created by palka on 11.03.2016.
 */
public interface IInvestable {

    void openInvestment(BigDecimal amount, Interest interest, int durationInDays);
    BigDecimal closeInvestment();

}
