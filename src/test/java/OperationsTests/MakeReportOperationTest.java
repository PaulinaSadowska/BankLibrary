package OperationsTests;

/**
 * Created by Paulina Sadowska on 03.05.2016.
 */

import Bank.*;
import Operations.MakeReportOperation;
import Products.IAccount;
import Products.ProductManager;
import Report.DefaultReportCreationStrategy;
import Report.PassAllReport;
import Report.ReportDocument;
import Utils.ProductFactory;
import org.junit.Before;
import org.junit.Test;


import static org.junit.Assert.*;

public class MakeReportOperationTest
{
    private Bank bank;
    @Before
    public void setUp() throws Exception
    {
        ProductManager manager = new ProductManager();
        bank = new Bank(manager);
        IAccount account = ProductFactory.createAccount(bank, 1200);
        ProductFactory.createLoan(manager, 1200, account);
    }

    @Test
    public void makeReport_getReport_getsReportObject() throws Exception
    {
        MakeReportOperation makeReport = new MakeReportOperation(bank, new PassAllReport(), new DefaultReportCreationStrategy());
        makeReport.execute();
        ReportDocument report = makeReport.getReportDocument();
        assertNotNull(report);
    }

    @Test (expected = BankException.class)
    public void makeReport_getReportBeforeExecute_throwsBankException() throws Exception
    {
        MakeReportOperation makeReport = new MakeReportOperation(bank, new PassAllReport(), new DefaultReportCreationStrategy());
        ReportDocument report = makeReport.getReportDocument();
        assertNull(report);
    }
}
