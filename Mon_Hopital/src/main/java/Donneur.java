import java.time.LocalDate;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;



@RequestScoped
@Named
public class Donneur implements Cloneable{
	private int id=1;
	private String CIN;
	private String nom;
	private String prenom;
	private String age;
	private String poids;
	private String taille;
	private LocalDate dateDon;
	private String gs;
	private String qgs;
	private String adr;
	private String tel;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id=id;
	}
	
	
	public String getNom(){
		return nom;
		
	}
	
	public void setNom(String nom) {
		this.nom=nom;
	}
	
	public String getPrenom(){
		return prenom;
		
	}
	
	public void setPrenom(String prenom) {
		this.prenom=prenom;
	}
	
	
	public String getAge() {
		return age;
	}
	
	public void setAge(String age) {
		this.age=age;
	}
	
	public String getPoids() {
		return poids;
	}
	
	public void setPoids(String poids) {
		this.poids=poids;
	}
	
	public String getTaille() {
		return taille;
	}
	
	public void setTaille(String taille) {
		this.taille=taille;
	}
	
	public String getGs() {
		return gs;
	}
	
	public void setGs(String gs) {
		this.gs=gs;
	}
	
	public String getAdr() {
		return adr;
	}
	
	public void setAdr(String adr) {
		this.adr=adr;
	}
	
	public String getTel() {
		return tel;
	}
	
	public void setTel(String tel) {
		this.tel=tel;
	}
	

	
	public LocalDate getDateDon() {
		return dateDon;
	}

	public void setDateDon(LocalDate dateDon) {
		this.dateDon = dateDon;
	}

	public String getQgs() {
		return qgs;
	}
	
	public void setQgs(String qgs) {
		this.qgs=qgs;
	}
	

	public String getCIN() {
		return CIN;
	}

	public void setCIN(String cIN) {
		CIN = cIN;
	}
	
	
    @Override
	public String toString() {
		return "Donneur [id=" + id + ", CIN=" + CIN + ", nom=" + nom + ", prenom=" + prenom + ", age=" + age
				+ ", poids=" + poids + ", taille=" + taille + ", dateDon=" + dateDon + ", gs=" + gs + ", qgs=" + qgs
				+ ", adr=" + adr + ", tel=" + tel + "]";
	}

	@Override
    public Donneur clone() {
        try {
            return (Donneur) super.clone();
        } catch (CloneNotSupportedException e) {
            // Gérer l'exception de clonage si nécessaire
            e.printStackTrace();
            return null;
        }
    }
	
	 public Donneur(String CIN, String nom, String prenom, String age, String poids, String taille,
             LocalDate dateDon, String gs, String qgs, String adr, String tel) {
		 
		 this.CIN = CIN;
		 this.nom = nom;
		 this.prenom = prenom;
		 this.age = age;
		 this.poids = poids;
		 this.taille = taille;
		 this.dateDon = dateDon;
		 this.gs = gs;
		 this.qgs = qgs;
		 this.adr = adr;
		 this.tel = tel;
}
	 
	 public Donneur() {
		 
	 }

	}