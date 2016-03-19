import java.math.BigDecimal;

/**
 * Created by palka on 11.03.2016.
 */
public interface IInterestable {
    BigDecimal calculateInterest();
    void changeInterestMachanizm(Interest interest);
}
