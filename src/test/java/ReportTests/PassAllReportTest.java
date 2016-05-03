package ReportTests;
import Products.*;
import Report.PassAllReport;
import Utils.ProductFactory;
import org.junit.*;

import static org.junit.Assert.*;

/**
 * Created by Paulina Sadowska on 03.05.2016.
 */
public class PassAllReportTest
{
    private PassAllReport report;

    @Before
    public void setUp()
    {
        report = new PassAllReport();
    }

    @Test
    public void visitsAccountTest_passesProduct()
    {
        IAccount account = ProductFactory.createAccount(1200);
        IProduct product = report.visit((Account) account);
        assertNotNull(product);
        assertEquals(account, product);
    }

    @Test
    public void visitsDebitAccountTest_passesProduct()
    {
        IAccount account = ProductFactory.createAccount(1200, 200);
        IProduct product = report.visit((DebitAccount) account);
        assertNotNull(product);
        assertEquals(account, product);
    }

    @Test
    public void visitsLoanTest_passesProduct()
    {
        Loan loan = ProductFactory.createLoan(1200);
        IProduct product = report.visit(loan);
        assertNotNull(product);
        assertEquals(loan, product);
    }

    @Test
    public void visitsInvestmentTest_passesProduct()
    {
        Investment investment = ProductFactory.createInvestment(1200);
        IProduct product = report.visit(investment);
        assertNotNull(product);
        assertEquals(investment, product);
    }
}
