package ReportTests;
import Bank.BankException;
import Products.*;
import Report.BalanceLimitedReport;
import Report.PassAllReport;
import Utils.ProductFactory;
import org.junit.*;
import org.junit.experimental.categories.IncludeCategories;

import java.math.BigDecimal;

import static org.junit.Assert.*;

/**
 * Created by Paulina Sadowska on 03.05.2016.
 */
public class BalanceLimitedReportTest
{
    private static final int toLowBalanceValueInt = 2;
    private static final int minBalanceInt = 12;
    private static final int betweenBalanceValueInt = 80;
    private static final int maxBalanceInt = 120;
    private static final int toBigBalanceValueInt = 180;

    private static final BigDecimal minBalance = new BigDecimal(minBalanceInt);
    private static final BigDecimal maxBalance = new BigDecimal(maxBalanceInt);

    @Test
    public void visitsDebitAccountWithBalanceInLimit_minAndMaxValuesSetProperly_passesProduct() throws BankException
    {
        BalanceLimitedReport report = new BalanceLimitedReport(minBalance, maxBalance);
        IAccount account = ProductFactory.createAccount(betweenBalanceValueInt, 200);
        assertEquals(account, report.visit((DebitAccount) account));
    }

    @Test
    public void visitsAccountWithBalanceInLimit_minAndMaxValuesSetProperly_passesProduct() throws BankException
    {
        BalanceLimitedReport report = new BalanceLimitedReport(minBalance, maxBalance);
        IAccount account = ProductFactory.createAccount(betweenBalanceValueInt);
        assertEquals(account, report.visit((Account) account));
    }

    @Test
    public void visitsAccountWithBalanceToLow_minAndMaxValuesSetProperly_notPassesProduct() throws BankException
    {
        BalanceLimitedReport report = new BalanceLimitedReport(minBalance, maxBalance);
        IAccount account = ProductFactory.createAccount(toLowBalanceValueInt);
        assertNull(report.visit((Account) account));
    }

    @Test
    public void visitsAccountWithBalanceToBig_minAndMaxValuesSetProperly_notPassesProduct() throws BankException
    {
        BalanceLimitedReport report = new BalanceLimitedReport(minBalance, maxBalance);
        IAccount account = ProductFactory.createAccount(toBigBalanceValueInt);
        assertNull(report.visit((Account) account));
    }

    @Test
    public void visitsInvestmentWithBalanceInLimit_minAndMaxValuesSetProperly_passesProduct() throws BankException
    {
        BalanceLimitedReport report = new BalanceLimitedReport(minBalance, maxBalance);
        Investment investment = ProductFactory.createInvestment(betweenBalanceValueInt);
        assertEquals(investment, report.visit(investment));
    }

    @Test
    public void visitsLoanWithBalanceInLimit_minAndMaxValuesSetProperly_passesProduct() throws BankException
    {
        BalanceLimitedReport report = new BalanceLimitedReport(minBalance, maxBalance);
        Loan loan = ProductFactory.createLoan(betweenBalanceValueInt);
        assertEquals(loan, report.visit(loan));
    }

    @Test (expected = BankException.class)
    public void minAndMaxValuesSetNotProperly_throwsBankException() throws BankException
    {
        new BalanceLimitedReport(maxBalance, minBalance);
    }
}

