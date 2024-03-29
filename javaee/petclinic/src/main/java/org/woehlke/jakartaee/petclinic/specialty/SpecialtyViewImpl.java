package org.woehlke.jakartaee.petclinic.specialty;

import jakarta.ejb.EJB;
import jakarta.ejb.EJBException;
import jakarta.ejb.EJBTransactionRolledbackException;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import org.woehlke.jakartaee.petclinic.application.conf.PetclinicApplication;
import org.woehlke.jakartaee.petclinic.application.views.FlashMessagesView;
import org.woehlke.jakartaee.petclinic.application.views.LanguageView;
import org.woehlke.jakartaee.petclinic.specialty.db.SpecialtyService;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.woehlke.jakartaee.petclinic.specialty.views.SpecialtyFlowView;

import java.io.Serializable;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: tw
 * Date: 04.01.14
 * Time: 12:00
 * To change this template use File | Settings | File Templates.
 */
@Log
@Getter
@Setter
@Named("specialtyView")
@SessionScoped
public class SpecialtyViewImpl implements SpecialtyView, Serializable {

    private static final long serialVersionUID = 9080853875975855082L;

    private Specialty entity;
    private List<Specialty> list;

    private String searchterm;

    @Inject
    private PetclinicApplication petclinicApplication;

    @Inject
    private LanguageView languageView;

    @Inject
    private FlashMessagesView flashMessagesView;

    @Inject
    private SpecialtyFlowView specialtyViewFlow;

    @EJB
    private SpecialtyService entityService;

    @Override
    public String showDetailsForm(Specialty o) {
        log.info("showDetailsForm");
        if (o != null) {
            this.entity = entityService.findById(o.getId());
            this.specialtyViewFlow.setFlowStateDetails();
            return JSF_PAGE_DETAILS;
        } else {
            this.specialtyViewFlow.setFlowStateList();
            return JSF_PAGE;
        }
    }

    @Override
    public  String cancelDetails(){
        log.info("cancelDetails");
        this.specialtyViewFlow.setFlowStateList();
        return JSF_PAGE;
    }

    @Override
    public String showNewForm() {
        log.info("showNewForm");
        this.newEntity();
        this.specialtyViewFlow.setFlowStateNew();
        return JSF_PAGE_NEW;
    }

    @Override
    public String cancelNew() {
        log.info("cancelNew");
        this.specialtyViewFlow.setFlowStateList();
        return JSF_PAGE;
    }

    @Override
    public String saveNew() {
        log.info("saveNew");
        this.saveNewEntity();
        this.specialtyViewFlow.setFlowStateDetails();
        return JSF_PAGE_DETAILS;
    }

    @Override
    public String showEditForm() {
        log.info("showEditForm");
        if ( this.entity != null) {
            this.specialtyViewFlow.setFlowStateEdit();
            return JSF_PAGE_EDIT;
        } else {
            this.specialtyViewFlow.setFlowStateList();
            return JSF_PAGE;
        }
    }

    @Override
    public String cancelEdited() {
        log.info("cancelEdited");
        this.specialtyViewFlow.setFlowStateDetails();
        return JSF_PAGE_DETAILS;
    }

    @Override
    public String saveEdited() {
        log.info("saveEdited");
        this.saveEditedEntity();
        this.specialtyViewFlow.setFlowStateDetails();
        return JSF_PAGE_DETAILS;
    }

    @Override
    public String showDeleteForm() {
        log.info("showDeleteForm");
        if ( this.entity != null) {
            this.specialtyViewFlow.setFlowStateDelete();
            return JSF_PAGE_DELETE;
        } else {
            this.specialtyViewFlow.setFlowStateList();
            return JSF_PAGE;
        }
    }

    @Override
    public String cancelDelete() {
        log.info("cancelDelete");
        this.specialtyViewFlow.setFlowStateDetails();
        return JSF_PAGE_DETAILS;
    }

    @Override
    public String performDelete() {
        log.info("performDelete");
        deleteSelectedEntity();
        this.specialtyViewFlow.setFlowStateList();
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
    public void loadList() {
        this.list = entityService.getAll();
    }

    @Override
    public void newEntity() {
        log.info("newEntity");
        String name = "add new name";
        this.entity = Specialty.newEntity();
    }

    @Override
    public void saveNewEntity() {
        log.info("saveNewEntity");
        try {
            this.entity = entityService.addNew(this.entity);
            this.specialtyViewFlow.setFlowStateList();
            String summaryKey = "org.woehlke.jakartaee.petclinic.specialty.search.done";
            String summary = this.petclinicApplication.getMsg().getString(summaryKey);
            flashMessagesView.addInfoMessage(summary, this.entity.getPrimaryKey());
        } catch (EJBException e) {
            this.specialtyViewFlow.setFlowStateNew();
            flashMessagesView.addWarnMessage(e.getLocalizedMessage(), this.entity);
        }
    }

    @Override
    public void saveEditedEntity() {
        log.info("saveEditedEntity");
        try {
            this.entity = this.entityService.update(this.entity);
            this.specialtyViewFlow.setFlowStateList();
            String summaryKey = "org.woehlke.jakartaee.petclinic.specialty.edit.done";
            String summary =this.petclinicApplication.getMsg().getString(summaryKey);
            flashMessagesView.addInfoMessage(summary, this.entity);
        } catch (EJBException e) {
            this.specialtyViewFlow.setFlowStateEdit();
            flashMessagesView.addWarnMessage(e.getLocalizedMessage(), this.entity);
        }
    }

    @Override
    public void deleteSelectedEntity() {
        log.info("deleteSelectedEntity");
        try {
            if (this.entity != null) {
                String details = this.entity.getPrimaryKey();
                entityService.delete(this.entity.getId());
                this.entity = null;
                this.specialtyViewFlow.setFlowStateList();
                String summaryKey = "org.woehlke.jakartaee.petclinic.specialty.delete.done";
                String summary =this.petclinicApplication.getMsg().getString(summaryKey);
                flashMessagesView.addInfoMessage(summary, details);
            }
        } catch (EJBTransactionRolledbackException e) {
            this.specialtyViewFlow.setFlowStateDelete();
            String summaryKey = "org.woehlke.jakartaee.petclinic.specialty.delete.denied";
            String summary =this.petclinicApplication.getMsg().getString(summaryKey);
            flashMessagesView.addWarnMessage(summary, this.entity);
        } catch (EJBException e) {
            this.specialtyViewFlow.setFlowStateDelete();
            flashMessagesView.addErrorMessage(e.getLocalizedMessage(), this.entity);
        }
    }

    @Override
    public void performSearch() {
        log.info("performSearch");
        String summaryKey = "org.woehlke.jakartaee.petclinic.specialty.search.done";
        String summary =this.petclinicApplication.getMsg().getString(summaryKey);
        if (searchterm == null || searchterm.isEmpty()) {
            this.specialtyViewFlow.setFlowStateList();
            String missingKey = "org.woehlke.jakartaee.petclinic.list.searchterm.missing";
            String detail =this.petclinicApplication.getMsg().getString(missingKey);
            flashMessagesView.addInfoMessage(summary, detail);
        } else {
            try {
                this.list = entityService.search(searchterm);
                this.specialtyViewFlow.setFlowStateSearchResult();
                String foundKey = "org.woehlke.jakartaee.petclinic.list.searchterm.found";
                String resultsKey = "org.woehlke.jakartaee.petclinic.list.searchterm.results";
                String found =this.petclinicApplication.getMsg().getString(foundKey);
                String results =this.petclinicApplication.getMsg().getString(resultsKey);
                String detail = found + " " + this.list.size() + " " + results + " " + searchterm;
                flashMessagesView.addInfoMessage(summary, detail);
            } catch (Exception e) {
                this.specialtyViewFlow.setFlowStateList();
                flashMessagesView.addWarnMessage(e.getLocalizedMessage(), searchterm);
            }
        }
    }

    @Override
    public List<Specialty> getList() {
        if (this.specialtyViewFlow.isFlowStateSearchResult()) {
            performSearch();
        } else {
            loadList();
        }
        this.flashMessagesView.flashTheMessages();
        return this.list;
    }

    @Override
    @PostConstruct
    public void postConstruct() {
        log.info("postConstruct: " + SpecialtyViewImpl.class.getSimpleName());
        this.specialtyViewFlow.setFlowStateList();
    }

    @Override
    @PreDestroy
    public void preDestroy() {
        log.info("preDestroy");
    }

}
