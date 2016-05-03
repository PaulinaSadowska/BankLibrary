package Report;

/**
 * Created by arasz on 03.04.2016.
 */
public class ReportDocument<T>
{
    protected T _report;

    public ReportDocument(T report)
    {
        _report = report;
    }

    public T getReportDocument()
    {
        return _report;
    }
}
