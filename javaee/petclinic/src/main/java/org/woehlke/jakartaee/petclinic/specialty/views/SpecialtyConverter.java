package org.woehlke.jakartaee.petclinic.specialty.views;


import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.ejb.Stateless;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import lombok.extern.java.Log;
import org.woehlke.jakartaee.petclinic.specialty.Specialty;

import java.io.Serializable;


/**
 * Created with IntelliJ IDEA.
 * User: tw
 * Date: 04.01.14
 * Time: 12:44
 * To change this template use File | Settings | File Templates.
 */
@Log
@FacesConverter(
        value = "specialtyConverter"
)
@Stateless
public class SpecialtyConverter implements Converter<Specialty>, Serializable {

    private static final long serialVersionUID = 3816519727799645701L;

    @Override
    public Specialty getAsObject(FacesContext context, UIComponent component, String name) {
        Specialty specialty = new Specialty(name);
        log.info("SpecialtyConverter.getAsObject: from = " + name + " to " + specialty.toString());
        return specialty;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Specialty specialty) {
        String name = specialty.getName();
        log.info("SpecialtyConverter.getAsString: from " + specialty.toString() + " to " + name);
        return name;
    }

    @PostConstruct
    public void postConstruct() {
        log.info("postConstruct: "+SpecialtyConverter.class.getSimpleName());
    }

    @PreDestroy
    public void preDestroy() {
        log.info("preDestroy: "+SpecialtyConverter.class.getSimpleName());
    }

}
