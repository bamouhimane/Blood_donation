import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.mindrot.jbcrypt.BCrypt;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

@RequestScoped
@Named
public class Admin_login implements Serializable {
    private static final long serialVersionUID = 1L;
    private static Connection connection ;
    private String pwd;
    private String username_ad;
	private PreparedStatement preparedStatement;
	private ResultSet resultSet;

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getUsername_ad() {
        return username_ad;
    }

    public void setUsername_ad(String username) {
        this.username_ad = username;
    }

    public String redirectToAdminLogin() {
        return "Authentification2.xhtml?faces-redirect=true";
    }
    public Admin_login() {
    	try {
    	Admin_login.connection=SingleConnexion.getConnection();
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }
    
    public String red2() throws Exception {
     
            //System.out.println("Connexion à la base de données réussie");
           
            try  {
            	 String query = "SELECT pasword_ad FROM login_admin WHERE username_ad=?";
            	preparedStatement= connection.prepareStatement(query);
                preparedStatement.setString(1, username_ad);
                resultSet = preparedStatement.executeQuery(); 
                    if (resultSet.next()) {
                        String storedHashedPassword = resultSet.getString("pasword_ad");
                        System.out.println("Password from db: " + resultSet.getString("pasword_ad"));

                        System.out.println("Username entered: " + username_ad);
                        System.out.println("Password entered: " + pwd);

                        // Check if the entered password matches the stored hashed password using BCrypt
                        if (BCrypt.checkpw(pwd, storedHashedPassword)) {
                            System.out.println("Mot de passe correct");
                            return "admin.xhtml?faces-redirect=true";
                            
               

                        } else {
                            System.out.println("Mot de passe incorrect");
                            return "error.xhtml?faces-redirect=true";
                        }
                    } else {
                        System.out.println("Aucun utilisateur trouvé");
                        return "error.xhtml?faces-redirect=true";
                    }
                
            
        } catch (SQLException e) {
            e.printStackTrace(); // Gérer les erreurs de connexion à la base de données
        }finally {
        	
        }

        System.out.println("Redirection vers Authentification.xhtml");
        return "Authentification2.xhtml?faces-redirect=true";
    }}
  