package org.woehlke.jakartaee.petclinic.application.conf;

import jakarta.faces.context.FacesContext;

import java.io.Serializable;
import java.util.ResourceBundle;

/**
 *
 */
public class MessageProvider implements Serializable {

    private static final long serialVersionUID = 3363265300512735983L;

    public ResourceBundle getBundle() {
        FacesContext context = FacesContext.getCurrentInstance();
        return context.getApplication().getResourceBundle(context, "msg");
    }

}
