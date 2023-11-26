import jakarta.inject.Named;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.primefaces.event.RowEditEvent;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;

@Named
@RequestScoped
public class InscriptionBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private Demandeur demandeur = new Demandeur();
    private List<Demandeur> demandeClone;

    public InscriptionBean() {
        try {
            demandeClone = getClonedDemandeur();
        } catch (Exception e) {
            // Gérez l'exception de manière appropriée (peut-être en journalisant ou en lançant une nouvelle exception)
            e.printStackTrace();
        }
    }

    public Demandeur getDemandeur() {
        return demandeur;
    }

    public void setDemandeur(Demandeur demandeur) {
        this.demandeur = demandeur;
    }

    public List<Demandeur> getDemandeClone() {
        return demandeClone;
    }

    public void setDemandeClone(List<Demandeur> demandeClone) {
        this.demandeClone = demandeClone;
    }

    public String enregistrer() throws Exception {
        DemandeurImplements demande;
        try {
            demande = new DemandeurImplements();
            if (demande.demandeurExists(demandeur)) return "ErrorDemande.xhtml";
            demande.add(demandeur);
            demande.UpdateEtatDemande();
            return "SuccessDemande.xhtml";
        } catch (Exception e) {
            throw e;
        }
    }

    public String Modifier() throws Exception {
        DemandeurImplements demande;
        try {
            demande = new DemandeurImplements();
            demande.update(demandeur);
            System.out.println("hola");
            return "confirmation.xhtml";
        } catch (Exception e) {
            throw e;
        }
    }

    public List<Demandeur> getDemandeurs() throws Exception {
    	
        DemandeurImplements demande = new DemandeurImplements();
        demande.UpdateEtatDemande();
        return demande.getAll();
    }

    public void onRowEdit(RowEditEvent<?> event) throws Exception {
        System.out.println("Updated data: " + event.getObject());
        DemandeurImplements demande = new DemandeurImplements();
        demande.update((Demandeur) event.getObject());

        System.out.println("hi");

        FacesMessage msg = new FacesMessage("Demandeur Edited", ((Demandeur) event.getObject()).getCNIE());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowCancel(RowEditEvent<?> event) {
        FacesMessage msg = new FacesMessage("Edit Cancelled", ((Demandeur) event.getObject()).getCNIE());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public List<Demandeur> getClonedDemandeur() throws Exception {
        DemandeurImplements demande = new DemandeurImplements();
        List<Demandeur> results = new ArrayList<>();
        List<Demandeur> originals = demande.getChwiya();
        for (Demandeur original : originals) {
            results.add(original.clone());
        }
        return results;
    }

    public List<Demandeur> getEtatEnCours() throws Exception {
        DemandeurImplements demande = new DemandeurImplements();
        return demande.EtatDemande_EnCours();
    }

    public List<Demandeur> Etat_Assuré() throws Exception {
        DemandeurImplements demande = new DemandeurImplements();
        return demande.EtatDemande_Assuré();
    }
}
