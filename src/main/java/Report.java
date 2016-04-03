/**
 * Created by arasz on 03.04.2016.
 */
public class Report<T>
{
    protected T _report;

    public Report(T report)
    {
        _report = report;
    }

    public T getReport()
    {
        return _report;
    }
}
