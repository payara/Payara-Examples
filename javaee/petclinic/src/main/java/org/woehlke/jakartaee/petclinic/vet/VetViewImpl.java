package org.woehlke.jakartaee.petclinic.vet;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import org.primefaces.model.DualListModel;
import org.woehlke.jakartaee.petclinic.application.conf.PetclinicApplication;
import org.woehlke.jakartaee.petclinic.application.views.FlashMessagesView;
import org.woehlke.jakartaee.petclinic.application.views.LanguageView;
import org.woehlke.jakartaee.petclinic.specialty.Specialty;
import org.woehlke.jakartaee.petclinic.specialty.db.SpecialtyService;
import org.woehlke.jakartaee.petclinic.vet.db.VetService;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.ejb.EJB;
import jakarta.ejb.EJBException;
import jakarta.ejb.EJBTransactionRolledbackException;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.woehlke.jakartaee.petclinic.vet.views.VetFlowViewImpl;

import java.io.Serializable;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: tw
 * Date: 01.01.14
 * Time: 22:59
 * To change this template use File | Settings | File Templates.
 */
@Log
@Getter
@Setter
@Named("vetView")
@SessionScoped
public class VetViewImpl implements VetView, Serializable {

    private static final long serialVersionUID = 2838339162976374606L;

    @Inject
    private PetclinicApplication petclinicApplication;

    @Inject
    private LanguageView languageView;

    @Inject
    private FlashMessagesView flashMessagesView;

    @Inject
    private VetFlowViewImpl vetViewFlow;

    @EJB
    private SpecialtyService specialtyService;

    @EJB
    private VetService entityService;


    private Vet entity;
    private DualListModel<Specialty> specialtiesPickList;
    private List<Vet> list;
    private String searchterm;


    @Override
    public String showDetailsForm(Vet o) {
        log.info("showDetailsForm");;
        if (o != null) {
            this.entity = entityService.findById(o.getId());
            this.resetSpecialtiesPickList();
            this.vetViewFlow.setFlowStateDetails();
        } else {
            this.vetViewFlow.setFlowStateList();
        }
        return JSF_PAGE;
    }

    @Override
    public  String cancelDetails(){
        log.info("cancelDetails");
        this.vetViewFlow.setFlowStateList();
        return JSF_PAGE;
    }

    @Override
    public String showNewForm() {
        log.info("showNewForm");
        this.newEntity();
        this.resetSpecialtiesPickList();
        this.vetViewFlow.setFlowStateNew();
        return JSF_PAGE;
    }

    @Override
    public String cancelNew() {
        log.info("cancelNew");
        this.vetViewFlow.setFlowStateList();
        return JSF_PAGE;
    }

    @Override
    public String saveNew() {
        log.info("saveNew");
        this.saveNewEntity();
        this.vetViewFlow.setFlowStateDetails();
        return JSF_PAGE;
    }

    @Override
    public String showEditForm() {
        log.info("showEditForm");
        if (this.entity != null) {
            this.resetSpecialtiesPickList();
            this.vetViewFlow.setFlowStateEdit();
        } else {
            this.vetViewFlow.setFlowStateList();
        }
        return JSF_PAGE;
    }

    @Override
    public String cancelEdited() {
        log.info("cancelEdited");
        this.vetViewFlow.setFlowStateDetails();
        return JSF_PAGE;
    }

    @Override
    public String saveEdited() {
        log.info("saveEdited");
        this.saveEditedEntity();
        this.vetViewFlow.setFlowStateDetails();
        return JSF_PAGE;
    }

    @Override
    public String showDeleteForm() {
        log.info("showDeleteForm");
        if (this.entity != null) {
            this.vetViewFlow.setFlowStateDelete();
        } else {
            this.vetViewFlow.setFlowStateList();
        }
        return JSF_PAGE;
    }

    @Override
    public String cancelDelete() {
        log.info("cancelDelete");
        this.vetViewFlow.setFlowStateDetails();
        return JSF_PAGE;
    }

    @Override
    public String performDelete() {
        log.info("performDelete");
        this.deleteSelectedEntity();
        this.vetViewFlow.setFlowStateList();
        return JSF_PAGE;
    }

    @Override
    public String clearSearchterm(){
        log.info("clearSearchterm");
        this.searchterm = null;
        this.vetViewFlow.setFlowStateList();
        return JSF_PAGE;
    }

    @Override
    public String search() {
        log.info("search");
        this.performSearch();
        return JSF_PAGE;
    }


    @Override
    public void loadList() {
        this.list = this.entityService.getAll();
    }

    @Override
    public void newEntity() {
        log.info("newEntity");
        this.entity = new Vet();
    }

    @Override
    public void saveNewEntity() {
        log.info("saveNewEntity");
        try {
            log.info("try to save New: " + this.entity.toString());
            this.entity.removeSpecialties();
            this.entity.setUuid(UUID.randomUUID());
            this.entity = entityService.addNew(this.entity);
            this.entity = entityService.findById(this.entity.getId());
            log.info("nr source: " + this.specialtiesPickList.getSource().size());
            log.info("nr target: " + this.specialtiesPickList.getTarget().size());
            for (Specialty specialtyTransient : this.specialtiesPickList.getTarget()) {
                Specialty specialty = specialtyService.findSpecialtyByName(specialtyTransient.getName());
                this.entity.addSpecialty(specialty);
            }
            this.entity = entityService.update(this.entity);
            log.info("saved New: " + this.entity.toString());
            String summaryKey = "org.woehlke.jakartaee.petclinic.veterinarian.addNew.done";
            String summary = this.petclinicApplication.getMsg().getString(summaryKey);
            flashMessagesView.addInfoMessage(summary, this.entity);
        } catch (EJBException e) {
            log.info(e.getMessage() + this.entity.toString());
            flashMessagesView.addWarnMessage(e, this.entity);
        }
    }

    @Override
    public void saveEditedEntity() {
        log.info("saveEditedEntity");
        try {
            this.entity.removeSpecialties();
            for (Specialty specialtyTransient : this.specialtiesPickList.getTarget()) {
                log.info(" added transient via saveEditedEntity: " + specialtyTransient.getPrimaryKeyWithId());
                Specialty specialty = specialtyService.findSpecialtyByName(specialtyTransient.getName());
                this.entity.addSpecialty(specialty);
            }
            this.entity = entityService.update(this.entity);
            String summaryKey = "org.woehlke.jakartaee.petclinic.veterinarian.edit.done";
            String summary = this.petclinicApplication.getMsg().getString(summaryKey);
            flashMessagesView.addInfoMessage(summary, this.entity);
        } catch (EJBException e) {
            log.info(e.getMessage() + this.entity.toString());
            flashMessagesView.addWarnMessage(e, this.entity);
        }
    }

    @Override
    public void deleteSelectedEntity() {
        log.info("deleteSelectedEntity");
        try {
            if (this.entity != null) {
                String msgInfo = this.entity.getPrimaryKey();
                long id = this.entity.getId();
                entityService.delete(id);
                this.entity = null;
                String summaryKey = "org.woehlke.jakartaee.petclinic.veterinarian.delete.done";
                String summary = this.petclinicApplication.getMsg().getString(summaryKey);
                flashMessagesView.addInfoMessage(summary, msgInfo);
            }
            loadList();
        } catch (EJBTransactionRolledbackException e) {
            String summaryKey = "org.woehlke.jakartaee.petclinic.veterinarian.delete.denied";
            String summary = this.petclinicApplication.getMsg().getString(summaryKey);
            flashMessagesView.addWarnMessage(summary, this.entity);
        } catch (EJBException e) {
            flashMessagesView.addErrorMessage(e.getLocalizedMessage(), this.entity);
        }
    }

    @Override
    public void performSearch() {
        log.info("performSearch");
        String summaryKey = "org.woehlke.jakartaee.petclinic.veterinarian.search.done";
        String summary = this.petclinicApplication.getMsg().getString(summaryKey);
        if (searchterm == null || searchterm.isEmpty()) {
            this.vetViewFlow.setFlowStateList();
            String missingKey = "org.woehlke.jakartaee.petclinic.frontend.web.entity.list.searchterm.missing";
            String detail = this.petclinicApplication.getMsg().getString(missingKey);
            flashMessagesView.addInfoMessage(summary, detail);
        } else {
            this.vetViewFlow.setFlowStateSearchResult();
            this.list = entityService.search(searchterm);
            String foundKey = "org.woehlke.jakartaee.petclinic.frontend.web.entity.list.searchterm.found";
            String resultsKey = "org.woehlke.jakartaee.petclinic.frontend.web.entity.list.searchterm.results";
            String found = this.petclinicApplication.getMsg().getString(foundKey);
            String results = this.petclinicApplication.getMsg().getString(resultsKey);
            String detail = found + " " + this.list.size() + " " + results + " " + searchterm;
            flashMessagesView.addInfoMessage(summary, detail);
        }
    }

    public void resetSpecialtiesPickList() {
        log.info("resetSpecialtiesPickList");
        List<Specialty> srcList = new ArrayList<>();
        List<Specialty> targetList = new ArrayList<>();
        if(this.entity != null) {
            Long id = this.entity.getId();
            if (id != null) {
                this.entity = this.entityService.findById(id);
                targetList.addAll(this.entity.getSpecialtiesAsList());
                for (Specialty specialty : this.specialtyService.getAll()) {
                    if (!targetList.contains(specialty)) {
                        srcList.add(specialty);
                    }
                }
            } else {
                srcList.addAll(this.specialtyService.getAll());
            }
        } else {
            srcList.addAll(this.specialtyService.getAll());
        }
        this.specialtiesPickList = new DualListModel<>(srcList, targetList);
    }

    @Override
    public List<Vet> getList() {
        if (this.vetViewFlow.isFlowStateSearchResult()) {
            this.performSearch();
        } else {
            this.loadList();
        }
        this.flashMessagesView.flashTheMessages();
        return this.list;
    }

    @Override
    public Specialty findSpecialtyByName(String name) {
        return specialtyService.findSpecialtyByName(name);
    }

    @Override
    @PostConstruct
    public void postConstruct() {
        log.info("postConstruct: " + VetViewImpl.class.getSimpleName());
        this.vetViewFlow.setFlowStateList();
        this.loadList();
    }

    @Override
    @PreDestroy
    public void preDestroy() {
        log.info("preDestroy");
    }
}
