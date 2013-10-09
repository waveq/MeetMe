package com.waveq.meetme.controllers;

import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.persistence.EntityManager;
import com.waveq.meetme.config.DBManager;
import com.waveq.meetme.entity.Event;

public class EventBean {

    private Event event = new Event();
    
    public List<Event> getList() {
        EntityManager em = DBManager.getManager().createEntityManager();
        List list = em.createNamedQuery("Event.findAll").getResultList();
        em.close();
        return list;
    }

    public String create() {
        EntityManager em = DBManager.getManager().createEntityManager();
        em.getTransaction().begin();
        event.setId(null);
        em.persist(event);
        em.getTransaction().commit();
        this.addInformation("Dodano wydarzenie");
        em.close();
        this.event = new Event();
        return null;
    }
    
    public String delete() {
        EntityManager em = DBManager.getManager().createEntityManager();
        em.getTransaction().begin();
        this.event = em.find(Event.class, event.getId());
        em.remove(this.event);
        this.event = new Event();
        em.getTransaction().commit();
        em.close();
        this.addInformation("Usunieto wydarzenie");
        return null;
    }

   

    public void eventListener(ActionEvent ae) {
        String ids = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("eventID").toString();
        int id = Integer.parseInt(ids);
        this.event.setId(id);
    }

    public void addInformation(String s) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, s, ""));
    }
    
     public String update() {
        EntityManager em = DBManager.getManager().createEntityManager();
        em.getTransaction().begin();
        em.merge(this.event);
        em.getTransaction().commit();
        em.close();
        this.addInformation("Zmieniono dane wydarzenia");
        this.event = new Event();
        return "showevents";
     }
     
      public String loadSigns() {
        EntityManager em = DBManager.getManager().createEntityManager();
        this.event = em.find(Event.class, event.getId());
        em.close();
        return "tosigns";
    }
        
     public String loadToUpdate() {
        EntityManager em = DBManager.getManager().createEntityManager();
        this.event = em.find(Event.class, event.getId());
        em.close();
        return "updateevent";
    }
     
    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
}
