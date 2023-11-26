import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class test1 {
	
	Connection connection;

    public test1() throws Exception {
        this.connection = SingleConnexion.getConnection();
    }
	
	public void add(Donneur d) {
		// TODO Auto-generated method stub
		PreparedStatement pst = null;
	    PreparedStatement pst1 = null;
		String sql = "insert into donneur (CIN,nom, prenom , age , poids , taille , gs , adresse , tel) values (?,?,?,?,?,?,?,?,?)";
	    String sql1="insert into opdon (idDonneur, date_don , quant_sang , idCentre) values (?,?,?,?)";

		try {
	
	        pst = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
	        pst.setString(1, d.getCIN());

	        pst.setString(2, d.getNom());
	        pst.setString(3, d.getPrenom());
	        pst.setString(4, d.getAge());
	        pst.setString(5, d.getPoids());
	        pst.setString(6, d.getTaille());
	        pst.setString(7, d.getGs());
	        pst.setString(8, d.getAdr());
	        pst.setString(9, d.getTel());
	        
	        int rowsAffected = pst.executeUpdate();
            long idDonneur = -1; // Initialisation avec une valeur par défaut
            if (rowsAffected > 0) {
                // Obtenir l'ID généré pour demandeur
                ResultSet generatedKeys = pst.getGeneratedKeys();

                if (generatedKeys.next()) {
                    idDonneur = generatedKeys.getLong(1);
                }
	        
            System.out.println("Enregistrement dans la table donneur réussi avec l'ID : " + idDonneur);

		    pst1= connection.prepareStatement(sql1);
		    
	        pst1.setLong(1, idDonneur);
	        pst1.setDate(2, Date.valueOf(d.getDateDon()));
	        pst1.setString(3, d.getQgs());
	        pst1.setLong(4, 1);
	        pst1.executeUpdate(); 
            System.out.println("Enregistrement dans la table operationdon réussi.");

            }}
            
            
		
		 catch (SQLException exp) {
	        System.out.println(exp.getMessage());
	    }
	    
		}

}
