package Products;

import java.math.BigDecimal;

/**
 * Created by Rafal on 02.05.2016.
 */
public class DividedAmount
{
    private BigDecimal residue;
    private BigDecimal toDebit;

    public DividedAmount(BigDecimal residue, BigDecimal toDebit)
    {
        this.residue = residue;
        this.toDebit = toDebit;
    }

    public BigDecimal getToDebit() {
        return toDebit;
    }

    public BigDecimal getResidue() {
        return residue;
    }
}
