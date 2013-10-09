/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.waveq.meetme.config;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.persistence.EntityManager;
import com.waveq.meetme.entity.User;
import java.util.List;

/**
 *
 * @author Szym
 */
public class LoginValidator implements Validator {

    @Override
    public void validate(FacesContext ctx, UIComponent component, Object value){
        if(!(value instanceof String))
            throw new ValidatorException(new FacesMessage("Przekazana wartosc nie jest lancuchem znakow!", ""));
        String login = ((String)value).toLowerCase();
        if(login.contains(" "))
            throw new ValidatorException(new FacesMessage("Login nie może zawierać spacji!", ""));
        
        EntityManager em = DBManager.getManager().createEntityManager();
        List<User> list = em.createNamedQuery("User.findByLogin").setParameter("login", login).getResultList();
        em.close();
        if(list.size()>0) 
            throw new ValidatorException(new FacesMessage("Login jest już zajęty.", ""));
    }
    
}
