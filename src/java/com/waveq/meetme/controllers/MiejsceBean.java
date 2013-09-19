package com.waveq.meetme.controllers;


import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.persistence.EntityManager;
import com.waveq.meetme.config.DBManager;
import com.waveq.meetme.entity.Miejsce;

public class MiejsceBean {
    private Miejsce miejsce = new Miejsce();
    
    public Miejsce getMiejsce() {
        return miejsce;
    }

    public void setMiejsce(Miejsce miejsce) {
        this.miejsce = miejsce;
    }

    public List<Miejsce> getLista() {
        EntityManager em = DBManager.getManager().createEntityManager();
        List list = em.createNamedQuery("Miejsce.findAll").getResultList();
        em.close();
        return list;
    }

    public String dodaj() {
        EntityManager em = DBManager.getManager().createEntityManager();
        em.getTransaction().begin();
        miejsce.setId(null); 
        em.persist(miejsce);
        em.getTransaction().commit();
        this.dodajInformacje("Dodano miejsce!");
        em.close();
        this.miejsce = new Miejsce();
        return null;
    }


    public void miejsceListener(ActionEvent ae) {
        String ids = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("miejsceID").toString();
        int id = Integer.parseInt(ids);
        this.miejsce.setId(id);
    }


    public void dodajInformacje(String s) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, s,""));
    }
}
