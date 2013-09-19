package com.waveq.meetme.config;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.persistence.EntityManager;
import com.waveq.meetme.entity.Miejsce;


public class MiejsceConverter implements Converter {
    @Override
    public String getAsString(FacesContext ctx, UIComponent c, Object o) {
        if (! (o instanceof Miejsce))
            throw new ConverterException(new FacesMessage("Nie udalo sie dokonac konwersji!"));
        Miejsce p = (Miejsce)o;
        return p.getId().toString();

    }
    @Override
    public Object getAsObject(FacesContext ctx, UIComponent c, String s) {
        Integer i = Integer.valueOf(s);
        EntityManager em = DBManager.getManager().createEntityManager();
        Miejsce p = em.find(Miejsce.class, i);
        em.close();
        return p;
    }
}
