import com.google.inject.Inject;

import java.util.List;

/**
 * Created by arasz on 18.03.2016.
 * Tworzy raport na podstawie listy produktów
 */
public class ReportManager
{
    //TODO: Użyć IOC do tworzenia jednej instancji menagera
    private IReportCreationStrategy _reportCreationStrategy;

    @Inject
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
