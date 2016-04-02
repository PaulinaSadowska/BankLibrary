import java.math.BigDecimal;

/**
 * Created by palka on 11.03.2016.
 * Interfejs umożliwiający branie i spłacanie kredytu
 */
public interface ILoanable {

    void makeLoan(Loan newLoan);
    void repayLoan();

}
