package org.woehlke.jakartaee.petclinic.visit.db;


import lombok.extern.java.Log;
import org.woehlke.jakartaee.petclinic.visit.Visit;

import jakarta.persistence.*;

import java.io.Serializable;

import static org.woehlke.jakartaee.petclinic.application.framework.db.EntityListenerLogger.logIt;

/**
 *
 */
@Log
public class VisitListener implements Serializable {

    private static final long serialVersionUID = -8346649868905401057L;

    @PrePersist
    public void onPrePersist(Object domainObject) {
        boolean correctType = domainObject instanceof Visit;

        logIt("try to Persist: ", (Visit) domainObject);
    }

    @PreUpdate
    public void onPreUpdate(Object domainObject) {

        boolean correctType = domainObject instanceof Visit;
        logIt("try to Update: ", (Visit) domainObject);
    }

    @PreRemove
    public void onPreRemove(Object domainObject) {

        boolean correctType = domainObject instanceof Visit;
        logIt("try to Remove: ", (Visit) domainObject);
    }

    @PostPersist
    public void onPostPersist(Object domainObject) {

        boolean correctType = domainObject instanceof Visit;
        logIt("Persisted: ", (Visit) domainObject);
    }

    @PostUpdate
    public void onPostUpdate(Object domainObject) {

        boolean correctType = domainObject instanceof Visit;
        logIt("Updated: ", (Visit) domainObject);
    }

    @PostRemove
    public void onPostRemove(Object domainObject) {

        boolean correctType = domainObject instanceof Visit;
        logIt("Removed: ", (Visit) domainObject);
    }

    @PostLoad
    public void onPostLoad(Object domainObject) {

        boolean correctType = domainObject instanceof Visit;
        logIt("Loaded: ", (Visit) domainObject);
    }

}
