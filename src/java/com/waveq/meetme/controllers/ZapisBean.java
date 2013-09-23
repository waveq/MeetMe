package com.waveq.meetme.controllers;

import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import com.waveq.meetme.config.DBManager;
import com.waveq.meetme.entity.Zapis;
import com.waveq.meetme.entity.Event;
import com.waveq.meetme.entity.User;
import javax.faces.event.ActionEvent;

public class ZapisBean {

    private Zapis zapis = new Zapis();
    private Event event = new Event();
    private User user = new User();
    private int eventID;
    private int userID;
    private boolean signed;

    public List<Zapis> getLista() {
        EntityManager em = DBManager.getManager().createEntityManager();
        List list = em.createQuery("from Zapis z WHERE z.event.id=:id").setParameter("id", this.getEventID()).getResultList();
        em.close();
        return list;
    }

    public String zapisz() {
        EntityManager em = DBManager.getManager().createEntityManager();

        this.event = em.find(Event.class, eventID);
        this.user = em.find(User.class, userID);

        this.zapis.setUser(this.user);
        this.zapis.setEvent(this.event);

        if (!isSigned()) {
            em.getTransaction().begin();
            zapis.setId(null);
            em.persist(zapis);
            em.getTransaction().commit();
            this.dodajInformacje("Zapisano");
            em.close();
            this.zapis = new Zapis();
            return null;
        } else {

            em.getTransaction().begin();

            em.remove(em.merge(this.zapis));
            this.zapis = new Zapis();
            em.getTransaction().commit();
            em.close();
            this.dodajInformacje("Wypisano");
            return null;
        }
    }

    public boolean isSigned() {
        EntityManager em = DBManager.getManager().createEntityManager();
        List<Zapis> signs = em.createQuery("from Zapis z").getResultList();

        for (int i = 0; i < signs.size(); i++) {
            if (signs.get(i).getEvent().equals(this.event) && signs.get(i).getUser().equals(this.user)) {
                em.close();
                this.zapis = signs.get(i);
                signed = true;
                return signed;
            }
        }
        em.close();
        signed = false;
        return signed;
    }

    public void dodajInformacje(String s) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, s, ""));
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

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
}
