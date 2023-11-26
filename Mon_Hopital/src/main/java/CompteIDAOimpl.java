import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mindrot.jbcrypt.BCrypt;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;

public class CompteIDAOimpl implements CompteIDAO {
	Connection connection;

    public CompteIDAOimpl() throws Exception {
        this.connection = SingleConnexion.getConnection();
    }
    private List<Compte> listeComptesAdmin = new ArrayList<>();

    public void add(Compte objet) {
        if (objet.getUsername().isEmpty() ||
            objet.getPassword().isEmpty() ||
            objet.getFonction().isEmpty() ||
            objet.getCNIE().isEmpty() ||
            objet.getNom().isEmpty() ||
            objet.getPrenom().isEmpty()) {
            System.out.println("vide");
            FacesContext facesContext = FacesContext.getCurrentInstance();
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur : Tous les champs doivent être remplis.", null));
            return; // Stop execution if any field is missing
        }

        PreparedStatement preparedStatement = null;

        try {
            String sqlCreer = "INSERT INTO login (username, password, fonction, CNIE, nom, prenom, creation_date) VALUES (?, ?, ?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(sqlCreer);

            objet.setLastModifiedDate(new Timestamp(System.currentTimeMillis()));
            System.out.println(objet.getLastModifiedDate());
            preparedStatement.setString(1, objet.getUsername());
            String hashedPassword = BCrypt.hashpw(objet.getPassword(), BCrypt.gensalt());
            preparedStatement.setString(2, hashedPassword);
            preparedStatement.setString(3, objet.getFonction());
            preparedStatement.setString(4, objet.getCNIE());
            preparedStatement.setString(5, objet.getNom());
            preparedStatement.setString(6, objet.getPrenom());
            preparedStatement.setTimestamp(7, objet.getLastModifiedDate());

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Compte créé avec succès.");
                listeComptesAdmin = getAll();
            } else {
                System.out.println("Échec de la création du compte.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e1) {
            e1.printStackTrace();
        } finally {
           
        }
    }



    @Override
    public List<Compte> getAll() {
        List<Compte> comptes = new ArrayList<>();

        String query = "SELECT * FROM login";
        
        try {
        	PreparedStatement preparedStatement1 = connection.prepareStatement(query);
        
            ResultSet resultSet = preparedStatement1.executeQuery();

            while (resultSet.next()) {
                Compte compte = new Compte(
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getString("CNIE"),
                        resultSet.getString("nom"),
                        resultSet.getString("prenom"),
                        resultSet.getString("fonction"),
                        resultSet.getTimestamp("creation_date"),
                        resultSet.getTimestamp("last_modified")
                );
                comptes.add(compte);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return comptes;
    }


   

    @Override
    public Compte getOne(String CNIE) {
            String query = "SELECT * FROM login WHERE CNIE = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, CNIE);
                ResultSet resultSet = preparedStatement.executeQuery();          
                    if (resultSet.next()) {
                        Compte compte = new Compte(
                                resultSet.getString("username"),
                                resultSet.getString("password"),
                                resultSet.getString("CNIE"),
                                resultSet.getString("nom"),
                                resultSet.getString("prenom"),
                                resultSet.getString("fonction"),
                                resultSet.getTimestamp("creation_date"),
                                resultSet.getTimestamp("last_modified")
                        );

                        return compte;
                    }
                
            
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void delete(String CNIE) {
        System.out.println("CNIE avant suppression : " + CNIE);

            String query = "DELETE FROM login WHERE CNIE = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, CNIE);

                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Compte supprimé avec succès.");
                } else {
                    System.out.println("Avertissement : Aucun compte n'a été supprimé. Le compte avec CNIE = " + CNIE + " n'existe peut-être pas.");
                }
            
        } catch (SQLException e) {
            System.out.println("Erreur SQL lors de la suppression du compte : " + e.getMessage());
            e.printStackTrace();}

    }

    @Override
    public void update(Compte compte) {
        
            // Vérifier que tous les champs nécessaires sont remplis
            if (compte.getUsername() == null || compte.getPassword() == null || compte.getFonction() == null
                    || compte.getCNIE() == null || compte.getNom() == null || compte.getPrenom() == null) {
                System.out.println("Erreur : Tous les champs doivent être remplis.");
                return; // Arrêter l'exécution si un champ est manquant
            }

            String query = "UPDATE login SET username = ?, password = ?, fonction = ?, nom = ?, prenom = ?, last_modified = CURRENT_TIMESTAMP WHERE CNIE = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

                preparedStatement.setString(1, compte.getUsername());
                String hashedPassword = BCrypt.hashpw(compte.getPassword(), BCrypt.gensalt());
                preparedStatement.setString(2, hashedPassword);
                preparedStatement.setString(3, compte.getFonction());
                preparedStatement.setString(4, compte.getNom());
                preparedStatement.setString(5, compte.getPrenom());
                preparedStatement.setString(6, compte.getCNIE());
                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Compte modifié avec succès.");

                    
                    
                } else {
                    System.out.println("Échec de la modification du compte.");
                }
            
        } catch (SQLException e) {
            e.printStackTrace();
        } 

    }

   

    @Override
    public Compte afficherDetails(String CNIE) {
        PreparedStatement preparedStatement3 = null;
        ResultSet resultSet = null;
        String query = "SELECT * FROM login WHERE CNIE = ?";
        Compte compte = null;

        try {
            preparedStatement3 = connection.prepareStatement(query);
            preparedStatement3.setString(1, CNIE);
            resultSet = preparedStatement3.executeQuery();

            if (resultSet.next()) {
                compte = new Compte(
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getString("CNIE"),
                        resultSet.getString("nom"),
                        resultSet.getString("prenom"),
                        resultSet.getString("fonction"),
                        resultSet.getTimestamp("creation_date"),
                        resultSet.getTimestamp("last_modified")
                );
                System.out.println("CNIE" + compte.getCNIE());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle SQL errors
        } finally {
            
        }
        return compte;
    }

    
    
    
    @SuppressWarnings("finally")
	@Override
    public Compte getByCNIE(String CNIE){
        PreparedStatement preparedStatement2 = null;
        ResultSet resultSet = null;

        try {
            String query = "SELECT * FROM login WHERE CNIE = ?";
            preparedStatement2 = connection.prepareStatement(query);
            preparedStatement2.setString(1, CNIE);
            resultSet = preparedStatement2.executeQuery();

            while(resultSet.next()) {
                return new Compte(
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getString("CNIE"),
                        resultSet.getString("nom"),
                        resultSet.getString("prenom"),
                        resultSet.getString("fonction"),
                        resultSet.getTimestamp("creation_date"),
                        resultSet.getTimestamp("last_modified")
                );
            } 
            return null;
            
        } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
            
		return null;
    }
    }




public boolean exists(String cNIEToDelete) {
    // Utilisez votre logique de vérification pour déterminer si le compte existe
    // Dans ce cas, vous pouvez vérifier s'il existe un compte avec le CNIE spécifié dans la base de données.

    String query = "SELECT COUNT(*) FROM login WHERE CNIE = ?";
    try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
        preparedStatement.setString(1, cNIEToDelete);

        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                int rowCount = resultSet.getInt(1);
                return rowCount > 0; // Retourne vrai si au moins un compte correspond au CNIE
            }
        }
    } catch (SQLException e) {
        System.out.println("Erreur SQL lors de la vérification de l'existence du compte : " + e.getMessage());
        e.printStackTrace();
    }

    return false; // Le compte n'existe pas (en cas d'erreur ou autre situation)
}


public boolean existsByCNIE(String cnie) {
    String query = "SELECT COUNT(*) FROM login WHERE CNIE = ?";
    return exists(query, cnie);
}

public boolean existsByUsername(String username) {
    String query = "SELECT COUNT(*) FROM login WHERE username = ?";
    return exists(query, username);
}

public boolean existsByPassword(String password) {
    String query = "SELECT COUNT(*) FROM login WHERE password = ?";
    return exists(query, password);
}

private boolean exists(String query, String value) {
    try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
        preparedStatement.setString(1, value);

        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                int rowCount = resultSet.getInt(1);
                return rowCount > 0;
            }
        }
    } catch (SQLException e) {
        System.out.println("Erreur SQL lors de la vérification de l'existence : " + e.getMessage());
        e.printStackTrace();
    }

    return false;
}
}

  
    
    
    
    
   

