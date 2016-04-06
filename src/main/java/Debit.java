import java.math.BigDecimal;

/**
 * Created by palka on 11.03.2016.
 * Reprezentuje debet który może posiadać rachunek
 */
public class Debit
{
    final private BigDecimal _debit;

    public Debit(BigDecimal debitValue)
    {
        this._debit= debitValue;
    }

    public BigDecimal getDebitValue()
    {
        return _debit;
    }


}