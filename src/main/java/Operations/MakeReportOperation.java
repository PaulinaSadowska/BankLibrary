package Operations;

import Bank.*;
import Products.IProduct;
import Report.*;

import java.util.List;

/**
 * Created by arasz on 15.04.2016.
 */
public class MakeReportOperation extends Operation implements ICommand
{
    private Bank bank;
    private Report report;
    private IReportCreationStrategy reportStrategy;
    private ReportDocument reportDecument;

    public MakeReportOperation(Bank bank, Report report, IReportCreationStrategy reportStrategy)
    {
        super(OperationType.MakeReport);
        this.bank = bank;
        this.reportStrategy = reportStrategy;
        this.report = report;
    }

    @Override
    public void execute() throws Exception
    {
        setExecuted(true);
        List<IProduct> productList = bank.doReport(report);
        reportDecument = reportStrategy.createReport(productList);
    }

    public ReportDocument getReportDocument() throws BankException
    {
        checkExecuted();
        return reportDecument;
    }
}
