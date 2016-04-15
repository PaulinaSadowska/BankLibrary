package Utils;

import Operations.Operation;
import Operations.OperationType;
import Products.Product;
import com.google.inject.Inject;

import java.util.List;

/**
 * Created by arasz on 18.03.2016.
 * Tworzy raport na podstawie listy produktów
 */
public class ReportManager
{
    OperationsHistory _history = OperationsHistory.getGlobalHistory();

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
        _history.add(new Operation(OperationType.MakeReport));
        return _reportCreationStrategy.createReport(productList);
    }
}
