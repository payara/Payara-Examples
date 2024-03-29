package org.woehlke.jakartaee.petclinic.owner.db;


import lombok.extern.java.Log;
import org.woehlke.jakartaee.petclinic.owner.Owner;

import jakarta.persistence.*;

import java.io.Serializable;

import static org.woehlke.jakartaee.petclinic.application.framework.db.EntityListenerLogger.logIt;

/**
 *
 */
@Log
public class OwnerListener implements Serializable {

    private static final long serialVersionUID = 5882887419196240305L;

    @PrePersist
    public void onPrePersist(Object domainObject) {
        boolean correctType = domainObject instanceof Owner;
        logIt("try to Persist: ", (Owner) domainObject);
    }

    @PreUpdate
    public void onPreUpdate(Object domainObject) {

        boolean correctType = domainObject instanceof Owner;
        logIt("try to Update: ", (Owner) domainObject);
    }

    @PreRemove
    public void onPreRemove(Object domainObject) {

        boolean correctType = domainObject instanceof Owner;
        logIt("try to Remove: ", (Owner) domainObject);
    }

    @PostPersist
    public void onPostPersist(Object domainObject) {

        boolean correctType = domainObject instanceof Owner;
        logIt("Persisted: ", (Owner) domainObject);
    }

    @PostUpdate
    public void onPostUpdate(Object domainObject) {

        boolean correctType = domainObject instanceof Owner;
        logIt("Updated: ", (Owner) domainObject);
    }

    @PostRemove
    public void onPostRemove(Object domainObject) {

        boolean correctType = domainObject instanceof Owner;
        logIt("Removed: ", (Owner) domainObject);
    }

    @PostLoad
    public void onPostLoad(Object domainObject) {

        boolean correctType = domainObject instanceof Owner;
        logIt("Loaded: ", (Owner) domainObject);
    }

}
