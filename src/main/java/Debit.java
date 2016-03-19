import java.math.BigDecimal;

/**
 * Created by palka on 11.03.2016.
 */
public class Debit {

    private BigDecimal maxDebitValue;

    public Debit(BigDecimal maxDebitValue)
    {
        this.maxDebitValue = maxDebitValue;
    }

    public BigDecimal getMaxDebitValue()
    {
        return maxDebitValue;
    }
}
