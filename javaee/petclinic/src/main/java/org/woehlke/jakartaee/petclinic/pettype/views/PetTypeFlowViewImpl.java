package org.woehlke.jakartaee.petclinic.pettype.views;


import org.woehlke.jakartaee.petclinic.application.framework.views.CrudFlowViewImpl;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;

import java.io.Serializable;

/**
 *
 */
@Named("petTypeViewFlow")
@SessionScoped
public class PetTypeFlowViewImpl extends CrudFlowViewImpl implements PetTypeFlowView, Serializable {
    private static final long serialVersionUID = 8397968607819895147L;
}
