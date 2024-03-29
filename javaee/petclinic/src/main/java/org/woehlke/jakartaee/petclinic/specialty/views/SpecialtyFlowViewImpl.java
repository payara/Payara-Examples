package org.woehlke.jakartaee.petclinic.specialty.views;

import org.woehlke.jakartaee.petclinic.application.framework.views.CrudFlowViewImpl;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;

import java.io.Serializable;


/**
 *
 */
@Named("specialtyViewFlow")
@SessionScoped
public class SpecialtyFlowViewImpl extends CrudFlowViewImpl implements SpecialtyFlowView , Serializable {
    private static final long serialVersionUID = -621000271002202654L;
}
