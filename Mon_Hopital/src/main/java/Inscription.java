import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

@RequestScoped
@Named
public class Inscription {
    private Compte compte1 = new Compte();
    private List<Compte> listeComptesAdmin = new ArrayList<>();
    private String successMessage;

    // Getters and Setters
    public Compte getCompte1() {
        return compte1;
    }

    public void setCompte1(Compte compte1) {
        this.compte1 = compte1;
    }

    public List<Compte> getListeComptesAdmin() {
        return listeComptesAdmin;
    }

    // Méthode pour ajouter un message de succès
    private void addSuccessMessage(String message) {
        FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, message, null);
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        successMessage = message;
    }

    // Méthode pour ajouter un message d'erreur
    private void addErrorMessage(String message) {
        FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null);
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
    }

    public String redirectToAdmin() {
        return "admin.xhtml?faces-redirect=true";
    }
    private void addErrorMessageAndRedirect(String message, String redirectPage) {
        FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null);
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        
        try {
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            externalContext.redirect(redirectPage);
        } catch (IOException e) {
            e.printStackTrace(); // Gérer l'exception selon vos besoins
        }
    }

    
 // Méthode pour l'ajout d'un compte
 // Méthode pour l'ajout d'un compte
    public void ajouter() throws Exception {
        CompteIDAOimpl compteIDOA = new CompteIDAOimpl();
        boolean success = false;  // Variable pour suivre si l'ajout a réussi

        try {
            // Vérifier si l'un des champs est vide
            if (isAnyFieldEmpty()) {
                addErrorMessage("Veuillez remplir tous les champs avant de créer le compte.");
                // Rediriger vers "error_c.xhtml" en cas de champ vide
                ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
                externalContext.redirect(externalContext.getRequestContextPath() + "/error_cr.xhtml");
            } else {
                // Vérifier si le CNIE, le username ou le password existe déjà
                if (compteIDOA.existsByCNIE(compte1.getCNIE()) || compteIDOA.existsByUsername(compte1.getUsername()) ) {
                    addErrorMessage("Le CNIE, le username ou le password existe déjà. Veuillez choisir des valeurs uniques.");
                    ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
                    externalContext.redirect(externalContext.getRequestContextPath() + "/compte_existe_deja.xhtml");
                } else {
                    compteIDOA.add(compte1);

                    // Vérifier si le compte a été ajouté avec succès
                    if (compteIDOA.getOne(compte1.getCNIE()) != null) {
                        success = true;
                        addSuccessMessage("Le compte a été créé avec succès.");
                        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
                        externalContext.redirect(externalContext.getRequestContextPath() + "/success1.xhtml");
                        // Rafraîchir la liste après l'ajout
                        consulter();
                    }
                }
            }
        } catch (Exception e) {
            // Gérer l'erreur ici
            addErrorMessage("Erreur lors de la création du compte : " + e.getMessage());
        }

        // Si l'ajout n'a pas réussi, rediriger vers la page d'erreur
        if (!success) {
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            externalContext.redirect(externalContext.getRequestContextPath() + "/error_c.xhtml");
        }
    }

    // Méthode pour la consultation des comptes
    private boolean validationRequired = true;

    public String consulter() throws Exception {
        CompteIDAOimpl compteIDOA = new CompteIDAOimpl();
        try {
            // Désactiver la validation pour la consultation
            setValidationRequired(false);

            // Appeler la méthode getAll() de la couche DAO pour obtenir tous les comptes
            listeComptesAdmin = compteIDOA.getAll();
            return "liste_compte.xhtml";
        } catch (Exception e) {
            addErrorMessage("Erreur lors de la consultation des comptes : " + e.getMessage());
            return null; // ou une autre chaîne de navigation appropriée
        } finally {
            // Réactiver la validation après la consultation
            setValidationRequired(true);
        }
    }

    private void setValidationRequired(boolean b) {
		// TODO Auto-generated method stub
		
	}

	// Méthode pour la suppression d'un compte
    private String CNIEToDelete;

    public String getCNIEToDelete() {
        return CNIEToDelete;
    }

    public void setCNIEToDelete(String CNIEToDelete) {
        this.CNIEToDelete = CNIEToDelete;
    }

 // Méthode pour la suppression d'un compte
 // Méthode pour la suppression d'un compte
 // Méthode pour la suppression d'un compte
    public void supprimer() throws Exception {
        CompteIDAOimpl compteIDOA = new CompteIDAOimpl();

        // Vérifier si CNIEToDelete est vide
        if (CNIEToDelete == null || CNIEToDelete.isEmpty()) {
            addErrorMessage("Veuillez spécifier le CNIE à supprimer.");
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            externalContext.redirect(externalContext.getRequestContextPath() + "/error_c.xhtml");
            return;
        }

        try {
            // Vérifier d'abord si le compte existe
            if (compteIDOA.exists(CNIEToDelete)) {
                compteIDOA.delete(CNIEToDelete);

                // Rafraîchir la liste après la suppression
                consulter();
                addSuccessMessage("Le compte a été supprimé avec succès.");
                ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
                externalContext.redirect(externalContext.getRequestContextPath() + "/success1.xhtml");
                return; // Terminer la méthode après la redirection vers la page de succès
            } else {
                // Si le compte n'existe pas, rediriger vers la page d'erreur
                addErrorMessage("Le compte avec CNIE " + CNIEToDelete + " n'existe pas.");
            }
        } catch (Exception e) {
            // Gérer l'erreur ici
            addErrorMessage("Erreur lors de la suppression du compte : " + e.getMessage());
        }

        // En cas d'erreur, rediriger vers la page d'erreur
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        externalContext.redirect(externalContext.getRequestContextPath() + "/error_c.xhtml");
    }


 // Méthode pour la modification d'un compte
    public void modifier() throws Exception {
        CompteIDAOimpl compteIDOA = new CompteIDAOimpl();

        // Vérifier si le CNIE existe dans la base de données
        if (compteIDOA.getOne(compte1.getCNIE()) == null) {
            // Le compte n'existe pas, rediriger vers "compt_mod.xhtml"
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            externalContext.redirect(externalContext.getRequestContextPath() + "/compt_mod.xhtml");
        } else {
            // Vérifier si l'un des champs est vide
            if (isAnyFieldEmpty()) {
                addErrorMessage("Veuillez remplir tous les champs avant de modifier le compte.");
                // Rediriger vers "error_c.xhtml" en cas de champ vide
                ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
                externalContext.redirect(externalContext.getRequestContextPath() + "/error_cr.xhtml");
            } else {
                // Tous les champs sont remplis, essayer de mettre à jour le compte
                try {
                    compteIDOA.update(compte1);

                    // Rafraîchir la liste après la modification
                    consulter();
                    addSuccessMessage("Le compte a été modifié avec succès.");
                    ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
                    externalContext.redirect("success1.xhtml");
                } catch (Exception e) {
                    // Gérer l'erreur ici
                    addErrorMessage("Erreur lors de la modification du compte : " + e.getMessage());
                    
                    // Si la modification échoue, rediriger vers "error_c.xhtml"
                    ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
                    externalContext.redirect(externalContext.getRequestContextPath() + "/error_c.xhtml");
                }
            }
        }
    }

    // Méthode pour vérifier si l'un des champs est vide
    private boolean isAnyFieldEmpty() {
        return compte1.getUsername() == null || compte1.getUsername().isEmpty() ||
               compte1.getPassword() == null || compte1.getPassword().isEmpty() ||
               compte1.getFonction() == null || compte1.getFonction().isEmpty() ||
               compte1.getCNIE() == null || compte1.getCNIE().isEmpty() ||
               compte1.getNom() == null || compte1.getNom().isEmpty() ||
               compte1.getPrenom() == null || compte1.getPrenom().isEmpty();
    }



    // Méthode pour afficher les détails d'un compte
    private Map<String, Compte> compteDetails = new HashMap<>();

    public Map<String, Compte> getCompteDetails() {
        return compteDetails;
    }

    public void setCompteDetails(Map<String, Compte> compteDetails) {
        this.compteDetails = compteDetails;
    }

    private String CNIEToDisplay; // Nouvelle propriété pour stocker le CNIE saisi

    public String getCNIEToDisplay() {
        return CNIEToDisplay;
    }

    public void setCNIEToDisplay(String CNIEToDisplay) {
        this.CNIEToDisplay = CNIEToDisplay;
    }

    public String afficherDetails() throws Exception {
        CompteIDAOimpl compteIDAO = new CompteIDAOimpl();
        Compte compte = compteIDAO.afficherDetails(CNIEToDisplay);

        if (compte != null) {
            // Stockez les détails du compte dans la carte avec le CNIE comme clé
            compteDetails.put(CNIEToDisplay, compte);
            return "details.xhtml";
        } else {
            // Le compte n'a pas été trouvé, vous pouvez gérer cela comme vous le souhaitez
            addErrorMessage("Le compte n'a pas été trouvé.");
            return null; // ou une autre chaîne de navigation appropriée
        }
    }

    public String getSuccessMessage() {
        return successMessage;
    }

    public void setSuccessMessage(String successMessage) {
        this.successMessage = successMessage;
    }
}
