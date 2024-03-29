package org.woehlke.jakartaee.petclinic.pettype.views;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.ejb.Stateless;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import lombok.extern.java.Log;
import org.woehlke.jakartaee.petclinic.pettype.PetType;

import java.io.Serializable;

/**
 *
 */
@Log
@FacesConverter(
        value = "petTypeConverter"
)
@Stateless
public class PetTypeConverter implements Converter<PetType>, Serializable {

    private static final long serialVersionUID = 4908876595996046904L;

    @Override
    public PetType getAsObject(FacesContext context, UIComponent component, String name) {
        PetType petType = new PetType(name);
        log.info("PetTypeConverter.getAsObject: from = " + name + " to " + petType.toString());
        return petType;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, PetType petType) {
        String name = petType.getName();
        log.info("PetTypeConverter.getAsObject: from = " + petType.toString() + " to " + name);
        return name;
    }


    @PostConstruct
    public void postConstruct() {
        log.info("postConstruct: "+PetTypeConverter.class.getSimpleName());
    }

    @PreDestroy
    public void preDestroy() {
        log.info("preDestroy: "+PetTypeConverter.class.getSimpleName());
    }

}
