package org.woehlke.jakartaee.petclinic.owner.views;

import lombok.extern.java.Log;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;

import java.io.Serializable;

/**
 *
 */
@Log
@Named("ownerViewFlow")
@SessionScoped
public class OwnerFlowViewImpl implements OwnerFlowView, Serializable {

    private static final long serialVersionUID = 4530858836742945751L;

    private OwnerFlowState flowState;

    public OwnerFlowState getFlowState() {
        if (this.flowState == null) {
            this.flowState = OwnerFlowState.LIST;
        }
        return this.flowState;
    }

    @Override
    public boolean isFlowStateList() {
        return this.getFlowState() == OwnerFlowState.LIST;
    }

    @Override
    public boolean isFlowStateDetails() {
        return this.getFlowState() == OwnerFlowState.DETAILS;
    }

    @Override
    public boolean isFlowStateNew() {
        return this.getFlowState() == OwnerFlowState.NEW_OWNER;
    }

    @Override
    public boolean isFlowStateEdit() {
        return this.getFlowState() == OwnerFlowState.EDIT_OWNER;
    }

    @Override
    public boolean isFlowStateDelete() {
        return this.getFlowState() == OwnerFlowState.DELETE_OWNER;
    }

    @Override
    public boolean isFlowStateSearchResult() {
        return this.getFlowState() == OwnerFlowState.LIST_SEARCH_RESULT;
    }

    public void logFlowState(){
        String msg = "flowState: " + this.flowState.name();
        log.info(msg);
    }

    @Override
    public boolean isFlowStateNewPet() {
        return this.getFlowState() == OwnerFlowState.NEW_PET;
    }

    @Override
    public boolean isFlowStateEditPet() {
        return this.getFlowState() == OwnerFlowState.EDIT_PET;
    }

    @Override
    public boolean isFlowStateNewVisit() {
        return this.getFlowState() == OwnerFlowState.NEW_VISIT;
    }

    @Override
    public void setFlowStateNewVisit() {
        this.flowState = OwnerFlowState.NEW_VISIT;
        logFlowState();
    }

    @Override
    public void setFlowStateNewPet() {
        this.flowState = OwnerFlowState.NEW_PET;
        logFlowState();
    }

    @Override
    public void setFlowStateEditPet() {
        this.flowState = OwnerFlowState.EDIT_PET;
        logFlowState();
    }

    @Override
    public void setFlowStateList() {
        this.flowState = OwnerFlowState.LIST;
        logFlowState();
    }

    @Override
    public void setFlowStateDetails() {
        this.flowState = OwnerFlowState.DETAILS;
        logFlowState();
    }

    @Override
    public void setFlowStateNew() {
        this.flowState = OwnerFlowState.NEW_OWNER;
        logFlowState();
    }

    @Override
    public void setFlowStateEdit() {
        this.flowState = OwnerFlowState.EDIT_OWNER;
        logFlowState();
    }

    @Override
    public void setFlowStateDelete() {
        this.flowState = OwnerFlowState.DELETE_OWNER;
        logFlowState();
    }

    @Override
    public void setFlowStateSearchResult() {
        this.flowState = OwnerFlowState.LIST_SEARCH_RESULT;
        logFlowState();
    }

    public boolean isRenderPanelDetailsOwner() {
        return this.isFlowStateDetails();
    }

    public boolean isRenderPanelAddNewOwner() {
        return this.isFlowStateNew();
    }

    public boolean isRenderPanelEditOwner() {
        return this.isFlowStateEdit();
    }

    public boolean isRenderPanelOwner() {
        return false;
    }

    public boolean isRenderPanelOwnerList() {
        return true;
    }

    public boolean isRenderPanelEditOwnersPet() {
        return this.isFlowStateEditPet();
    }

    public boolean isRenderPanelAddNewOwnersPet() {
        return this.isFlowStateNewPet();
    }

    public boolean isRenderPanelAddNewOwnersPetVisit() {
        return this.isFlowStateNewVisit();
    }
}
