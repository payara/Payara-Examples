package org.woehlke.jakartaee.petclinic.pettype;


import jakarta.ejb.EJB;
import jakarta.ejb.EJBException;
import jakarta.ejb.EJBTransactionRolledbackException;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import org.woehlke.jakartaee.petclinic.application.conf.PetclinicApplication;
import org.woehlke.jakartaee.petclinic.application.views.FlashMessagesView;
import org.woehlke.jakartaee.petclinic.application.views.LanguageView;
import org.woehlke.jakartaee.petclinic.pettype.db.PetTypeService;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.woehlke.jakartaee.petclinic.pettype.views.PetTypeFlowViewImpl;

import java.io.Serializable;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Fert
 * Date: 06.01.14
 * Time: 11:49
 * To change this template use File | Settings | File Templates.
 */
@Log
@Getter
@Setter
@Named("petTypeView")
@SessionScoped
public class PetTypeViewImpl implements PetTypeView, Serializable {

    private static final long serialVersionUID = -528406859430949031L;

    @Inject
    private PetclinicApplication petclinicApplication;

    @Inject
    private LanguageView languageView;

    @Inject
    private FlashMessagesView flashMessagesView;

    @Inject
    private PetTypeFlowViewImpl petTypeViewFlow;

    @EJB
    private PetTypeService entityService;

    private PetType entity;
    private List<PetType> list;
    private String searchterm;


    @Override
    public String showDetailsForm(PetType o) {
        log.info("---------------------------------------------------------------------------");
        log.info("showDetailsForm");;
        log.info("---------------------------------------------------------------------------");
        if (o != null) {
            this.entity = entityService.findById(o.getId());
            this.petTypeViewFlow.setFlowStateDetails();
        } else {
            this.petTypeViewFlow.setFlowStateList();
        }
        return JSF_PAGE;
    }

    @Override
    public  String cancelDetails(){
        log.info("---------------------------------------------------------------------------");
        log.info("cancelDetails");
        log.info("---------------------------------------------------------------------------");
        this.petTypeViewFlow.setFlowStateList();
        return JSF_PAGE;
    }

    @Override
    public String showNewForm() {
        log.info("---------------------------------------------------------------------------");
        log.info("showNewForm");
        log.info("---------------------------------------------------------------------------");
        this.newEntity();
        this.petTypeViewFlow.setFlowStateNew();
        return JSF_PAGE;
    }

    @Override
    public String cancelNew() {
        log.info("---------------------------------------------------------------------------");
        log.info("cancelNew");
        log.info("---------------------------------------------------------------------------");
        this.petTypeViewFlow.setFlowStateList();
        return JSF_PAGE;
    }

    @Override
    public String saveNew() {
        log.info("---------------------------------------------------------------------------");
        log.info("saveNew");
        log.info("---------------------------------------------------------------------------");
        this.saveNewEntity();
        this.petTypeViewFlow.setFlowStateDetails();
        return JSF_PAGE;
    }

    @Override
    public String showEditForm() {
        log.info("---------------------------------------------------------------------------");
        log.info("showEditForm");
        log.info("---------------------------------------------------------------------------");
        if (this.entity != null) {
            this.petTypeViewFlow.setFlowStateEdit();
        } else {
            this.petTypeViewFlow.setFlowStateList();
        }
        return JSF_PAGE;
    }

    @Override
    public String cancelEdited() {
        log.info("---------------------------------------------------------------------------");
        log.info("cancelEdited");
        log.info("---------------------------------------------------------------------------");
        this.petTypeViewFlow.setFlowStateDetails();
        return JSF_PAGE;
    }

    @Override
    public String saveEdited() {
        log.info("---------------------------------------------------------------------------");
        log.info("saveEdited");
        log.info("---------------------------------------------------------------------------");
        this.saveEditedEntity();
        this.petTypeViewFlow.setFlowStateDetails();
        return JSF_PAGE;
    }

    @Override
    public String showDeleteForm() {
        log.info("---------------------------------------------------------------------------");
        log.info("showDeleteForm");
        log.info("---------------------------------------------------------------------------");
        if (this.entity != null) {
            this.petTypeViewFlow.setFlowStateDelete();
        } else {
            this.petTypeViewFlow.setFlowStateList();
        }
        return JSF_PAGE;
    }

    @Override
    public String cancelDelete() {
        log.info("---------------------------------------------------------------------------");
        log.info("cancelDelete");
        log.info("---------------------------------------------------------------------------");
        this.petTypeViewFlow.setFlowStateDetails();
        return JSF_PAGE;
    }

    @Override
    public String performDelete() {
        log.info("performDelete");
        this.deleteSelectedEntity();
        this.petTypeViewFlow.setFlowStateList();
        return JSF_PAGE;
    }

    @Override
    public String search() {
        this.performSearch();
        return JSF_PAGE;
    }

    @Override
    public String clearSearchterm(){
        log.info("clearSearchterm");
        this.searchterm = null;
        return JSF_PAGE;
    }

    @Override
    public PetType getEntity() {
        if (entity == null) {
            newEntity();
        }
        return entity;
    }

    @Override
    public void newEntity() {
        log.info("---------------------------------------------------------------------------");
        log.info("newEntity");
        log.info("---------------------------------------------------------------------------");
            this.entity = PetType.newEntity();
    }

    @Override
    public void saveNewEntity() {
        log.info("---------------------------------------------------------------------------");
        log.info("saveNewEntity");
        log.info("---------------------------------------------------------------------------");
        try {
            log.info((this.entity != null) ? this.entity.toString() : "null");
            this.entity.updateSearchindex();
            this.entity = this.entityService.addNew(this.entity);
            log.info((this.entity != null) ? this.entity.toString() : "null");
            this.petTypeViewFlow.setFlowStateDetails();
            String summaryKey = "org.woehlke.jakartaee.petclinic.petType.addNew.done";
            String summary = this.petclinicApplication.getMsg().getString(summaryKey);
            flashMessagesView.addInfoMessage(summary, this.entity.getPrimaryKey());
        } catch (EJBException e) {
            this.petTypeViewFlow.setFlowStateNew();
            log.info(e.getMessage() + this.entity.toString());
            flashMessagesView.addWarnMessage(e, this.entity);
        }
    }

    @Override
    public void saveEditedEntity() {
        log.info("---------------------------------------------------------------------------");
        log.info("saveEditedEntity");
        log.info("---------------------------------------------------------------------------");
        try {
            log.info((this.entity != null) ? this.entity.toString() : "null");
            this.entity.updateSearchindex();
            this.entity = this.entityService.update(this.entity);
            log.info((this.entity != null) ? this.entity.toString() : "null");
            this.petTypeViewFlow.setFlowStateDetails();
            String summaryKey = "org.woehlke.jakartaee.petclinic.petType.edit.done";
            String summary = this.petclinicApplication.getMsg().getString(summaryKey);
            flashMessagesView.addInfoMessage(summary, this.entity.getPrimaryKey());
        } catch (EJBException e) {
            this.petTypeViewFlow.setFlowStateEdit();
            log.info(e.getMessage() + this.entity.toString());
        }
    }

    @Override
    public void deleteSelectedEntity() {
        log.info("---------------------------------------------------------------------------");
        log.info("deleteSelectedEntity");
        log.info("---------------------------------------------------------------------------");
        try {
            if (this.entity != null) {
                String msgInfo = this.entity.getPrimaryKey();
                entityService.delete(this.entity.getId());
                this.entity = null;
                String summaryKey = "org.woehlke.jakartaee.petclinic.petType.delete.done";
                String summary = this.petclinicApplication.getMsg().getString(summaryKey);
                flashMessagesView.addInfoMessage(summary, msgInfo);
                this.petTypeViewFlow.setFlowStateList();
            }
            this.petTypeViewFlow.setFlowStateList();
        } catch (EJBTransactionRolledbackException e) {
            this.petTypeViewFlow.setFlowStateDelete();
            String summaryKey = "org.woehlke.jakartaee.petclinic.petType.delete.denied";
            String summary = this.petclinicApplication.getMsg().getString(summaryKey);
            flashMessagesView.addWarnMessage(summary, this.entity);
        } catch (EJBException e) {
            this.petTypeViewFlow.setFlowStateDelete();
            flashMessagesView.addErrorMessage(e.getLocalizedMessage(), this.entity);
        }
    }

    @Override
    public void performSearch() {
        log.info("---------------------------------------------------------------------------");
        log.info("performSearch");
        log.info("---------------------------------------------------------------------------");
        String summaryKey = "org.woehlke.jakartaee.petclinic.petType.search.done";
        String summary = this.petclinicApplication.getMsg().getString(summaryKey);
        if (searchterm == null || searchterm.isEmpty()) {
            this.petTypeViewFlow.setFlowStateList();
            String missingKey = "org.woehlke.jakartaee.petclinic.list.searchterm.missing";
            String detail = this.petclinicApplication.getMsg().getString(missingKey);
            flashMessagesView.addInfoMessage(summary, detail);
        } else {
            this.petTypeViewFlow.setFlowStateSearchResult();
            this.list = entityService.search(searchterm);
            String foundKey = "org.woehlke.jakartaee.petclinic.list.searchterm.found";
            String resultsKey = "org.woehlke.jakartaee.petclinic.list.searchterm.results";
            String found = this.petclinicApplication.getMsg().getString(foundKey);
            String results = this.petclinicApplication.getMsg().getString(resultsKey);
            String detail = found + " " + this.list.size() + " " + results + " " + searchterm;
            flashMessagesView.addInfoMessage(summary, detail);
        }
    }

    public List<PetType> getList() {
        if (this.petTypeViewFlow.isFlowStateSearchResult()) {
            performSearch();
        } else {
            loadList();
        }
        this.flashMessagesView.flashTheMessages();
        return list;
    }

    @Override
    public void loadList() {
        this.list = this.entityService.getAll();
    }

    
    @Override
    @PostConstruct
    public void postConstruct() {
        log.info("---------------------------------------------------------------------------");
        log.info("postConstruct: " + PetTypeViewImpl.class.getSimpleName());
        log.info("---------------------------------------------------------------------------");
        this.petTypeViewFlow.setFlowStateList();
    }

    @Override
    @PreDestroy
    public void preDestroy() {
        log.info("---------------------------------------------------------------------------");
        log.info("preDestroy" + PetTypeViewImpl.class.getSimpleName());
        log.info("---------------------------------------------------------------------------");
    }

}
