package com.waveq.meetme.controllers;

import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.persistence.EntityManager;
import com.waveq.meetme.config.DBManager;
import com.waveq.meetme.entity.Zapis;
import com.waveq.meetme.entity.Event;
import com.waveq.meetme.entity.User;


public class ZapisBean {
    private Zapis zapis = new Zapis();
    
    private Event event = new Event();
    private User user = new User();

    private int eventID;
    private int userID;
    
    public int getEventID() {
        return eventID;
    }
    
    public void setEventID(int eventID) {
        this.eventID = eventID;
    }
   
    public Zapis getZapis() {
        return zapis;
    }

    public void setZapis(Zapis zapis) {
        this.zapis = zapis;
    }

    public List<Zapis> getLista() {
    //    String idEventS = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("eventID").toString();
    //    int idEvent = Integer.parseInt(idEventS);
  
        EntityManager em = DBManager.getManager().createEntityManager();
        List list = em.createQuery("from Zapis z WHERE z.event.id=:id").setParameter("id", this.getEventID()).getResultList();
        em.close();
        return list;
    }

    public String zapisz() {
        this.zapis.setUser(this.user);
        this.zapis.setEvent(this.event);
        
        EntityManager em = DBManager.getManager().createEntityManager();
        List<Zapis> signs = em.createQuery("from Zapis z").getResultList();
        
        for(int i=0; i<signs.size();i++) { 
            if(signs.get(i).getEvent().equals(this.event) && signs.get(i).getUser().equals(this.user)) {                      
                em.close();
                this.dodajInformacje("Nie możesz zapisać się dwa razy na to samo wydarzenie!");
                return null;
            }
        }
 
            em.getTransaction().begin();
            zapis.setId(null); 
            em.persist(zapis);
            em.getTransaction().commit();
            this.dodajInformacje("Zapisano!");
            em.close();
            this.zapis = new Zapis();               
            return null;
    }
   
    
    public void dodajInformacje(String s) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, s,""));
    }



    public void zapisListener(ActionEvent ae) {
        String idEventS = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("eventID").toString();
        int idEvent = Integer.parseInt(idEventS);
        this.event.setId(idEvent);
        
        String idUserS = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("userID").toString();
        int idUser = Integer.parseInt(idUserS);
        this.user.setId(idUser);
        
        EntityManager em = DBManager.getManager().createEntityManager();
        this.event = em.find(Event.class, event.getId());
        this.user = em.find(User.class, user.getId());
        em.close();
    }


    
 
}
