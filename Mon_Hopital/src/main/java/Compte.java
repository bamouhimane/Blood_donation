import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Compte implements Serializable {
    private static final long serialVersionUID = 1L;
    private String username;
    private String password;
    private String CNIE;
    private String nom;
    private String prenom;
    private String fonction;
    private Timestamp creationDate;
    private Timestamp lastModifiedDate;

    

    public Compte() {
    }

    // Constructeur
    public Compte(String username, String password, String CNIE, String nom, String prenom, String fonction,
            String creationDate, String lastModifiedDate) {
        this.username = username;
        this.password = password;
        this.CNIE = CNIE;
        this.nom = nom;
        this.prenom = prenom;
        this.fonction = fonction;
        this.creationDate = Timestamp.valueOf(creationDate);
        this.lastModifiedDate = Timestamp.valueOf(lastModifiedDate);
    }
    public Compte(String username, String password, String CNIE, String nom, String prenom, String fonction,
            Timestamp creationDate, Timestamp lastModifiedDate) {
        this.username = username;
        this.password = password;
        this.CNIE = CNIE;
        this.nom = nom;
        this.prenom = prenom;
        this.fonction = fonction;
        this.creationDate = creationDate;
        this.lastModifiedDate = lastModifiedDate;
    }

    
    
    
    private int modificationCount;
    

    

    

    public int getModificationCount() {
        return modificationCount;
    }

    public void setModificationCount(int modificationCount) {
        this.modificationCount = modificationCount;
    }

    

    
    public Timestamp getCreationDate() {
        return creationDate;
    }
    public Date getLastModifiedDateAsDate() {
        return new Date(lastModifiedDate.getTime());
    }

    public Timestamp getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Timestamp currentTimestamp) {
        this.lastModifiedDate = currentTimestamp;
    }

    // Getter et Setter pour chaque propriété

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCNIE() {
        return CNIE;
    }

    public void setCNIE(String CNIE) {
        this.CNIE = CNIE;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getFonction() {
        return fonction;
    }

    public void setFonction(String fonction) {
        this.fonction = fonction;
    }

	@Override
	public String toString() {
		return "Compte [username=" + username + ", password=" + password + ", CNIE=" + CNIE + ", nom=" + nom
				+ ", prenom=" + prenom + ", fonction=" + fonction + ", creationDate=" + creationDate
				+ ", lastModifiedDate=" + lastModifiedDate + ", modificationCount=" + modificationCount + "]";
	}


}
