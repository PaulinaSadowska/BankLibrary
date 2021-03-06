package Report;

import Operations.Operation;
import Operations.OperationType;
import Products.Product;
import Utils.OperationsHistory;
import com.google.inject.Inject;

import java.util.List;

/**
 * Created by arasz on 18.03.2016.
 * Tworzy raport na podstawie listy produktów
 */
public class ReportManager
{
    OperationsHistory _history = OperationsHistory.getGlobalHistory();

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

    public <T> ReportDocument<T>createReport(List<Product> productList)
    {
        _history.add(new Operation(OperationType.MakeReport));
        return _reportCreationStrategy.createReport(productList);
    }
}
