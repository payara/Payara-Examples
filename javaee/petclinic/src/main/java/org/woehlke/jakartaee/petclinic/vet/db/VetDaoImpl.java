package org.woehlke.jakartaee.petclinic.vet.db;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.java.Log;
import org.woehlke.jakartaee.petclinic.vet.Vet;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.ejb.PostActivate;
import jakarta.ejb.PrePassivate;
import jakarta.ejb.Stateless;
import jakarta.persistence.TypedQuery;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: tw
 * Date: 02.01.14
 * Time: 08:30
 * To change this template use File | Settings | File Templates.
 */
@Log
@Stateless
public class VetDaoImpl implements VetDao, Serializable {

    private static final long serialVersionUID = -1003870150408928198L;

    @PersistenceContext(unitName = PERSISTENCE_UNIT_NAME)
    private EntityManager entityManager;

    @Override
    public List<Vet> getAll() {
        TypedQuery<Vet> q = entityManager.createNamedQuery("Vet.getAll", Vet.class);
        List<Vet> list = q.getResultList();
        return list;
    }

    @Override
    public Vet findById(long id) {
        Vet vet = entityManager.find(Vet.class, id);
        return vet;
    }

    @Override
    public void delete(long id) {
        Vet vet = entityManager.find(Vet.class, id);
        log.info("delete Vet: " + vet.toString());
        entityManager.remove(vet);
    }

    @Override
    public Vet addNew(Vet vet) {
        vet.setUuid(UUID.randomUUID());
        //vet.updateSearchindex(); TODO
        log.info("addNew Vet: " + vet.toString());
        entityManager.persist(vet);
        log.info("addded New Vet: " + vet.toString());
        return vet;
    }

    @Override
    public Vet update(Vet vet) {
        vet.updateSearchindex();
        log.info("update Vet: " + vet.toString());
        return entityManager.merge(vet);
    }

    @Override
    public List<Vet> search(String searchterm) {
        log.info("search Vet: " + searchterm);
        TypedQuery<Vet> q = entityManager.createNamedQuery("Vet.search", Vet.class);
        q.setParameter("searchterm", "%" + searchterm + "%");
        List<Vet> list = q.getResultList();
        return list;
    }

    @Override
    public void resetSearchIndex() {
        log.info("resetSearchIndex Vet");
    }


    @PostConstruct
    public void postConstruct() {
        log.info("postConstruct: "+VetDaoImpl.class.getSimpleName());
    }

    @PreDestroy
    public void preDestroy() {
        log.info("preDestroy: "+VetDaoImpl.class.getSimpleName());
    }
}
