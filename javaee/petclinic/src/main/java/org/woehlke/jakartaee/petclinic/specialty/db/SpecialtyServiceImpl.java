package org.woehlke.jakartaee.petclinic.specialty.db;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import lombok.extern.java.Log;
import org.woehlke.jakartaee.petclinic.specialty.Specialty;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

import java.util.List;

@Log
@Stateless
public class SpecialtyServiceImpl implements SpecialtyService {

    private static final long serialVersionUID = 6145428275502469961L;

    @EJB
    private SpecialtyDao specialtyDao;

    @Override
    public List<Specialty> getAll() {
        return this.specialtyDao.getAll();
    }

    @Override
    public Specialty findById(long id) {
        log.info("findById Specialty: " + id);
        return this.specialtyDao.findById(id);
    }

    @Override
    public Specialty addNew(Specialty specialty) {
        log.info("addNew Specialty: " + specialty.toString());
        return this.specialtyDao.addNew(specialty);
    }

    @Override
    public Specialty update(Specialty specialty) {
        log.info("update Specialty: " + specialty.toString());
        return this.specialtyDao.update(specialty);
    }

    @Override
    public void delete(long id) {
        log.info("delete Specialty: " + id);
        this.specialtyDao.delete(id);
    }

    @Override
    public List<Specialty> search(String searchterm) {
        log.info("search Specialty: " + searchterm);
        return this.specialtyDao.search(searchterm);
    }

    @Override
    public void resetSearchIndex() {
        for(Specialty s:getAll()){
            this.specialtyDao.update(s);
        }
    }

    @Override
    public Specialty findSpecialtyByName(String name) {
        return this.specialtyDao.findSpecialtyByName(name);
    }


    @PostConstruct
    public void postConstruct() {
        log.info("postConstruct: "+SpecialtyServiceImpl.class.getSimpleName());
    }

    @PreDestroy
    public void preDestroy() {
        log.info("preDestroy: "+SpecialtyServiceImpl.class.getSimpleName());
    }
}
