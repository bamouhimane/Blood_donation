import java.time.LocalDate;

public class Demandeur implements Cloneable{
	private long idDemandeur;
    private String CNIE;

    private String nom;
    private String adresse;
    private String telephone;
    private String sexe;
    private int age;
    private LocalDate dateNaissance;
    private String  grpS;
    private double qteSang;
    private LocalDate dateDemande;
    private String etat_demande;
    
    
    
    public String getCNIE() {
		return CNIE;
	}


	public void setCNIE(String cNIE) {
		CNIE = cNIE;
	}


	@Override
	public String toString() {
		return "Demandeur [ CNIE=" + CNIE + ",nom=" + nom + ", adresse=" + adresse + ", telephone="
				+ telephone + ", sexe=" + sexe + ", age=" + age + ", dateNaissance=" + dateNaissance + ", grpS=" + grpS
				+ ", qteSang=" + qteSang + ", dateDemande=" + dateDemande + ", etat_demande=" + etat_demande + "]";
	}


	public long getIdDemandeur() {
		return idDemandeur;
	}


	public void setIdDemandeur(long idDemandeur) {
		this.idDemandeur = idDemandeur;
	}


	public String getNom() {
		return nom;
	}


	public void setNom(String nom) {
		this.nom = nom;
	}


	public String getAdresse() {
		return adresse;
	}


	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}


	public String getTelephone() {
		return telephone;
	}


	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}


	public String getSexe() {
		return sexe;
	}


	public void setSexe(String sexe) {
		this.sexe = sexe;
	}


	public int getAge() {
		return age;
	}


	public void setAge(int age) {
		this.age = age;
	}


	public LocalDate getDateNaissance() {
		return dateNaissance;
	}


	public void setDateNaissance(LocalDate dateNaissance) {
		this.dateNaissance = dateNaissance;
	}


	public String getGrpS() {
		return grpS;
	}


	public void setGrpS(String grpS) {
		this.grpS = grpS;
	}


	public double getQteSang() {
		return qteSang;
	}


	public void setQteSang(double qteSang) {
		this.qteSang = qteSang;
	}


	public LocalDate getDateDemande() {
		return dateDemande;
	}


	public void setDateDemande(LocalDate dateDemande) {
		this.dateDemande = dateDemande;
	}


	public String getEtat_demande() {
		return etat_demande;
	}


	public void setEtat_demande(String etat_demande) {
		this.etat_demande = etat_demande;
	}


	public Demandeur() {
    }
    
  
    public Demandeur(String CNIE,String nom,String adresse, String telephone,String sexe, int age, LocalDate dateNaissance,String  grpS,double qteSang,LocalDate dateDemande){
    	this.CNIE = CNIE;
    	this.nom = nom;
        this.adresse = adresse;
        this.telephone = telephone;
        this.sexe = sexe;
        this.age = age;
        this.dateNaissance = dateNaissance;
        this.grpS = grpS;
        this.qteSang = qteSang;
        this.dateDemande = dateDemande;
       // this.etat_demande=etat_demande;
        
       }
    @Override
    public Demandeur clone() {
        try {
            return (Demandeur) super.clone();
        } catch (CloneNotSupportedException e) {
            // Gérer l'exception de clonage si nécessaire
            e.printStackTrace();
            return null;
        }
    }
}

   