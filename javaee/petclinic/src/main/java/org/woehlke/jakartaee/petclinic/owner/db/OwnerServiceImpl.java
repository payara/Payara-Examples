package org.woehlke.jakartaee.petclinic.owner.db;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.validation.constraints.NotNull;
import lombok.extern.java.Log;
import org.woehlke.jakartaee.petclinic.pet.db.PetDao;
import org.woehlke.jakartaee.petclinic.visit.db.VisitDao;
import org.woehlke.jakartaee.petclinic.owner.Owner;
import org.woehlke.jakartaee.petclinic.pet.Pet;
import org.woehlke.jakartaee.petclinic.visit.Visit;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

import java.io.Serializable;
import java.util.List;

/**
 * Created by tw on 10.03.14.
 */
@Log
@Stateless
public class OwnerServiceImpl implements OwnerService, Serializable {

    private static final long serialVersionUID = -553095693269912269L;

    @EJB
    private OwnerDao ownerDao;

    @EJB
    private PetDao petDao;

    @EJB
    private VisitDao visitDao;

    @Override
    public Visit addNewVisit(Visit visit) {
        log.info("addNew Visit: " + visit.toString());
        Pet pet = visit.getPet();
        Owner owner = pet.getOwner();
        visit.setPet(null);
        visit = visitDao.addNew(visit);
        owner = ownerDao.update(owner);
        pet.setOwner(owner);
        pet = petDao.update(pet);
        visit.setPet(pet);
        visit = visitDao.update(visit);
        log.info("added new Visit - updated owner: " + owner.toString());
        log.info("added new Visit - updated pet:   " + pet.toString());
        log.info("added new Visit:                 " + visit.toString());
        return visit;
    }

    @Override
    public List<Pet> getPetsAsList(@NotNull Owner owner){
        return petDao.getPetsAsList(owner);
    }

    @Override
    public String getPetsAsString(@NotNull Owner owner) {
        StringBuilder s = new StringBuilder();
        for (Pet pet : this.getPetsAsList(owner)) {
            s.append(pet.getName())
                    .append(" (")
                    .append(pet.getType().getName())
                    .append(") ");
        }
        return s.toString();
    }

    @Override
    public void resetSearchIndex() {
        for(Owner owner: this.getAll()){
            for (Pet pet : this.getPetsAsList(owner)) {
                for(Visit visit:visitDao.getVisits(pet)){
                    this.visitDao.update(visit);
                }
                this.petDao.update(pet);
            }
            this.ownerDao.update(owner);
        }
    }

    @Override
    public List<Owner> getAll() {
        return this.ownerDao.getAll();
    }

    @Override
    public void delete(long id) {
        this.ownerDao.delete(id);
    }

    @Override
    public Owner addNew(Owner owner) {
        //owner = this.updateSearchindex(owner); TODO
        log.info("addNew Owner: " + owner.toString());
        return this.ownerDao.addNew(owner);
    }

    @Override
    public Owner findById(long id) {
        return this.ownerDao.findById(id);
    }

    @Override
    public Owner update(Owner owner) {
        //owner = this.updateSearchindex(owner); TODO
        log.info("update Owner: " + owner.toString());
        return this.ownerDao.update(owner);
    }

    private Owner updateSearchindex(Owner owner) {
        //TODO
        for(Pet pet:this.petDao.getPetsAsList(owner)){
            for(Visit visit:visitDao.getVisits(pet)){
                this.visitDao.update(visit);
            }
            this.petDao.update(pet);
        }
        return owner;
    }

    @Override
    public List<Owner> search(String searchterm) {
        return this.ownerDao.search(searchterm);
    }

    @PostConstruct
    public void postConstruct() {
        log.info("postConstruct: "+OwnerServiceImpl.class.getSimpleName());
    }

    @PreDestroy
    public void preDestroy() {
        log.info("preDestroy: "+OwnerServiceImpl.class.getSimpleName());
    }
}
