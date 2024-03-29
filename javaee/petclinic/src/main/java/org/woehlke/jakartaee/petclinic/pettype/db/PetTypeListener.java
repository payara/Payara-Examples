package org.woehlke.jakartaee.petclinic.pettype.db;


import lombok.extern.java.Log;
import org.woehlke.jakartaee.petclinic.pettype.PetType;

import jakarta.persistence.*;

import java.io.Serializable;

import static org.woehlke.jakartaee.petclinic.application.framework.db.EntityListenerLogger.logIt;

/**
 *
 */
@Log
public class PetTypeListener implements Serializable {

    private static final long serialVersionUID = 8419288369702922659L;

    @PrePersist
    public void onPrePersist(Object domainObject) {

        boolean correctType = domainObject instanceof PetType;
        logIt("try to Persist: ", (PetType) domainObject);
    }

    @PreUpdate
    public void onPreUpdate(Object domainObject) {

        boolean correctType = domainObject instanceof PetType;
        logIt("try to Update: ", (PetType) domainObject);
    }

    @PreRemove
    public void onPreRemove(Object domainObject) {

        boolean correctType = domainObject instanceof PetType;
        logIt("try to Remove: ", (PetType) domainObject);
    }

    @PostPersist
    public void onPostPersist(Object domainObject) {

        boolean correctType = domainObject instanceof PetType;
        logIt("Persisted: ", (PetType) domainObject);
    }

    @PostUpdate
    public void onPostUpdate(Object domainObject) {

        boolean correctType = domainObject instanceof PetType;
        logIt("Updated: ", (PetType) domainObject);
    }

    @PostRemove
    public void onPostRemove(Object domainObject) {

        boolean correctType = domainObject instanceof PetType;
        logIt("Removed: ", (PetType) domainObject);
    }

    @PostLoad
    public void onPostLoad(Object domainObject) {

        boolean correctType = domainObject instanceof PetType;
        logIt("Loaded: ", (PetType) domainObject);
    }

}
