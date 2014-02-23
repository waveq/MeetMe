package com.waveq.meetme.controllers;

import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import com.waveq.meetme.config.DBManager;
import com.waveq.meetme.entity.Sign;
import com.waveq.meetme.entity.Event;
import com.waveq.meetme.entity.User;
import javax.faces.event.ActionEvent;

public class SignBean {

    private Sign sign = new Sign();
    private Event event = new Event();
    private User user = new User();
    private int eventID;
    private int userID;
    private boolean signed;

    public List<Sign> getList() {
        EntityManager em = DBManager.getManager().createEntityManager();
        List list = em.createQuery("from Sign s WHERE s.event.id=:id")
                .setParameter("id", this.getEventID()).getResultList();
        em.close();
        return list;
    }

    public String signIn() {
        EntityManager em = DBManager.getManager().createEntityManager();

        this.event = em.find(Event.class, eventID);
        this.user = em.find(User.class, userID);

        this.sign.setUser(this.user);
        this.sign.setEvent(this.event);

        if (!isSigned()) {
            em.getTransaction().begin();
            sign.setId(null);
            em.persist(sign);
            em.getTransaction().commit();
            this.addGrowl("Sukces!", "Zapisano użytkownika!");
            em.close();
            this.sign = new Sign();
            return null;
        } else {

            em.getTransaction().begin();

            em.remove(em.merge(this.sign));
            this.sign = new Sign();
            em.getTransaction().commit();
            em.close();
            this.addGrowl("Sukces!", "Wypisano użytkownika!");
            return null;
        }
    }

    public boolean isSigned() {
        EntityManager em = DBManager.getManager().createEntityManager();
        List<Sign> signs = em.createQuery("from Sign s").getResultList();
        for (int i = 0; i < signs.size(); i++) {
            if (signs.get(i).getEvent().equals(this.event) 
                    && signs.get(i).getUser().equals(this.user)) {
                em.close();
                this.sign = signs.get(i);
                signed = true;
                return signed;
            }
        }
        em.close();
        signed = false;
        return signed;
    }
    
    public String delete() {
        EntityManager em = DBManager.getManager().createEntityManager();
        em.getTransaction().begin();
        this.sign = em.find(Sign.class, sign.getId());
        em.remove(this.sign);
        this.sign = new Sign();
        em.getTransaction().commit();
        em.close();
        this.addGrowl("Sukces!", "Wypisano użytkownika.");
        return null;
    }
    
    public void signListener(ActionEvent ae) {
        String ids = FacesContext.getCurrentInstance().getExternalContext()
                .getRequestParameterMap().get("signID").toString();
        int id = Integer.parseInt(ids);
        this.sign.setId(id);
    }

    public void addInformation(String s) {
        FacesContext.getCurrentInstance()
                .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, s, ""));
    }

    public void addGrowl(String title, String content) {
         FacesContext context = FacesContext.getCurrentInstance();  
          
        context.addMessage(null, new FacesMessage(title, content));  
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

    public Sign getSign() {
        return sign;
    }

    public void setSign(Sign sign) {
        this.sign = sign;
    }
}
