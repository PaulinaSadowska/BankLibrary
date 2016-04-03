import java.util.List;

/**
 * Created by arasz on 18.03.2016.
 * Tworzy raport na podstawie listy produktów
 */
public class ReportManager
{
    private IReportCreationStrategy _reportCreationStrategy;

    public <T> ReportManager(IReportCreationStrategy<T> reportCreationStrategy)
    {
        setReportCreationStrategy(reportCreationStrategy);
    }

    public void setReportCreationStrategy(IReportCreationStrategy reportCreationStrategy)
    {
        _reportCreationStrategy = reportCreationStrategy;
    }

    public <T> Report<T>createReport(List<Product> productList)
    {
        return _reportCreationStrategy.createReport(productList);
    }
}
