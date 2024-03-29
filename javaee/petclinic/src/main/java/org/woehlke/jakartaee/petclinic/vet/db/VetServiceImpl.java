package org.woehlke.jakartaee.petclinic.vet.db;

import lombok.extern.java.Log;
import org.woehlke.jakartaee.petclinic.specialty.Specialty;
import org.woehlke.jakartaee.petclinic.specialty.db.SpecialtyDao;
import org.woehlke.jakartaee.petclinic.vet.Vet;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;


/**
 *
 */
@Log
@Stateless
public class VetServiceImpl implements VetService, Serializable {

    private static final long serialVersionUID = 2698313227542867286L;

    @EJB
    private VetDao vetDao;

    @EJB
    private SpecialtyDao specialtyDao;

    @Override
    public List<Vet> getAll() {
        return vetDao.getAll();
    }

    @Override
    public Vet findById(long id) {
        return this.vetDao.findById(id);
    }

    @Override
    public void delete(long id) {
        log.info("update Vet: " + id);
        this.vetDao.delete(id);
    }

    @Override
    public Vet addNew(Vet vet) {
        vet.setUuid(UUID.randomUUID());
        log.info("try to addNew: " + vet.toString());
        return this.vetDao.addNew(vet);
    }

    @Override
    public Vet update(Vet vet) {
        log.info("update Vet: " + vet.toString());
        return this.vetDao.update(vet);
    }

    @Override
    public List<Vet> search(String searchterm) {
        log.info("search: " + searchterm);
        return this.vetDao.search(searchterm);
    }

    @Override
    public void resetSearchIndex() {
        for(Vet v: getAll()){
            for(Specialty s:v.getSpecialties()){
                this.specialtyDao.update(s);
            }
            this.vetDao.update(v);
        }
    }
    @PostConstruct
    public void postConstruct() {
        log.info("postConstruct: "+VetServiceImpl.class.getCanonicalName() );
    }

    @PreDestroy
    public void preDestroy() {
        log.info("preDestroy: "+VetServiceImpl.class.getCanonicalName() );
    }
}
