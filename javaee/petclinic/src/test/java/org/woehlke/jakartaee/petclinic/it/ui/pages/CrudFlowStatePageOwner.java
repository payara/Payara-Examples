package org.woehlke.jakartaee.petclinic.it.ui.pages;

public interface CrudFlowStatePageOwner extends CrudFlowStatePage {

    boolean isFlowStateNewPet();

    boolean isFlowStateEditPet();

    boolean isFlowStateNewVisit();
}
