package org.woehlke.jakartaee.petclinic.visit.db;

import lombok.extern.java.Log;
import org.woehlke.jakartaee.petclinic.owner.Owner;
import org.woehlke.jakartaee.petclinic.pet.Pet;
import org.woehlke.jakartaee.petclinic.visit.Visit;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;

import java.io.Serializable;
import java.util.List;


/**
 *
 */
@Log
@Stateless
public class VisitServiceImpl implements VisitService, Serializable {

    private static final long serialVersionUID = 4560958540651968289L;

    @EJB
    private VisitDao visitDao;

    @Override
    public List<Visit> getAll() {
        return this.visitDao.getAll();
    }

    @Override
    public Visit findById(long id) {
        return this.visitDao.findById(id);
    }

    @Override
    public Visit addNew(Visit visit) {
        log.info("addNew Visit: " + visit.toString());
        return this.visitDao.addNew(visit);
    }

    @Override
    public Visit update(Visit visit) {
        log.info("update Visit: " + visit.toString());
        return this.visitDao.update(visit);
    }

    @Override
    public void delete(long id) {
        log.info("delete: " + id);
        this.visitDao.delete(id);
    }

    @Override
    public List<Visit> getAllVisitsOfAnPet(Pet pet) {
        return  this.visitDao.getVisits(pet);
    }

    @PostConstruct
    public void postConstruct() {
        log.info("postConstruct: "+VisitServiceImpl.class.getSimpleName());
    }

    @PreDestroy
    public void preDestroy() {
        log.info("preDestroy: "+VisitServiceImpl.class.getSimpleName());
    }


}
