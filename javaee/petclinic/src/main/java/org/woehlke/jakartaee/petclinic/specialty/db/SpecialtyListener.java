package org.woehlke.jakartaee.petclinic.specialty.db;

import lombok.extern.java.Log;
import org.woehlke.jakartaee.petclinic.specialty.Specialty;

import jakarta.persistence.*;

import java.io.Serializable;

import static org.woehlke.jakartaee.petclinic.application.framework.db.EntityListenerLogger.logIt;

@Log
public class SpecialtyListener implements Serializable {

    private static final long serialVersionUID = -8293962022644297603L;

    @PrePersist
    public void onPrePersist(Object domainObject) {

        boolean correctType = domainObject instanceof Specialty;
        logIt("try to Persist: ", (Specialty) domainObject);
    }

    @PreUpdate
    public void onPreUpdate(Object domainObject) {

        boolean correctType = domainObject instanceof Specialty;
        logIt("try to Update: ", (Specialty) domainObject);
    }

    @PreRemove
    public void onPreRemove(Object domainObject) {

        boolean correctType = domainObject instanceof Specialty;
        logIt("try to Remove: ", (Specialty) domainObject);
    }

    @PostPersist
    public void onPostPersist(Object domainObject) {

        boolean correctType = domainObject instanceof Specialty;
        logIt("Persisted: ", (Specialty) domainObject);
    }

    @PostUpdate
    public void onPostUpdate(Object domainObject) {

        boolean correctType = domainObject instanceof Specialty;
        logIt("Updated: ", (Specialty) domainObject);
    }

    @PostRemove
    public void onPostRemove(Object domainObject) {

        boolean correctType = domainObject instanceof Specialty;
        logIt("Removed: ", (Specialty) domainObject);
    }

    @PostLoad
    public void onPostLoad(Object domainObject) {

        boolean correctType = domainObject instanceof Specialty;
        logIt("Loaded: ", (Specialty) domainObject);
    }

}
