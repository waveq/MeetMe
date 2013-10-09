package com.waveq.meetme.controllers;

import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.persistence.EntityManager;
import com.waveq.meetme.config.DBManager;
import com.waveq.meetme.entity.Place;
import javax.persistence.RollbackException;

public class PlaceBean {

    private Place place = new Place();

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public List<Place> getList() {
        EntityManager em = DBManager.getManager().createEntityManager();
        List list = em.createNamedQuery("Place.findAll").getResultList();
        em.close();
        return list;
    }

    public String create() {
        EntityManager em = DBManager.getManager().createEntityManager();
        em.getTransaction().begin();
        place.setId(null);
        em.persist(place);
        em.getTransaction().commit();
        this.addInformation("Dodano miejsce");
        em.close();
        this.place = new Place();
        return null;
    }
      
      public String delete() {
        try{
            EntityManager em = DBManager.getManager().createEntityManager();
            em.getTransaction().begin();
            this.place = em.find(Place.class, place.getId());
            em.remove(this.place);
            this.place = new Place();
            em.getTransaction().commit();
            em.close();
            this.addInformation("Usunieto miejsce");
        }catch(RollbackException r) {
            FacesMessage msg = new FacesMessage("Miejsce, które chcesz usunąć prawdopodobnie jest wykorzystywane w jednym z wydarzeń. "
                    + "Spróbuj najpierw usunąć to wydarzenie.", "");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        return null;
    }

    public void placeListener(ActionEvent ae) {
        String ids = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("placeID").toString();
        int id = Integer.parseInt(ids);
        this.place.setId(id);
    }
    
    public String loadToUpdate() {
        EntityManager em = DBManager.getManager().createEntityManager();
        this.place = em.find(Place.class, place.getId());
        em.close();
        return "updateplace";
    }
    
     public String update() {
        EntityManager em = DBManager.getManager().createEntityManager();
        em.getTransaction().begin();
        em.merge(this.place);
        em.getTransaction().commit();
        em.close();
        this.addInformation("Zmieniono dane miejsca");
        this.place = new Place();
        return "showplaces";
     }

    public void addInformation(String s) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, s, ""));
    }
}
