package org.woehlke.jakartaee.petclinic.pet.db;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.java.Log;
import org.woehlke.jakartaee.petclinic.owner.Owner;
import org.woehlke.jakartaee.petclinic.pet.Pet;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.ejb.Stateless;
import jakarta.persistence.TypedQuery;
import org.woehlke.jakartaee.petclinic.visit.Visit;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: tw
 * Date: 06.01.14
 * Time: 20:34
 * To change this template use File | Settings | File Templates.
 */
@Log
@Stateless
public class PetDaoImpl implements PetDao, Serializable {

    private static final long serialVersionUID = -9149391932558758266L;

    @PersistenceContext(unitName = PERSISTENCE_UNIT_NAME)
    private EntityManager entityManager;

    @Override
    public List<Pet> getPetsAsList(Owner owner) {
        TypedQuery<Pet> q = entityManager.createNamedQuery("Pet.getPetsAsList", Pet.class);
        q.setParameter("owner",owner);
        return q.getResultList();
    }

    @Override
    public List<Visit> getVisits(Pet pet) {
        TypedQuery<Visit> q = entityManager.createNamedQuery( "Visit.getVisits", Visit.class);
        q.setParameter("pet",pet);
        return q.getResultList();
    }

    @Override
    public Pet addNew(Pet pet) {
        pet.setUuid(UUID.randomUUID());
        //pet.updateSearchindex(); TODO
        log.info("transient New Pet: " + pet.toString());
        entityManager.persist(pet);
        log.info("persistent New Pet: " + pet.toString());
        return pet;
    }

    @Override
    public List<Pet> getAll() {
        TypedQuery<Pet> q = entityManager.createNamedQuery("Pet.getAll", Pet.class);
        return q.getResultList();
    }

    @Override
    public Pet findById(long petId) {
        return entityManager.find(Pet.class, petId);
    }

    @Override
    public Pet update(Pet pet) {
        //pet.updateSearchindex(); TODO
        log.info("update Pet: " + pet.toString());
        return entityManager.merge(pet);
    }

    @Override
    public void delete(long id) {
        Pet p = this.findById(id);
        log.info("delete Pet: " + p.toString());
        entityManager.remove(p);
    }

    @PostConstruct
    public void postConstruct() {
        log.info("postConstruct: "+PetDaoImpl.class.getSimpleName());
    }

    @PreDestroy
    public void preDestroy() {
        log.info("preDestroy: "+PetDaoImpl.class.getSimpleName());
    }

}
