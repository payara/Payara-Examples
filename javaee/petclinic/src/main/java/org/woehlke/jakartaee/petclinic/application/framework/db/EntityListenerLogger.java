package org.woehlke.jakartaee.petclinic.application.framework.db;

import lombok.extern.java.Log;
import org.woehlke.jakartaee.petclinic.application.framework.EntityBaseObject;

import java.io.Serializable;

/**
 *
 */
@Log
public class EntityListenerLogger implements Serializable {

    private static final long serialVersionUID = -6345160390628557648L;

    public static void logIt(String event, EntityBaseObject domainObject) {
        String[] infos = {
                event + " TableName  " + domainObject.getTableName(),
                //event + " PrimaryKey " + domainObject.getPrimaryKey(),
                event + " Id         " + domainObject.getId(),
                event + " Uuid       " + domainObject.getUuid(),
                event + " toString   " + domainObject.toString()
        };
        for (String info : infos) {
            log.fine(info);
        }

    }
}
