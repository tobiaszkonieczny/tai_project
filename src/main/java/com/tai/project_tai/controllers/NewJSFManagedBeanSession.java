package com.tai.project_tai.controllers;

import com.tai.project_tai.dao.TUzytkownikJpaController;
import com.tai.project_tai.entity.TUzytkownik;
import com.tai.project_tai.to.UserTO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections; // Dodano do sortowania
import java.util.Comparator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.EntityManager;
import javax.transaction.UserTransaction;

@Named(value = "newJSFManagedBeanSession")
@SessionScoped
public class NewJSFManagedBeanSession implements Serializable {
    

    @PersistenceContext(unitName="project_tai")
    private EntityManager entityManager;
    
    @Resource
    private UserTransaction userTransaction;
    
    private List<UserTO> userToList = new ArrayList<>();
    
    public NewJSFManagedBeanSession() {
    }
 
    @PostConstruct
    public void refreshData() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("project_tai");
        TUzytkownikJpaController daneDao = new TUzytkownikJpaController(userTransaction, emf);
        
        List<TUzytkownik> uzytkownikToListLokal = daneDao.findTUzytkownikEntities();
        if (uzytkownikToListLokal != null) {
            userToList.clear();
            for (TUzytkownik uzytkownik: uzytkownikToListLokal) {
                userToList.add(new UserTO(uzytkownik.getId(), uzytkownik.getImie(), uzytkownik.getNazwisko(), false));
            }
        }
    }
    
    public List<UserTO> getUserToList() {
        return userToList;
    }

    public void setUserToList(List<UserTO> userToList) {
        this.userToList = userToList;
    }
    
    public void visibleChange(UserTO userTO) {
        if (!userTO.isEdited()) {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("project_tai");
            TUzytkownikJpaController daneDao = new TUzytkownikJpaController(userTransaction, emf);
            try{
                TUzytkownik encja = new TUzytkownik(userTO.getId(), userTO.getImie(), userTO.getNazwisko());
                daneDao.edit(encja);
                
                int index = userToList.indexOf(userTO);
                if(index != -1) userToList.set(index, userTO);
                
            } catch(Exception ex) {
                sendJSFErrorMesssage(ex);
            }
        }
    }
    
    public void deleteRow (UserTO userTO) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("project_tai");
        TUzytkownikJpaController daneDao = new TUzytkownikJpaController(userTransaction, emf);
        try{
            daneDao.destroy(userTO.getId());
            userToList.remove(userTO);
        } catch(Exception ex) {
            sendJSFErrorMesssage(ex);
        }
    }
    
    public void addRow (UserTO userTO) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("project_tai");
        TUzytkownikJpaController daneDao = new TUzytkownikJpaController(userTransaction, emf);
        
        Long id = System.currentTimeMillis();
        TUzytkownik uzytkownik = new TUzytkownik(id, "", ""); 
        
        try{
            daneDao.create(uzytkownik);
            
            int index = userToList.indexOf(userTO);
            UserTO noweTO = new UserTO(id, "Nowe", "Imie", true); 
            
            if(index != -1 && index < userToList.size()) {
                userToList.add(index + 1, noweTO);
            } else {
                userToList.add(noweTO);
            }
            
        } catch(Exception ex) {
            sendJSFErrorMesssage(ex);
        }
    }
    
    public void sortAsc() {        
        Collections.sort(userToList, new Comparator<UserTO>() {
            @Override
            public int compare(UserTO a, UserTO b) {
                String valA = a.getNazwisko() != null ? a.getNazwisko() : "";
                String valB = b.getNazwisko() != null ? b.getNazwisko() : "";
                return valA.compareToIgnoreCase(valB);
            }
        });
    }

    public void sortDesc() {        
        Collections.sort(userToList, new Comparator<UserTO>() {
            @Override
            public int compare(UserTO a, UserTO b) {
                String valA = a.getNazwisko() != null ? a.getNazwisko() : "";
                String valB = b.getNazwisko() != null ? b.getNazwisko() : "";
                return valB.compareToIgnoreCase(valA);
            }
        });
    }
    
    public void sendJSFErrorMesssage(Exception ex) {
        javax.faces.context.FacesContext facesContext = javax.faces.context.FacesContext.getCurrentInstance();
        if (facesContext != null) {
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Błąd", ex.getMessage()));
        }
        ex.printStackTrace(); 
    }
}