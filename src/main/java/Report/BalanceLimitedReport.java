package Report;

import Products.*;
import Utils.BigDecimalComparator;

import java.math.BigDecimal;

/**
 * Created by Paulina Sadowska on 03.05.2016.
 */
public class BalanceLimitedReport implements Visitor
{
    private BigDecimal maxBalance;
    private BigDecimal minBalance;

    public BalanceLimitedReport(BigDecimal maxBalance, BigDecimal minBalance)
    {
        this.maxBalance = maxBalance;
        this.minBalance = minBalance;
    }

    private boolean checkBalance(BigDecimal balanceValue){
        boolean result = false;
        if(minBalance != null){
            result =  BigDecimalComparator.GreaterOrEqual(balanceValue, minBalance);
        }
        if(maxBalance != null){
            result = result && BigDecimalComparator.LessOrEqual(balanceValue, maxBalance);
        }
        return result;
    }

    private IProduct visit(Product product){
        if(checkBalance(product.getBalanceValue()))
        {
            return (IProduct) this;
        }
        else
            return null;
    }

    @Override
    public IProduct visit(Account account)
    {
        return visit(account);
    }

    @Override
    public IProduct visit(DebitAccount debitAccount)
    {
        return visit(debitAccount);
    }

    @Override
    public IProduct visit(Loan loan)
    {
        return visit(loan);
    }

    @Override
    public IProduct visit(Investment investment)
    {
        return visit(investment);
    }
}
