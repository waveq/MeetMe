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
import com.waveq.meetme.entity.Miejsce;
import java.util.List;

/**
 *
 * @author Szym
 */
public class MiejsceValidator implements Validator {

    @Override
    public void validate(FacesContext ctx, UIComponent component, Object value){
        if(!(value instanceof String))
            throw new ValidatorException(new FacesMessage("Przekazana wartosc nie jest lancuchem znakow!"));
        String nazwa = (String)value;
        EntityManager em = DBManager.getManager().createEntityManager();
        List<Miejsce> list = em.createNamedQuery("Miejsce.findByNazwa").setParameter("nazwa", nazwa).getResultList();
        em.close();
        if(list.size()>0) 
            throw new ValidatorException(new FacesMessage("Nazwa miejsca, które chcesz dodać jest już zajęta!"));
    }
    
}
