package org.woehlke.jakartaee.petclinic.owner.db;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import lombok.extern.java.Log;
import org.woehlke.jakartaee.petclinic.owner.Owner;
import org.woehlke.jakartaee.petclinic.pet.Pet;
import org.woehlke.jakartaee.petclinic.pet.db.PetDao;
import org.woehlke.jakartaee.petclinic.pettype.PetType;
import org.woehlke.jakartaee.petclinic.pettype.db.PetTypeDao;
import org.woehlke.jakartaee.petclinic.pettype.db.PetTypeService;
import org.woehlke.jakartaee.petclinic.visit.Visit;
import org.woehlke.jakartaee.petclinic.visit.db.VisitDao;

import java.io.Serializable;
import java.util.List;

@Log
@Stateless
public class OwnerViewServiceImpl implements OwnerViewService, Serializable {

    private static final long serialVersionUID = -553095668269912269L;

    @EJB
    private PetTypeDao petTypeDao;

    @EJB
    private OwnerDao ownerDao;

    @EJB
    private VisitDao visitDao;

    @EJB
    private PetDao petDao;

    @Override
    public void deleteOwner(long ownerId) {
        Owner owner = ownerDao.findById(ownerId);
        for(Pet pet:petDao.getPetsAsList(owner)){
            for(Visit visit:visitDao.getVisits(pet)){
                visitDao.delete(visit.getId());
            }
            petDao.delete(pet.getId());
        }
        ownerDao.delete(ownerId);
    }

    @Override
    public List<Owner> getAllOwner() {
        return ownerDao.getAll();
    }

    @Override
    public Owner findOwnerById(long id) {
        return ownerDao.findById(id);
    }

    @Override
    public Owner updateOwner(Owner entity) {
        return ownerDao.update(entity);
    }

    @Override
    public Owner addNewOwner(Owner entity) {
        return ownerDao.addNew(entity);
    }

    @Override
    public List<Owner> searchOwner(String searchterm) {
        return ownerDao.search(searchterm);
    }

    @Override
    public PetType findPetTypeById(long petTypeId) {
        return petTypeDao.findById(petTypeId);
    }

    @Override
    public List<PetType> getAllPetType() {
        return petTypeDao.getAll();
    }

    @Override
    public List<Pet> getPetsAsList(Owner owner) {
        return petDao.getPetsAsList(owner);
    }

    @Override
    public String getPetsAsString(Owner owner) {
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
    public Pet addNewPet(Pet pet) {
        return petDao.addNew(pet);
    }

    @Override
    public Pet findPetById(long id) {
        return petDao.findById(id);
    }

    @Override
    public Pet updatePet(Pet pet) {
        return petDao.update(pet);
    }

    @Override
    public List<Visit> getVisits(Pet ownersPet) {
        return visitDao.getVisits(ownersPet);
    }

    @Override
    public Visit addNewVisit(Visit visit) {
        return visitDao.addNew(visit);
    }


    @PostConstruct
    public void postConstruct() {
        log.info("postConstruct: "+OwnerViewServiceImpl.class.getSimpleName());
    }

    @PreDestroy
    public void preDestroy() {
        log.info("preDestroy: "+OwnerViewServiceImpl.class.getSimpleName());
    }
}
