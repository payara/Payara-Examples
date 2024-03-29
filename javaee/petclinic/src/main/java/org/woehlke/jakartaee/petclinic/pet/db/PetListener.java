package org.woehlke.jakartaee.petclinic.pet.db;

import lombok.extern.java.Log;
import org.woehlke.jakartaee.petclinic.pet.Pet;

import jakarta.persistence.*;

import java.io.Serializable;

import static org.woehlke.jakartaee.petclinic.application.framework.db.EntityListenerLogger.logIt;

/**
 *
 */
@Log
public class PetListener implements Serializable {

    private static final long serialVersionUID = 2648243165481283491L;

    @PrePersist
    public void onPrePersist(Object domainObject) {

        boolean correctType = domainObject instanceof Pet;
        logIt("try to Persist: ", (Pet) domainObject);
    }

    @PreUpdate
    public void onPreUpdate(Object domainObject) {

        boolean correctType = domainObject instanceof Pet;
        logIt("try to Update: ", (Pet) domainObject);
    }

    @PreRemove
    public void onPreRemove(Object domainObject) {

        boolean correctType = domainObject instanceof Pet;
        logIt("try to Remove: ", (Pet) domainObject);
    }

    @PostPersist
    public void onPostPersist(Object domainObject) {

        boolean correctType = domainObject instanceof Pet;
        logIt("Persisted: ", (Pet) domainObject);
    }

    @PostUpdate
    public void onPostUpdate(Object domainObject) {

        boolean correctType = domainObject instanceof Pet;
        logIt("Updated: ", (Pet) domainObject);
    }

    @PostRemove
    public void onPostRemove(Object domainObject) {

        boolean correctType = domainObject instanceof Pet;
        logIt("Removed: ", (Pet) domainObject);
    }

    @PostLoad
    public void onPostLoad(Object domainObject) {

        boolean correctType = domainObject instanceof Pet;
        logIt("Loaded: ", (Pet) domainObject);
    }

}
