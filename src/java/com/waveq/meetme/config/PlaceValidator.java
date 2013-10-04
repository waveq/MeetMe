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
import com.waveq.meetme.entity.Place;
import java.util.List;

/**
 *
 * @author Szym
 */
public class PlaceValidator implements Validator {

    @Override
    public void validate(FacesContext ctx, UIComponent component, Object value){
        if(!(value instanceof String))
            throw new ValidatorException(new FacesMessage("Przekazana wartosc nie jest lancuchem znakow!"));
        String name = (String)value;
        EntityManager em = DBManager.getManager().createEntityManager();
        List<Place> list = em.createNamedQuery("Place.findByName").setParameter("name", name).getResultList();
        em.close();
        if(list.size()>0) 
            throw new ValidatorException(new FacesMessage("Nazwa miejsca, które chcesz dodać jest już zajęta!"));
    }
    
}
