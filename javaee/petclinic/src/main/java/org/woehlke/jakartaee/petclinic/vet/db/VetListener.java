package org.woehlke.jakartaee.petclinic.vet.db;

import lombok.extern.java.Log;
import jakarta.persistence.*;
import org.woehlke.jakartaee.petclinic.vet.Vet;

import java.io.Serializable;

import static org.woehlke.jakartaee.petclinic.application.framework.db.EntityListenerLogger.logIt;

/**
 *
 */
@Log
public class VetListener implements Serializable {

    private static final long serialVersionUID = 1624676127692597414L;

    @PrePersist
    public void onPrePersist(Object domainObject) {
        boolean correctType = domainObject instanceof Vet;
        logIt("try to Persist: ", (Vet) domainObject);
    }

    @PreUpdate
    public void onPreUpdate(Object domainObject) {

        boolean correctType = domainObject instanceof Vet;
        logIt("try to Update: ", (Vet) domainObject);
    }

    @PreRemove
    public void onPreRemove(Object domainObject) {

        boolean correctType = domainObject instanceof Vet;
        logIt("try to Remove: ", (Vet) domainObject);
    }

    @PostPersist
    public void onPostPersist(Object domainObject) {

        boolean correctType = domainObject instanceof Vet;
        logIt("Persisted: ", (Vet) domainObject);
    }

    @PostUpdate
    public void onPostUpdate(Object domainObject) {

        boolean correctType = domainObject instanceof Vet;
        logIt("Updated: ", (Vet) domainObject);
    }

    @PostRemove
    public void onPostRemove(Object domainObject) {

        boolean correctType = domainObject instanceof Vet;
        logIt("Removed: ", (Vet) domainObject);
    }

    @PostLoad
    public void onPostLoad(Object domainObject) {

        boolean correctType = domainObject instanceof Vet;
        logIt("Loaded: ", (Vet) domainObject);
    }

}
