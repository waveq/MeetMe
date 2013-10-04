package com.waveq.meetme.config;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.persistence.EntityManager;
import com.waveq.meetme.entity.Place;


public class PlaceConverter implements Converter {
    @Override
    public String getAsString(FacesContext ctx, UIComponent c, Object o) {
        if (! (o instanceof Place))
            throw new ConverterException(new FacesMessage("Nie udalo sie dokonac konwersji!"));
        Place p = (Place)o;
        return p.getId().toString();

    }
    @Override
    public Object getAsObject(FacesContext ctx, UIComponent c, String s) {
        Integer i = Integer.valueOf(s);
        EntityManager em = DBManager.getManager().createEntityManager();
        Place p = em.find(Place.class, i);
        em.close();
        return p;
    }
}
