package org.woehlke.jakartaee.petclinic.application.framework.views;


/**
 *
 */
public interface CrudFlowView {

    //long serialVersionUID = -2732706731385890693L;

    boolean isFlowStateList();

    boolean isFlowStateDetails();

    boolean isFlowStateNew();

    boolean isFlowStateEdit();

    boolean isFlowStateDelete();

    boolean isFlowStateSearchResult();

    void logFlowState();

    void setFlowStateList();

    void setFlowStateDetails();

    void setFlowStateNew();

    void setFlowStateEdit();

    void setFlowStateDelete();

    void setFlowStateSearchResult();
}
