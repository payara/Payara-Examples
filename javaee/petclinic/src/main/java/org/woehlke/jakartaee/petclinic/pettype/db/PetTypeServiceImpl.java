package org.woehlke.jakartaee.petclinic.pettype.db;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import lombok.extern.java.Log;
import org.woehlke.jakartaee.petclinic.pettype.PetType;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

import java.io.Serializable;
import java.util.List;

/**
 *
 */
@Log
@Stateless
public class PetTypeServiceImpl implements PetTypeService, Serializable  {

    private static final long serialVersionUID = -6242995649030237034L;

    @EJB
    private PetTypeDao petTypeDao;

    @Override
    public List<PetType> getAll() {
        return petTypeDao.getAll();
    }

    @Override
    public void delete(long id) {
        log.info("delete PetType: " + id);
        this.petTypeDao.delete(id);
    }

    @Override
    public PetType addNew(PetType petType) {
        log.info("addNew PetType: " + petType.toString());
        return this.petTypeDao.addNew(petType);
    }

    @Override
    public PetType findById(long id) {
        return this.petTypeDao.findById(id);
    }

    @Override
    public PetType update(PetType petType) {
        log.info("about to update: " + petType.toString());
        return this.petTypeDao.update(petType);
    }

    @Override
    public List<PetType> search(String searchterm) {
        return this.petTypeDao.search(searchterm);
    }

    @Override
    public void resetSearchIndex() {
        for(PetType s:getAll()){
            this.petTypeDao.update(s);
        }
    }

    @Override
    public PetType findByName(String name) {
        return this.petTypeDao.findByName(name);
    }


    @PostConstruct
    public void postConstruct() {
        log.info("postConstruct: "+PetTypeServiceImpl.class.getSimpleName());
    }

    @PreDestroy
    public void preDestroy() {
        log.info("preDestroy: "+PetTypeServiceImpl.class.getSimpleName());
    }
}
