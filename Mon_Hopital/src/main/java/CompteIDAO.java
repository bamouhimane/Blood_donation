


import java.sql.SQLException;
import java.util.List;

public interface CompteIDAO extends IDAO<Compte> {
    //String afficherDetails(String CNIE) throws Exception;
	Compte getByCNIE(String CNIE) throws SQLException, Exception;
    void delete(String CNIE);
    Compte getOne(String CNIE) throws Exception;
    Compte afficherDetails(String CNIE);

	

}
