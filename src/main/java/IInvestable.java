import java.math.BigDecimal;

/**
 * Created by palka on 11.03.2016.
 * Określa obiekt umożliwiający otwieranie i zamykanie lokaty
 */
public interface IInvestable {

    /**
     * Otwiera nowa lokate
     * @param newInvestment nowa lokata
     */
    void openInvestment(Investment newInvestment);

    /**
     * Zamyka lokate.
     * @return Kwota zgromadzona na loakcie
     */
    BigDecimal closeInvestment();

}
