package ReportTests;

import Products.Product;
import Report.DefaultReportCreationStrategy;
import Report.IReportCreationStrategy;
import Report.ReportDocument;
import Report.ReportManager;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by arasz on 07.04.2016.
 */
public class ReportManagerTest
{
    ReportManager _reportManager;
    String _reportString = "Created report";

    @Before
    public void setUp()
    {
        ReportDocument<String> reportMock = mock(ReportDocument.class);
        when(reportMock.getReportDocument()).thenReturn(_reportString);

        IReportCreationStrategy reportCreationStrategyMock = mock(DefaultReportCreationStrategy.class);
        when(reportCreationStrategyMock.createReport(any(List.class))).thenReturn(reportMock);

        _reportManager = new ReportManager(reportCreationStrategyMock);
    }

    @Test
    public void createReport_ThenReturnDefaultReport()
    {
        ReportDocument<String> report =_reportManager.createReport(new ArrayList<Product>());
        Assert.assertEquals(_reportString, report.getReportDocument());
    }
}
