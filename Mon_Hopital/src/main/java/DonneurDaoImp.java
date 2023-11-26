import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import jakarta.faces.context.FacesContext;

public class DonneurDaoImp implements DonneurDAO{
	
	
	
	Connection connection;

    public DonneurDaoImp() throws Exception {
        this.connection = SingleConnexion.getConnection();
    }
	public String evilpwd;
	@Override
	public void add(Donneur d) throws Exception {
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
	        
	        
	        Login_users user=new Login_users();
	        
	        try {
				user.red();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} // Assurez-vous de gérer les exceptions appropriées

	     // Récupérez la valeur idCentre depuis la session
	        String idCentre = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("idCentre");
	        pst1.setString(4,idCentre);
	        System.out.println("AM the evil ID"+idCentre);
	        pst1.executeUpdate(); 
            System.out.println("Enregistrement dans la table operationdon réussi."+idCentre);

            }}
            
            
		
		 catch (SQLException exp) {
	        System.out.println(exp.getMessage());
	    }
	    
		}

	 // Méthode pour vérifier si le donneur existe déjà dans la table
    public boolean donneurExists(Donneur D) throws SQLException {
        String checkIfExistsQuery = "SELECT COUNT(*) FROM donneur d join opdon o on o.idDonneur=d.idDonneur WHERE CIN = ? and date_don=? ";
        try (PreparedStatement checkIfExistsStatement = connection.prepareStatement(checkIfExistsQuery)) {
            checkIfExistsStatement.setString(1, D.getCIN());
            checkIfExistsStatement.setDate(2, Date.valueOf(D.getDateDon()));

            ResultSet resultSet = checkIfExistsStatement.executeQuery();
            resultSet.next();
            return resultSet.getInt(1) > 0;
        }
    }
	
	public List<Donneur> getChwiya() throws Exception {
			List<Donneur> dataList = new ArrayList<>();


	    	String sql = "SELECT * FROM donneur d join opdon o on d.idDonneur=o.idDonneur where idCentre=?";
	    	Login_users user  =new Login_users();
	    	  try {
					user.red();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} // Assurez-vous de gérer les exceptions appropriées
	    	  String idCentre = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("idCentre");
	        try {
	            PreparedStatement statement = connection.prepareStatement(sql);
	            statement.setString(1,idCentre);
	            ResultSet resultSet = statement.executeQuery();
	            

	            while (resultSet.next()) {
	                Donneur data = new Donneur();
	                Date dateD = resultSet.getDate("date_don");
	                data.setNom(resultSet.getString("nom"));
	                data.setPrenom(resultSet.getString("prenom"));
	                data.setAge(resultSet.getString("age"));
	                data.setPoids(resultSet.getString("poids"));
	                data.setTaille(resultSet.getString("taille"));
	                data.setGs(resultSet.getString("gs"));
	                data.setAdr(resultSet.getString("adresse"));
	                data.setTel(resultSet.getString("tel"));
	                data.setCIN(resultSet.getString("CIN"));
	                data.setQgs(resultSet.getString("quant_sang"));
	                data.setDateDon(dateD.toLocalDate());
	                
	                dataList.add(data);
	                
	            }
	            
	        } catch (SQLException e) {
	            e.printStackTrace();
		
	}
	        
	        return dataList;
	}
	
	public List<Donneur> getAll() {
		List<Donneur> dataList = new ArrayList<>();
		

    	String sql = "SELECT * FROM donneur d join opdon o on d.idDonneur=o.idDonneur";
        try {
        	PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            

            while (resultSet.next()) {
                Donneur data = new Donneur();
                Date dateD = resultSet.getDate("date_don");
                data.setNom(resultSet.getString("nom"));
                data.setPrenom(resultSet.getString("prenom"));
                data.setAge(resultSet.getString("age"));
                data.setPoids(resultSet.getString("poids"));
                data.setTaille(resultSet.getString("taille"));
                data.setGs(resultSet.getString("gs"));
                data.setAdr(resultSet.getString("adresse"));
                data.setTel(resultSet.getString("tel"));
                data.setCIN(resultSet.getString("CIN"));
                data.setQgs(resultSet.getString("quant_sang"));
                data.setDateDon(dateD.toLocalDate());
                
                dataList.add(data);
                
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
	
}
        
        return dataList;
}

	@Override
	public void update(Donneur D) {
		
	        PreparedStatement pstDonneur = null;
	        PreparedStatement pstOperation = null;

	        String updateDemandeurQuery = "UPDATE donneur SET  nom=?, prenom=?, age=? ,poids=? , taille=? ,gs=?, adresse=?, tel=?   WHERE CIN=? ";
	        String updateOperationQuery = "UPDATE opdon SET date_don=?, quant_sang=? WHERE idDonneur=?";
	        
	        try {
	            connection.setAutoCommit(false); // Désactiver l'autocommit pour gérer la transaction

	            // Mise à jour de la table demandeur
	            pstDonneur = connection.prepareStatement(updateDemandeurQuery);
	            pstDonneur.setString(1, D.getNom());

	            pstDonneur.setString(2, D.getPrenom());
	            pstDonneur.setString(3, D.getAge());
	            pstDonneur.setString(4, D.getPoids());
	            pstDonneur.setString(5, D.getTaille());
	            pstDonneur.setString(6,D.getGs());
	            pstDonneur.setString(7,D.getAdr());
	            pstDonneur.setString(8,D.getTel());
	            pstDonneur.setString(9, D.getCIN());

	            pstDonneur.executeUpdate();

	            
	            // Mise à jour de la table operationdemande
	            pstOperation = connection.prepareStatement(updateOperationQuery);
	            pstOperation.setDate(1, Date.valueOf(D.getDateDon()));
	            pstOperation.setString(2, D.getQgs());
	            pstOperation.setLong(3, getIdDonneur(D));// une méthode pour obtenir l'ID du demandeur

	            pstOperation.executeUpdate();
			 	System.out.println("la modification est bien effectuée");

	            connection.commit(); // Valider la transaction
	            System.out.println("Transaction committed");
	        } catch (SQLException e) {
	            try {
	                connection.rollback(); // En cas d'échec, effectuer un rollback pour annuler les changements
	            } catch (SQLException rollbackException) {
	                rollbackException.printStackTrace();
	            }
	            e.printStackTrace();
	        } finally {
	            
	        }
	  
		}
    // Méthode pour obtenir l'ID du demandeur à partir du nom et de la date de naissance
	public long getIdDonneur(Donneur D) throws SQLException {
	    String selectIdQuery = "SELECT idDonneur FROM donneur WHERE CIN=?";
	    try (PreparedStatement selectIdStatement = connection.prepareStatement(selectIdQuery)) {
	        selectIdStatement.setString(1, D.getCIN());
	        ResultSet resultSet = selectIdStatement.executeQuery();
	        if (resultSet.next()) {
	            long idDonneur = resultSet.getLong("idDonneur");
	            System.out.println("ID du donneur trouvé: " + idDonneur);
	            return idDonneur;
	        } else {
	            System.out.println("ID du donneur non trouvé.");
	            return -1; // Retourne -1 si l'ID n'est pas trouvé (à adapter selon votre logique)
	        }
	    }
	}

	

	
	}

