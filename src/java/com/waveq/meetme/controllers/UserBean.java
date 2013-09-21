package com.waveq.meetme.controllers;


import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.persistence.EntityManager;
import com.waveq.meetme.config.DBManager;
import com.waveq.meetme.entity.User;


public class UserBean {
    private User user = new User();
    
    private NavigationBean navigationBean;
    
    private boolean loggedIn;
    
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<User> getLista() {
        EntityManager em = DBManager.getManager().createEntityManager();
        List list = em.createNamedQuery("User.findAll").getResultList();
        em.close();
        
        return list;
    }

    public String dodaj() {
        EntityManager em = DBManager.getManager().createEntityManager();
        user.setLogin(user.getLogin().toLowerCase());        
        em.getTransaction().begin();
        user.setId(null); 
        em.persist(user);
        em.getTransaction().commit();
        this.dodajInformacje("Dodano użytkownika!");
        em.close();
        this.user = new User();               
        return navigationBean.redirectToIndex();
    }


    public void userListener(ActionEvent ae) {
        String ids = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("userID").toString();
        int id = Integer.parseInt(ids);
        this.user.setId(id);
    }


    public void dodajInformacje(String s) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, s,""));
    }

    
    public String doLogin() {

        EntityManager em = DBManager.getManager().createEntityManager();
        List<User> users = em.createQuery("from User u").getResultList();
        List<String> logins = em.createQuery("SELECT u.login from User u").getResultList();
        List<String> passwords = em.createQuery("SELECT u.haslo from User u").getResultList();
        
        // Set input login to lower case and delete whitespaces
        user.setLogin(user.getLogin().toLowerCase().replaceAll("\\s",""));
        
        for(int i=0; i<users.size();i++) {
            if (users.get(i).getLogin().equals(user.getLogin()) && users.get(i).getHaslo().equals(user.getHaslo())) {
                
               this.user.setId(users.get(i).getId());
               this.user = em.find(User.class, user.getId());
               em.close();
               loggedIn = true;
               return "login-success";
            }
        }
         
        // Set login ERROR
        em.close();
        FacesMessage msg = new FacesMessage("Błąd logowania!", "ERROR MSG");
        msg.setSeverity(FacesMessage.SEVERITY_ERROR);
        FacesContext.getCurrentInstance().addMessage(null, msg);
         
        // To to index page
        return "login-fail";
         
    }  
    
     public String doLogout() {
        // Set the paremeter indicating that user is logged in to false
        loggedIn = false;
         
        // Set logout message
        this.dodajInformacje("Wylogowano!");      
        return "logout-success";
    }
     
       public boolean isLoggedIn() {
        return loggedIn;
    }
 
    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }
 
    public void setNavigationBean(NavigationBean navigationBean) {
        this.navigationBean = navigationBean;
    }
}
