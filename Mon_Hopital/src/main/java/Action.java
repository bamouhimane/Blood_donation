import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.primefaces.event.RowEditEvent;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

@Named
@RequestScoped
public class Action implements Serializable {
    private static final long serialVersionUID = 1L;

    private DonneurDaoImp don;
    private List<Donneur> dataList;
    private List<Donneur> donneurClone;
    private Donneur donneur;

    public Action() {
        try {
            don = new DonneurDaoImp();
            dataList = consulter();
            donneurClone = getClonedDonneur();
            donneur = new Donneur();
        } catch (Exception e) {
            // Gérez l'exception de manière appropriée (peut-être en journalisant ou en lançant une nouvelle exception)
            e.printStackTrace();
        }
    }

	public DonneurDaoImp getDon() {
		return don;
	}

	public void setDon(DonneurDaoImp don) {
		this.don = don;
	}

	public Donneur getDonneur() {
		return donneur;
	}

	public void setDonneur(Donneur donneur) {
		this.donneur = donneur;
	}

	public List<Donneur> getDonneurClone() {
		return donneurClone;
	}

	public void setDonneurClone(List<Donneur> donneurClone) {
		this.donneurClone = donneurClone;
	}


	
	public List<Donneur> getDataList() {
		return dataList;
	}

	public void setDataList(List<Donneur> dataList) {
		this.dataList = dataList;
	}


	
	
	public String ajouter() throws Exception {
		 
		if (donneur.getCIN().isEmpty()||donneur.getNom().isEmpty() || donneur.getPrenom().isEmpty() || donneur.getAge().isEmpty() || donneur.getTel().isEmpty() || donneur.getAdr().isEmpty() || donneur.getPoids().isEmpty() || donneur.getTaille().isEmpty() ) return "Error1.xhtml";
		else {
			
		    try {
		        if (don.donneurExists(donneur)) {
		            System.out.println("Le donneur existe déjà dans la table.");
		            return "ErrorDon.xhtml";
		        }
		    } catch (SQLException e1) {
		        System.out.println("erreur de la fct donexits");

		        e1.printStackTrace();
		    }
		don.add(donneur);
		System.out.println("Donneur ajouté");
		return "Success.xhtml";
		
		
		}
		
		
		
	}
	
	
	public List<Donneur> consulter() {
		return don.getAll();
		
		
	}
	
	 public void onRowEdit(RowEditEvent<?> event) {
		 	System.out.println("Updated data: " + event.getObject());
		 	
		    // Vous pouvez directement utiliser event.getObject() pour accéder aux nouvelles valeurs
		    don.update((Donneur) event.getObject());
		 	
		 	System.out.println("hi");

	        FacesMessage msg = new FacesMessage("Donneur Edited", ((Donneur) event.getObject()).getCIN());
	        FacesContext.getCurrentInstance().addMessage(null, msg);
	    }

	    public void onRowCancel(RowEditEvent<?> event) {
	        FacesMessage msg = new FacesMessage("Edit Cancelled", ((Donneur) event.getObject()).getCIN());
	        FacesContext.getCurrentInstance().addMessage(null, msg);
	    }

	   
	      public List<Donneur> getClonedDonneur() throws Exception {
	  		
	        List<Donneur> results = new ArrayList<>();
	        List<Donneur> originals = don.getChwiya();
	        for (Donneur original : originals) {
	            results.add(original.clone());
	        }
	        

	        return results;
	    }
		
	

}
