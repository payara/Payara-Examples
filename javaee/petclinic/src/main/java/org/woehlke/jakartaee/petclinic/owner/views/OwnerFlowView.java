package org.woehlke.jakartaee.petclinic.owner.views;

import org.woehlke.jakartaee.petclinic.application.framework.views.CrudFlowView;

import java.io.Serializable;

/**
 *
 */
public interface OwnerFlowView extends CrudFlowView {

    long serialVersionUID = 6654391340545426308L;

    boolean isFlowStateNewPet();

    void setFlowStateNewPet();

    boolean isFlowStateEditPet();

    void setFlowStateEditPet();

    boolean isFlowStateNewVisit();

    void setFlowStateNewVisit();

    boolean isRenderPanelDetailsOwner();

    boolean isRenderPanelAddNewOwner();

    boolean isRenderPanelEditOwner();

    boolean isRenderPanelOwner();

    boolean isRenderPanelOwnerList();

    boolean isRenderPanelEditOwnersPet();

    boolean isRenderPanelAddNewOwnersPet();

    boolean isRenderPanelAddNewOwnersPetVisit();
}
