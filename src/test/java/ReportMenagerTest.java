import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by arasz on 07.04.2016.
 */
public class ReportMenagerTest
{
    ReportManager _reportManager;
    String _reportString = "Created report";

    @Before
    public void setUp()
    {
        Report<String> reportMock = mock(Report.class);
        when(reportMock.getReport()).thenReturn(_reportString);

        IReportCreationStrategy reportCreationStrategyMock = mock(DefaultReportCreationStrategy.class);
        when(reportCreationStrategyMock.createReport(new ArrayList<Product>())).thenReturn(reportMock);

        _reportManager = new ReportManager(reportCreationStrategyMock);
    }

    @Test
    public void createReport_ThenReturnDefaultReport()
    {
        Report<String> report =_reportManager.createReport(new ArrayList<Product>());
        Assert.assertEquals(_reportString, report.getReport());
    }
}
