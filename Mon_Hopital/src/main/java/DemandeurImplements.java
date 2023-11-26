import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jakarta.faces.context.FacesContext;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;


public class DemandeurImplements  implements DemandeurDAO   {
	
	
	
	Connection connection;

    public DemandeurImplements() throws Exception {
        this.connection = SingleConnexion.getConnection();
    }
    @Override
    public void add(Demandeur D) {
        System.out.println("bonjour depuis fct add");
        PreparedStatement pstDemandeur = null;
        PreparedStatement pstOperation = null;
        System.out.println("Using connection: " + connection); // Ajout de ce message de débogage

        try {
            if (demandeurExists(D)) {
                System.out.println("Le demandeur existe déjà dans la table.");
                return;
            }
        } catch (SQLException e1) {
            System.out.println("erreur de la fct demandeexits");

            e1.printStackTrace();
        }

        String sqlDemandeur = "insert into demandeur (CNIE,nom, adresse, telephone,sexe,age,dateNaissance,groupeSanguin) values (?,?,?,?,?,?,?,?)";
        String sqlOperation = "insert into opérationdemande (id_demandeur, date_demande,quantité_sang, id_centre) VALUES (?, ?, ?,?)";

        try {
            pstDemandeur = connection.prepareStatement(sqlDemandeur, Statement.RETURN_GENERATED_KEYS);
            
            pstDemandeur.setString(1, D.getCNIE());
            pstDemandeur.setString(2, D.getNom());
            pstDemandeur.setString(3, D.getAdresse());
            pstDemandeur.setString(4, D.getTelephone());
            pstDemandeur.setString(5, D.getSexe());
            pstDemandeur.setInt(6, D.getAge());
            Date dateNaissance = Date.valueOf(D.getDateNaissance());
            pstDemandeur.setDate(7, dateNaissance);
            pstDemandeur.setString(8,D.getGrpS());

            int rowsAffected = pstDemandeur.executeUpdate();
            long idDemandeur = -1; // Initialisation avec une valeur par défaut
            if (rowsAffected > 0) {
                // Obtenir l'ID généré pour demandeur
                ResultSet generatedKeys = pstDemandeur.getGeneratedKeys();

                if (generatedKeys.next()) {
                    idDemandeur = generatedKeys.getLong(1);
                }
                System.out.println("Enregistrement dans la table demandeur réussi avec l'ID : " + idDemandeur);

                pstOperation = connection.prepareStatement(sqlOperation);
                pstOperation.setLong(1, idDemandeur); // Utilisation de l'ID généré pour id_demandeur
                pstOperation.setDate(2, Date.valueOf(D.getDateDemande()));
                pstOperation.setDouble(3,D.getQteSang());
    	        Login_users user=new Login_users();
    	        
    	        try {
    				user.red();
    			} catch (Exception e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			} // Assurez-vous de gérer les exceptions appropriées

    	     // Récupérez la valeur idCentre depuis la session
    	        String idCentre = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("idCentre");
                pstOperation.setString(4,idCentre);
                

                pstOperation.executeUpdate();
                System.out.println("Enregistrement dans la table operationdemande réussi.");

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Erreur lors de l'ajout du demandeur : " + e.getMessage());
        } finally {
            
            
        }
    }

 // Méthode pour vérifier si le demandeur existe déjà dans la table
    public boolean demandeurExists(Demandeur D) throws SQLException {
        String checkIfExistsQuery = "SELECT COUNT(*) FROM demandeur join opérationdemande on idDemandeur=id_demandeur WHERE CNIE = ? and date_demande=? ";
        try (PreparedStatement checkIfExistsStatement = connection.prepareStatement(checkIfExistsQuery)) {
            checkIfExistsStatement.setString(1, D.getCNIE());
            checkIfExistsStatement.setDate(2, Date.valueOf(D.getDateDemande()));

            ResultSet resultSet = checkIfExistsStatement.executeQuery();
            resultSet.next();
            return resultSet.getInt(1) > 0;
        }
    }
	

	@Override
	public void update(Demandeur D) {
		
	        PreparedStatement pstDemandeur = null;
	        PreparedStatement pstOperation = null;

	        String updateDemandeurQuery = "UPDATE demandeur SET  nom=?, adresse=?, telephone=? ,sexe=? ,age=? ,groupeSanguin=?,dateNaissance=?   WHERE CNIE=? ";
	        String updateOperationQuery = "UPDATE opérationdemande SET date_demande=?, quantité_sang=? WHERE id_demandeur=?";
	        System.out.println(D.getNom());
	        try {
	            connection.setAutoCommit(false); 
	            pstDemandeur = connection.prepareStatement(updateDemandeurQuery);
	            pstDemandeur.setString(1, D.getNom());
	            pstDemandeur.setString(2, D.getAdresse());
	            pstDemandeur.setString(3, D.getTelephone());
	            pstDemandeur.setString(4, D.getSexe());
	            pstDemandeur.setInt(5, D.getAge());
	            pstDemandeur.setString(6,D.getGrpS());
	            pstDemandeur.setDate(7, Date.valueOf(D.getDateNaissance()));
	            pstDemandeur.setString(8, D.getCNIE());

	            pstDemandeur.executeUpdate();

	            
	            // Mise à jour de la table operationdemande
	            pstOperation = connection.prepareStatement(updateOperationQuery);
	            pstOperation.setDate(1, Date.valueOf(D.getDateDemande()));
	            pstOperation.setDouble(2, D.getQteSang());
	            pstOperation.setLong(3, getIdDemandeur(D));// une méthode pour obtenir l'ID du demandeur

	            pstOperation.executeUpdate();
			 	System.out.println("la modification est bien effectuée");
			 	
	            connection.commit(); // Valider la transaction
	            UpdateEtatDemande() ;
	            System.out.println(D.getNom());
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
		
		
	    // Méthode pour obtenir l'ID du demandeur à partir du CNIE
	    private long getIdDemandeur(Demandeur D) throws SQLException {
	        String selectIdQuery = "SELECT idDemandeur FROM demandeur WHERE CNIE=?";
	        try (PreparedStatement selectIdStatement = connection.prepareStatement(selectIdQuery)) {
	            selectIdStatement.setString(1, D.getCNIE());
	            
	            ResultSet resultSet = selectIdStatement.executeQuery();
	            if (resultSet.next()) {
	                return resultSet.getLong("idDemandeur");
	            }
	            return -1; // Retourne -1 si l'ID n'est pas trouvé 
	        }
	    }


	    /* @Override
	    public Demandeur getOne(String nom, LocalDate dateNaissance) {
	        PreparedStatement pstDemandeur = null;
	        String sqlGetOne = "select CNIE, nom, adresse, telephone,sexe,age,dateNaissance,groupeSanguin,date_demande ,quantité_sang,état_demande from  demandeur join opérationdemande on idDemandeur=id_demandeur where nom=? and dateNaissance=? ";
	        try {
	            pstDemandeur = connection.prepareStatement(sqlGetOne);
	            pstDemandeur.setString(1, nom);
	            Date sqlDateNaissance = Date.valueOf(dateNaissance);
	            pstDemandeur.setDate(2, sqlDateNaissance);
	            ResultSet resultSet = pstDemandeur.executeQuery();

	            if (resultSet.next()) {
	                Date dateN = resultSet.getDate("dateNaissance");
	                Date dateD = resultSet.getDate("date_demande");
	                return new Demandeur(
	                        resultSet.getString("CNIE"),

	                        resultSet.getString("nom"),
	                        resultSet.getString("adresse"),
	                        resultSet.getString("telephone"),
	                        resultSet.getString("sexe"),
	                        resultSet.getInt("age"),
	                        dateN.toLocalDate(),
	                        resultSet.getString("groupeSanguin"),
	                        resultSet.getDouble("quantité_sang"),
	                        dateD.toLocalDate(),
	                        resultSet.getString("état_demande")
	                );
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        } finally {
	            if (pstDemandeur != null) {
	                try {
	                    pstDemandeur.close();
	                } catch (SQLException e) {
	                    e.printStackTrace();
	                }
	            }
	        }
	        return null;
	    }*/


	@Override
	public  List<Demandeur> getAll() {
		UpdateEtatDemande() ;
		 List<Demandeur> demandeurs = new ArrayList<>();
		 String sql = "select CNIE, nom, adresse, telephone,sexe,age,dateNaissance,groupeSanguin,date_demande ,quantité_sang,état_demande from  demandeur join opérationdemande on idDemandeur=id_demandeur";
		 try {
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet resultSet = statement.executeQuery();
			 while (resultSet.next()) {
		            Demandeur demandeur = new Demandeur();
	                Date dateD = resultSet.getDate("date_demande");
		            Date dateN = resultSet.getDate("dateNaissance");
		            demandeur.setCNIE(resultSet.getString("CNIE"));

		            demandeur.setNom(resultSet.getString("nom"));
		            demandeur.setAdresse(resultSet.getString("adresse"));
		            demandeur.setTelephone(resultSet.getString("telephone"));
		            demandeur.setSexe(resultSet.getString("sexe"));
		            demandeur.setAge(resultSet.getInt("age"));
		            demandeur.setGrpS(resultSet.getString("groupeSanguin"));
		            demandeur.setDateNaissance(dateN.toLocalDate());
		            demandeur.setDateDemande(dateD.toLocalDate());
		            demandeur.setQteSang(resultSet.getDouble("quantité_sang"));
		            demandeur.setEtat_demande(resultSet.getString("état_demande"));
		            demandeurs.add(demandeur);
		        }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		    return demandeurs;
	}
	public  List<Demandeur> getChwiya() throws Exception {
		UpdateEtatDemande() ;
		 List<Demandeur> demandeurs = new ArrayList<>();
		 String sql = "select CNIE, nom, adresse, telephone,sexe,age,dateNaissance,groupeSanguin,date_demande ,quantité_sang,état_demande from  demandeur join opérationdemande on idDemandeur=id_demandeur where id_centre=?";
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
			//System.out.println(user.getCentreID());
			ResultSet resultSet = statement.executeQuery();
			 while (resultSet.next()) {
		            Demandeur demandeur = new Demandeur();
	                Date dateD = resultSet.getDate("date_demande");
		            Date dateN = resultSet.getDate("dateNaissance");
		            demandeur.setCNIE(resultSet.getString("CNIE"));

		            demandeur.setNom(resultSet.getString("nom"));
		            demandeur.setAdresse(resultSet.getString("adresse"));
		            demandeur.setTelephone(resultSet.getString("telephone"));
		            demandeur.setSexe(resultSet.getString("sexe"));
		            demandeur.setAge(resultSet.getInt("age"));
		            demandeur.setGrpS(resultSet.getString("groupeSanguin"));
		            demandeur.setDateNaissance(dateN.toLocalDate());
		            demandeur.setDateDemande(dateD.toLocalDate());
		            demandeur.setQteSang(resultSet.getDouble("quantité_sang"));
		            demandeur.setEtat_demande(resultSet.getString("état_demande"));
		            demandeurs.add(demandeur);
		        }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		    return demandeurs;
	}
	
	
	public void UpdateEtatDemande() {
		
        PreparedStatement pst1 = null;
        PreparedStatement pst2 = null;
        PreparedStatement pst3 = null;
        PreparedStatement pst4= null;
        PreparedStatement pst5= null;
        String vuesqlQuatiteDemande = "CREATE OR REPLACE VIEW QuantiteDemande AS SELECT groupeSanguin, COALESCE(SUM(quantite_sang_assuree), 0) AS Q1 FROM (SELECT groupeSanguin,idDemandeur,SUM(quantité_sang) AS quantite_sang_assuree FROM demandeur LEFT JOIN opérationdemande ON idDemandeur = id_demandeur AND état_demande = 'assurée' GROUP BY groupeSanguin, idDemandeur) AS subquery GROUP BY groupeSanguin";
        String vuesqlQuatiteDon = "CREATE OR REPLACE VIEW QuantiteDon AS SELECT gs, COALESCE(SUM(quant_sang), 0) AS Q2 FROM donneur d, opdon o WHERE d.idDonneur = o.idDonneur GROUP BY gs";
        String vuesqlQuantiteDispo = "CREATE OR REPLACE VIEW quantiteDispo AS SELECT groupeSanguin, (Q2 - Q1) AS quantiteSangDisp FROM QuantiteDemande, QuantiteDon WHERE groupeSanguin = gs";
        String sqlUpdateDemande = "UPDATE opérationdemande od "
                + "JOIN demandeur d ON od.id_demandeur = d.idDemandeur "
                + "JOIN quantiteDispo qd ON d.groupeSanguin = qd.groupeSanguin "
                + "SET od.état_demande = "
                + "    CASE "
                + "        WHEN od.quantité_sang IS NOT NULL AND qd.quantiteSangDisp IS NOT NULL AND od.quantité_sang <= qd.quantiteSangDisp THEN 'assuré' "
                + "        WHEN od.quantité_sang IS NOT NULL AND qd.quantiteSangDisp IS NOT NULL AND od.quantité_sang > qd.quantiteSangDisp THEN 'en cours' "
                + "        ELSE 'rien' "
                + "    END "
                + "WHERE od.id_operation IS NOT NULL";

        
       

        String setEtatEnCours = "UPDATE opérationdemande od "
                + "SET od.état_demande = 'en Cours' "
                + "WHERE od.état_demande IS NULL";
        		
        		
        try {
            connection.setAutoCommit(false); 

            
            pst1 = connection.prepareStatement(vuesqlQuatiteDemande);
            pst2 = connection.prepareStatement(vuesqlQuatiteDon);
            pst3 = connection.prepareStatement(vuesqlQuantiteDispo);
            pst4 = connection.prepareStatement(sqlUpdateDemande);
            pst5 = connection.prepareStatement(setEtatEnCours);

            pst1.executeUpdate();
            pst2.executeUpdate();
            pst3.executeUpdate();
            pst4.executeUpdate();
            pst5.executeUpdate();

            connection.commit();

        } catch (SQLException e) {
            try {
                connection.rollback(); // En cas d'échec, effectuer un rollback pour annuler les changements
            } catch (SQLException rollbackException) {
                rollbackException.printStackTrace();
            }
            e.printStackTrace();
        } 
  
	}
	
	
	
	
	
	public  List<Demandeur> EtatDemande_EnCours() throws Exception {
		
		List<Demandeur> EtatD= new ArrayList<>();
		
		 String sql = "select CNIE, nom,groupeSanguin,date_demande ,quantité_sang,état_demande from  demandeur join opérationdemande on idDemandeur=id_demandeur  where état_demande=? and id_centre=?";
	     Login_users user=new Login_users();
	        
	        try {
				user.red();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} // Assurez-vous de gérer les exceptions appropriées

	     // Récupérez la valeur idCentre depuis la session
	        String idCentre = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("idCentre");
		 try {
			PreparedStatement statement = connection.prepareStatement(sql);
	        statement.setString(1, "en Cours");
	        statement.setString(2, idCentre);
			ResultSet resultSet = statement.executeQuery();
			 while (resultSet.next()) {
		            Demandeur demandeur = new Demandeur();
	                Date dateD = resultSet.getDate("date_demande");
		            demandeur.setCNIE(resultSet.getString("CNIE"));
		            demandeur.setNom(resultSet.getString("nom"));
		            demandeur.setGrpS(resultSet.getString("groupeSanguin"));
		            demandeur.setDateDemande(dateD.toLocalDate());
		            demandeur.setQteSang(resultSet.getDouble("quantité_sang"));
		            demandeur.setEtat_demande(resultSet.getString("état_demande"));

		            EtatD.add(demandeur);
		        }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		    return EtatD;
	}

public  List<Demandeur> EtatDemande_Assuré() throws Exception {
		
		List<Demandeur> EtatD= new ArrayList<>();
		
		 String sql = "select CNIE, nom,groupeSanguin,date_demande ,quantité_sang,état_demande from  demandeur join opérationdemande on idDemandeur=id_demandeur  where état_demande=? and id_centre=?";
	        Login_users user=new Login_users();
	        
	        try {
				user.red();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} // Assurez-vous de gérer les exceptions appropriées

	     // Récupérez la valeur idCentre depuis la session
	        String idCentre = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("idCentre");
		 try {
			PreparedStatement statement = connection.prepareStatement(sql);
	        statement.setString(1, "assuré");
	        statement.setString(2, idCentre);
			ResultSet resultSet = statement.executeQuery();
			 while (resultSet.next()) {
		            Demandeur demandeur = new Demandeur();
	                Date dateD = resultSet.getDate("date_demande");
		            demandeur.setCNIE(resultSet.getString("CNIE"));
		            demandeur.setNom(resultSet.getString("nom"));
		            demandeur.setGrpS(resultSet.getString("groupeSanguin"));
		            demandeur.setDateDemande(dateD.toLocalDate());
		            demandeur.setQteSang(resultSet.getDouble("quantité_sang"));
		            demandeur.setEtat_demande(resultSet.getString("état_demande"));

		            EtatD.add(demandeur);
		        }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		    return EtatD;
	}

}
