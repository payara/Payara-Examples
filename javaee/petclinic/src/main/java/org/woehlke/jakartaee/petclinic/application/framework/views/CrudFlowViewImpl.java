package org.woehlke.jakartaee.petclinic.application.framework.views;

import lombok.Getter;
import lombok.extern.java.Log;

import java.io.Serializable;

/**
 *
 */
@Log
public abstract class CrudFlowViewImpl implements CrudFlowView, Serializable {

    private static final long serialVersionUID = -143286438L;

    private CrudFlowState flowState;

    public CrudFlowState getFlowState() {
        if (this.flowState == null) {
            this.flowState = CrudFlowState.LIST;
        }
        return flowState;
    }

    @Override
    public boolean isFlowStateList() {
        return CrudFlowState.LIST == this.getFlowState();
    }

    @Override
    public boolean isFlowStateDetails(){
        return CrudFlowState.DETAILS  == this.getFlowState();
    }

    @Override
    public boolean isFlowStateNew() {
        return CrudFlowState.NEW == this.getFlowState();
    }

    @Override
    public boolean isFlowStateEdit() {
        return CrudFlowState.EDIT == this.getFlowState();
    }

    @Override
    public boolean isFlowStateDelete() {
        return CrudFlowState.DELETE == this.getFlowState();
    }

    @Override
    public boolean isFlowStateSearchResult() {
        return CrudFlowState.LIST_SEARCH_RESULT == this.getFlowState();
    }


    public void logFlowState(){
        String msg = "flowState: " + this.flowState.name();
        log.info(msg);
    }

    @Override
    public void setFlowStateList() {
        this.flowState = CrudFlowState.LIST;
    }

    @Override
    public void setFlowStateDetails() {
        this.flowState = CrudFlowState.DETAILS;
    }

    @Override
    public void setFlowStateNew() {
        this.flowState = CrudFlowState.NEW;
    }

    @Override
    public void setFlowStateEdit() {
        this.flowState = CrudFlowState.EDIT;
    }

    @Override
    public void setFlowStateDelete() {
        this.flowState = CrudFlowState.DELETE;
    }

    @Override
    public void setFlowStateSearchResult() {
        this.flowState = CrudFlowState.LIST_SEARCH_RESULT;
    }
}
