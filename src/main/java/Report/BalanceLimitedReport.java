package Report;

import Bank.BankException;
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

    public BalanceLimitedReport(BigDecimal maxBalance, BigDecimal minBalance) throws BankException
    {
        if(BigDecimalComparator.LessThan(minBalance, maxBalance)){
            throw new BankException("minimum Balance value must be less or equal to max Balance value");
        }
        this.maxBalance = maxBalance;
        this.minBalance = minBalance;
    }

    private boolean checkBalance(BigDecimal balanceValue){
        boolean result = false;
        if(minBalance != null){
            result =  BigDecimalComparator.GreaterOrEqual(minBalance, balanceValue);
        }
        if(maxBalance != null){
            result = result && BigDecimalComparator.LessOrEqual(maxBalance, balanceValue);
        }
        return result;
    }

    private IProduct _visit(IProduct product){
        if(checkBalance(product.getBalanceValue()))
        {
            return product;
        }
        else
            return null;
    }

    @Override
    public IProduct visit(Account account)
    {
        return _visit(account);
    }

    @Override
    public IProduct visit(DebitAccount debitAccount)
    {
        return _visit(debitAccount);
    }

    @Override
    public IProduct visit(Loan loan)
    {
        return _visit(loan);
    }

    @Override
    public IProduct visit(Investment investment)
    {
        return _visit(investment);
    }
}
