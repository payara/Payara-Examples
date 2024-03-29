package org.woehlke.jakartaee.petclinic.owner.db;


import jakarta.ejb.Stateless;
import lombok.extern.java.Log;
import org.woehlke.jakartaee.petclinic.owner.Owner;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.io.Serializable;
import java.util.*;


/**
 * Created with IntelliJ IDEA.
 * User: tw
 * Date: 06.01.14
 * Time: 09:38
 * To change this template use File | Settings | File Templates.
 */
@Log
@Stateless
public class OwnerDaoImpl implements OwnerDao, Serializable {

    private static final long serialVersionUID = 1313423542L;

    @PersistenceContext(unitName = PERSISTENCE_UNIT_NAME)
    private EntityManager entityManager;

    @Override
    public List<Owner> getAll() {
        TypedQuery<Owner> q = entityManager.createNamedQuery("Owner.getAll", Owner.class);
        return q.getResultList();
    }

    @Override
    public void delete(long id) {
        Owner owner = entityManager.find(Owner.class, id);
        log.info("delete Owner: " + owner.toString());
        entityManager.remove(owner);
    }

    @Override
    public Owner addNew(Owner owner) {
        owner.setUuid(UUID.randomUUID());
        //owner.updateSearchindex(); TODO
        log.info("addNew Owner: " + owner.toString());
        entityManager.persist(owner);
        return owner;
    }

    @Override
    public Owner findById(long id) {
        return entityManager.find(Owner.class, id);
    }

    @Override
    public Owner update(Owner owner) {
        //owner.updateSearchindex(); TODO
        log.info("update Owner: " + owner.toString());
        return entityManager.merge(owner);
    }

    @Override
    public List<Owner> search(String searchterm) {
        log.info("search Owner: " + searchterm);
        TypedQuery<Owner> q = entityManager.createNamedQuery("Owner.search", Owner.class);
        q.setParameter("searchterm", "%" + searchterm + "%");
        List<Owner> list = q.getResultList();
        return list;
    }

    @Override
    public void resetSearchIndex() {

    }

    @PostConstruct
    public void postConstruct() {
        log.info("postConstruct: "+OwnerDaoImpl.class.getSimpleName());
    }

    @PreDestroy
    public void preDestroy() {
        log.info("preDestroy: "+OwnerDaoImpl.class.getSimpleName());
    }
}
