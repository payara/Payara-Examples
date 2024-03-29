package org.woehlke.jakartaee.petclinic.owner;

import jakarta.ejb.EJB;
import jakarta.ejb.EJBException;
import jakarta.ejb.EJBTransactionRolledbackException;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import org.woehlke.jakartaee.petclinic.application.conf.PetclinicApplication;
import org.woehlke.jakartaee.petclinic.application.views.FlashMessagesView;
import org.woehlke.jakartaee.petclinic.application.views.LanguageView;
import org.woehlke.jakartaee.petclinic.owner.db.OwnerViewService;
import org.woehlke.jakartaee.petclinic.owner.views.OwnerFlowView;
import org.woehlke.jakartaee.petclinic.pet.Pet;
import org.woehlke.jakartaee.petclinic.pettype.PetType;
import org.woehlke.jakartaee.petclinic.visit.Visit;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: tw
 * Date: 06.01.14
 * Time: 16:24
 * To change this template use File | Settings | File Templates.
 */
@Log
@Getter
@Setter
@Named("ownerView")
@SessionScoped
public class OwnerViewImpl implements OwnerView, Serializable {

    private static final long serialVersionUID = -4809817472969005481L;

    @Inject
    private PetclinicApplication petclinicApplication;

    @Inject
    private LanguageView languageView;

    @Inject
    private FlashMessagesView flashMessagesView;

    @Inject
    private OwnerFlowView ownerFlowView;

    /*
    @EJB
    private OwnerService entityService;

    @EJB
    private PetService petService;

    @EJB
    private PetTypeService petTypeService;

    @EJB
    private VisitService visitService;
    */

    @EJB
    private OwnerViewService ownerViewService;

    private String searchterm;
    private List<Owner> list;
    private Owner entity;
    private Pet pet;
    private List<PetType> petTypeList;
    private long petTypeId;
    private Visit visit;

    @Override
    public String showDetailsForm(Owner o) {
        log.info("showDetailsForm");
        if (o != null) {
            this.entity = this.ownerViewService.findOwnerById(o.getId());
            this.ownerFlowView.setFlowStateDetails();
        } else {
            this.ownerFlowView.setFlowStateList();
        }
        return JSF_PAGE;
    }

    @Override
    public  String cancelDetails(){
        log.info("cancelDetails");
        this.ownerFlowView.setFlowStateList();
        return JSF_PAGE;
    }

    @Override
    public String showNewForm() {
        log.info("showNewForm");
        this.newEntity();
        this.ownerFlowView.setFlowStateNew();
        return JSF_PAGE;
    }

    @Override
    public String cancelNew() {
        log.info("cancelNew");
        this.ownerFlowView.setFlowStateList();
        return JSF_PAGE;
    }

    @Override
    public String saveNew() {
        log.info("saveNew");
        this.saveNewEntity();
        this.ownerFlowView.setFlowStateDetails();
        return JSF_PAGE;
    }

    @Override
    public String showEditForm() {
        log.info("showEditForm");
        if (this.entity != null) {
            this.ownerFlowView.setFlowStateEdit();
        } else {
            this.ownerFlowView.setFlowStateList();
        }
        return JSF_PAGE;
    }

    @Deprecated
    @Override
    public String showOwnerEditForm() {
        log.info("showOwnerEditForm");
        if (this.entity != null) {
            this.ownerFlowView.setFlowStateEdit();
        } else {
            this.ownerFlowView.setFlowStateList();
        }
        return JSF_PAGE;
    }

    @Override
    public List<Pet> getPetsAsList(Owner owner) {
        return ownerViewService.getPetsAsList(owner);
    }

    @Override
    public String getPetsAsString(Owner owner) {
        return ownerViewService.getPetsAsString(owner);
    }

    @Override
    public List<Visit> getVisits(Pet ownersPet){
        return ownerViewService.getVisits(ownersPet);
    }

    @Override
    public String cancelEdited() {
        log.info("cancelEdited");
        this.ownerFlowView.setFlowStateDetails();
        return JSF_PAGE;
    }

    @Override
    public String saveEdited() {
        log.info("saveEdited");
        this.saveEditedEntity();
        this.ownerFlowView.setFlowStateDetails();
        return JSF_PAGE;
    }

    @Override
    public String showDeleteForm() {
        log.info("showDeleteForm");
        if (this.entity != null) {
            this.ownerFlowView.setFlowStateDelete();
        } else {
            this.ownerFlowView.setFlowStateList();
        }
        return JSF_PAGE;
    }

    @Override
    public String cancelDelete() {
        log.info("cancelDelete");
        this.ownerFlowView.setFlowStateDetails();
        return JSF_PAGE;
    }

    @Override
    public String performDelete() {
        log.info("performDelete");
        this.ownerFlowView.setFlowStateDelete();
        try {
            long id = this.entity.getId();
            String uuid = this.entity.getUuid().toString();
            String selectedPrimaryKey = this.entity.getPrimaryKey() + "(" + id + "," + uuid + ")";
            this.ownerViewService.deleteOwner(this.entity.getId());
            this.entity = null;
            this.ownerFlowView.setFlowStateList();
            String summaryKey = "org.woehlke.jakartaee.petclinic.owner.delete.done";
            String summary = this.petclinicApplication.getMsg().getString(summaryKey);
            flashMessagesView.addInfoMessage(summary, selectedPrimaryKey);
            this.ownerFlowView.setFlowStateList();
        } catch (EJBException e) {
            flashMessagesView.addWarnMessage(e, this.entity);
        }
        return JSF_PAGE;
    }

    @Override
    public String cancelOwnerPetVisitNew() {
        log.info("cancelOwnerPetVisitNew");
        this.ownerFlowView.setFlowStateDetails();
        return JSF_PAGE;
    }

    @Override
    public String showOwnerPetNewForm() {
        log.info("showOwnerPetNewForm");
        this.pet = new Pet();
        this.ownerFlowView.setFlowStateNewPet();
        return JSF_PAGE;
    }

    @Override
    public String saveOwnerPetNew() {
        log.info("saveOwnerPetNew");
        try {
            PetType petType = this.ownerViewService.findPetTypeById(this.petTypeId);
            this.pet.setUuid(UUID.randomUUID());
            this.pet.setType(petType);
            this.pet.setOwner(this.entity);
            this.pet = this.ownerViewService.addNewPet(this.pet);
            String summaryKey = "org.woehlke.jakartaee.petclinic.owner.addNew.done";
            String summary = this.petclinicApplication.getMsg().getString(summaryKey);
            flashMessagesView.addInfoMessage(summary, this.pet);
            this.ownerFlowView.setFlowStateDetails();
        } catch (EJBException e) {
            this.ownerFlowView.setFlowStateNewPet();
            flashMessagesView.addWarnMessage(e, this.pet);
        }
        return JSF_PAGE;
    }

    @Override
    public String cancelOwnerPetNew() {
        log.info("cancelOwnerPetNew");
        this.ownerFlowView.setFlowStateDetails();
        return JSF_PAGE;
    }

    @Override
    public String showOwnerPetEditForm(Pet pet) {
        log.info("showOwnerPetEditForm");
        if (pet != null) {
            this.pet = this.ownerViewService.findPetById(pet.getId());
            this.ownerFlowView.setFlowStateEditPet();
        } else {
            this.ownerFlowView.setFlowStateDetails();
        }
        return JSF_PAGE;
    }

    @Override
    public String saveOwnerPetEdit() {
        log.info("saveOwnerPetEdit");
        try {
            PetType petType = this.ownerViewService.findPetTypeById(this.petTypeId);
            this.pet.setType(petType);
            this.ownerViewService.updatePet(this.pet);
            long ownerId = this.entity.getId();
            this.entity = this.ownerViewService.findOwnerById(ownerId);
            String summaryKey = "org.woehlke.jakartaee.petclinic.owner.edit.done";
            String summary = this.petclinicApplication.getMsg().getString(summaryKey);
            flashMessagesView.addInfoMessage(summary, this.pet);
            this.ownerFlowView.setFlowStateDetails();
        } catch (EJBException e) {
            this.ownerFlowView.setFlowStateEditPet();
            flashMessagesView.addWarnMessage(e, this.pet);
        }
        return JSF_PAGE;
    }

    @Override
    public String cancelOwnerPetEdit() {
        log.info("cancelOwnerPetEdit");
        this.ownerFlowView.setFlowStateDetails();
        return JSF_PAGE;
    }

    @Override
    public String editOwnerPetVisitNewForm(Pet rowPet) {
        log.info("editOwnerPetVisitNewForm");
        if (rowPet != null) {
            this.pet = this.ownerViewService.findPetById(rowPet.getId());
            this.petTypeId = this.pet.getType().getId();
            this.visit = new Visit();
            this.ownerFlowView.setFlowStateNewVisit();
            return JSF_PAGE;
        } else {
            String summaryKey = "org.woehlke.jakartaee.petclinic.owner.pet.choose.summary";
            String summary = this.petclinicApplication.getMsg().getString(summaryKey);
            String msgKey = "org.woehlke.jakartaee.petclinic.owner.pet.choose.detail";
            String msg = this.petclinicApplication.getMsg().getString(msgKey);
            flashMessagesView.addWarnMessage(summary, msg);
            this.ownerFlowView.setFlowStateDetails();
            return JSF_PAGE;
        }
    }

    @Override
    public String saveOwnerPetVisitNew(Pet rowPet) {
        log.info("saveOwnerPetVisitNew");
        try {
            if (rowPet != null) {
                this.pet = this.ownerViewService.findPetById(rowPet.getId());
                this.petTypeId = this.pet.getType().getId();
                this.visit.setPet(this.pet);
                //this.pet.addVisit(this.visit);
                this.visit = this.ownerViewService.addNewVisit(this.visit);
                String summaryKey = "org.woehlke.jakartaee.petclinic.owner.pet.visit.addNew.done";
                String summary = this.petclinicApplication.getMsg().getString(summaryKey);
                flashMessagesView.addInfoMessage(summary, this.visit);
                //reload View Data to display changes
                this.pet = this.ownerViewService.findPetById(rowPet.getId());
                this.entity = this.ownerViewService.findOwnerById(this.entity.getId());
            } else {
                String summaryKey = "org.woehlke.jakartaee.petclinic.owner.pet.visit.addNew";
                String summary = this.petclinicApplication.getMsg().getString(summaryKey);
                String msgKey = "org.woehlke.jakartaee.petclinic.owner.pet.visit.addNew.denied";
                String msg = this.petclinicApplication.getMsg().getString(msgKey);
                flashMessagesView.addWarnMessage(summary, msg);
            }
            this.ownerFlowView.setFlowStateDetails();
            return JSF_PAGE;
        } catch (EJBException e) {
            this.ownerFlowView.setFlowStateNewVisit();
            flashMessagesView.addWarnMessage(e, this.visit);
            return JSF_PAGE;
        }
    }

    @Override
    public String search() {
        log.info("search");
        String summaryKey = "org.woehlke.jakartaee.petclinic.owner.search.done";
        String summary = this.petclinicApplication.getMsg().getString(summaryKey);
        if (searchterm == null || searchterm.isEmpty()) {
            String missingKey = "org.woehlke.jakartaee.petclinic.list.searchterm.missing";
            String detail = this.petclinicApplication.getMsg().getString(missingKey);
            flashMessagesView.addInfoMessage(summary, detail);
            this.ownerFlowView.setFlowStateList();
        } else {
            this.performSearch();
            String foundKey = "org.woehlke.jakartaee.petclinic.list.searchterm.found";
            String resultsKey = "org.woehlke.jakartaee.petclinic.list.searchterm.results";
            String found = this.petclinicApplication.getMsg().getString(foundKey);
            String results = this.petclinicApplication.getMsg().getString(resultsKey);
            String detail = found + " " + this.list.size() + " " + results + " " + searchterm;
            flashMessagesView.addInfoMessage(summary, detail);
            this.ownerFlowView.setFlowStateSearchResult();
        }
        return JSF_PAGE;
    }

    @Override
    public String clearSearchterm(){
        log.info("clearSearchterm");
        this.searchterm = null;
        this.ownerFlowView.setFlowStateList();
        return JSF_PAGE;
    }

    @Override
    public void loadList() {
        this.list = this.ownerViewService.getAllOwner();
    }

    @Override
    public void loadPetTypeList() {
        this.petTypeList = this.ownerViewService.getAllPetType();
    }

    @Override
    public List<PetType> getPetTypeList() {
        this.loadPetTypeList();
        return petTypeList;
    }

    @Override
    public void newEntity() {
        this.entity = new Owner();
    }

    @Override
    public void saveNewEntity() {
        log.info("saveNewEntity");
        this.ownerFlowView.setFlowStateNew();
        try {
            if (this.entity != null) {
                this.entity = this.ownerViewService.addNewOwner(this.entity);
                String summaryKey = "org.woehlke.jakartaee.petclinic.owner.addNew.done";
                String summary = this.petclinicApplication.getMsg().getString(summaryKey);
                flashMessagesView.addInfoMessage(summary, this.entity);
                this.ownerFlowView.setFlowStateDetails();
            } else {
                this.newEntity();
            }
        } catch (EJBException e) {
            flashMessagesView.addWarnMessage(e, this.entity);
        }
    }

    @Override
    public void saveEditedEntity() {
        log.info("saveEditedEntity");
        this.ownerFlowView.setFlowStateEdit();
        try {
            if (this.entity != null) {
                this.entity = this.ownerViewService.updateOwner(this.entity);
                String summaryKey = "org.woehlke.jakartaee.petclinic.owner.edit.done";
                String summary = this.petclinicApplication.getMsg().getString(summaryKey);
                flashMessagesView.addInfoMessage(summary, this.entity);
                this.ownerFlowView.setFlowStateDetails();
            }
        } catch (EJBException e) {
            flashMessagesView.addWarnMessage(e, this.entity);
        }
    }

    @Override
    public void deleteSelectedEntity() {
        log.info("deleteSelectedEntity");
        this.ownerFlowView.setFlowStateDelete();
        try {
            if (this.entity != null) {
                String msgInfo = this.entity.getPrimaryKey();
                this.ownerViewService.deleteOwner(this.entity.getId());
                this.entity = null;
                String summaryKey = "org.woehlke.jakartaee.petclinic.owner.delete.done";
                String summary = this.petclinicApplication.getMsg().getString(summaryKey);
                flashMessagesView.addInfoMessage(summary, msgInfo);
                this.ownerFlowView.setFlowStateList();
                loadList();
            }
        } catch (EJBTransactionRolledbackException e) {
            String summaryKey = "org.woehlke.jakartaee.petclinic.owner.delete.denied";
            String summary = this.petclinicApplication.getMsg().getString(summaryKey);
            flashMessagesView.addWarnMessage(summary, this.entity);
        } catch (EJBException e) {
            this.ownerFlowView.setFlowStateDelete();
            flashMessagesView.addErrorMessage(e.getLocalizedMessage(), this.entity);
        }
    }

    @Override
    public void performSearch() {
        log.info("performSearch");
        this.list = ownerViewService.searchOwner(this.searchterm);
    }

    @Override
    public List<PetType> getAllPetTypes() {
        return this.ownerViewService.getAllPetType();
    }

    @Override
    public List<Owner> getList() {
        if (this.ownerFlowView.isFlowStateSearchResult()) {
            performSearch();
        } else {
            loadList();
        }
        this.flashMessagesView.flashTheMessages();
        return list;
    }

    @Override
    @PostConstruct
    public void postConstruct() {
        log.info("postConstruct: " + OwnerViewImpl.class.getSimpleName());
        this.ownerFlowView.setFlowStateList();
    }

    @Override
    @PreDestroy
    public void preDestroy() {
        log.info("preDestroy");
    }
}
