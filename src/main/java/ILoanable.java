
package main.java;
import java.math.BigDecimal;

/**
 * Created by palka on 11.03.2016.
 */
public interface ILoanable {

    void makeLoan(BigDecimal amount, Interest interest, int durationInDays);
    void repayLoan();

}
