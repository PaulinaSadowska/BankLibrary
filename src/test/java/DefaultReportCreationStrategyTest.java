import Products.Account;
import Products.Product;
import Utils.DefaultReportCreationStrategy;
import Utils.PassAllReport;
import Utils.Report;
import Utils.ReportDocument;
import org.junit.*;

import java.util.LinkedList;
import static org.junit.Assert.*;


import static org.mockito.Mockito.mock;

/**
 * Created by Paulina Sadowska on 07.04.2016.
 */
public class DefaultReportCreationStrategyTest
{
    @Test
    public void createReportTest()
    {
        DefaultReportCreationStrategy strategy = new DefaultReportCreationStrategy();
        LinkedList<Product> productList = new LinkedList<Product>();
        productList.add(mock(Account.class));
        Object o = strategy.createReport(productList);
        assertNotNull(o);
        assertEquals(o.getClass().getName(), ReportDocument.class.getName());
    }
}
