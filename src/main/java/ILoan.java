import java.math.BigDecimal;

/**
 * Created by arasz on 03.04.2016.
 */
public interface ILoan extends IClosable
{
    BigDecimal getLoanRepayAmount();
}
