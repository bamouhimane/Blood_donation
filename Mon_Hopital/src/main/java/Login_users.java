import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.mindrot.jbcrypt.BCrypt;
import org.primefaces.event.RowEditEvent;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

@RequestScoped
@Named
public class Login_users implements Serializable {
    private static final long serialVersionUID = 1L;
    private static Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
	private DonneurDaoImp donpwd;

    public Login_users() throws Exception {
        try {
            this.connection = SingleConnexion.getConnection();
            this.donpwd = new DonneurDaoImp();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public Login_users(String pwd) {
        this.pwd = pwd;
    }

    private String pwd;
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String redirectToEmployeeLogin() {
        return "Authentification.xhtml?faces-redirect=true";
    }

    public String red() throws Exception {
        try {
            System.out.println("Connexion à la base de données réussie");
            String query = "SELECT fonction, password FROM login WHERE username=?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String storedHashedPassword = resultSet.getString("password");

                // Check if the entered password matches the stored hashed password using BCrypt
                if (BCrypt.checkpw(pwd, storedHashedPassword)) {
                    System.out.println("Mot de passe correct");

                    String fonction = resultSet.getString("fonction");
                    System.out.println("Fonction: " + fonction);

                    // Nouvelle logique pour vérifier les trois premiers caractères
                    String centreName = pwd.substring(0, 3); // Récupérer les trois premiers caractères du mot de passe
                    String centreQuery = "SELECT idcentre FROM centre WHERE trois_prem_car=?";
                    try (PreparedStatement centreStatement = connection.prepareStatement(centreQuery)) {
                        centreStatement.setString(1, centreName);
                        try (ResultSet centreResultSet = centreStatement.executeQuery()) {
                            if (centreResultSet.next()) {
                                String idCentre = centreResultSet.getString("idcentre");
                                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("idCentre",
                                        idCentre);
                                // System.out.println("Id du Centre: " + idCentre);

                                if ("infirmier".equalsIgnoreCase(fonction)) {
                                    System.out.println("Redirection vers demandeur.xhtml");
                                    return "acceuilDemande.xhtml?faces-redirect=true";

                                } else if ("responsable".equalsIgnoreCase(fonction)) {
                                    System.out.println("Redirection vers donneur.xhtml");
                                    return "acceuilDon.xhtml?faces-redirect=true";
                                } else {
                                    System.out.println("Redirection vers demandeur.xhtml (par défaut)");
                                    return "acceuilDemande.xhtml?faces-redirect=true";
                                }

                            } else {
                                System.out.println(
                                        "Aucun centre trouvé pour les trois premiers caractères du mot de passe");
                                return "error.xhtml";
                            }
                        }
                    }

                } else {
                    System.out.println("Mot de passe incorrect");
                    return "error.xhtml";
                }
            } else {
                System.out.println("Aucun utilisateur trouvé");
                return "error.xhtml";
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Gérer les erreurs de connexion à la base de données
        } finally {

        }

        System.out.println("Redirection vers Authentification.xhtml");
        return "Authentification.xhtml?faces-redirect=true";
    }

    public static String getCentreID(String pwd) {
        // Nouvelle logique pour vérifier les trois premiers caractères
        if (pwd != null && pwd.length() >= 3) {
            String centreName = pwd.substring(0, 3); // Récupérer les trois premiers caractères du mot de passe
            String centreQuery = "SELECT idcentre FROM centre WHERE trois_prem_car=?";
            try (PreparedStatement centreStatement = connection.prepareStatement(centreQuery)) {
                centreStatement.setString(1, centreName);
                try (ResultSet centreResultSet = centreStatement.executeQuery()) {
                    if (centreResultSet.next()) {
                        String idCentre = centreResultSet.getString("idcentre");
                        System.out.println(idCentre);
                        return idCentre;
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace(); // Gérer les erreurs SQL
            }
        }
        return "0";
    }

    public void g(RowEditEvent<?> event) {

    }

}




