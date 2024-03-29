package org.woehlke.jakartaee.petclinic.application.framework;

import jakarta.persistence.Transient;
import java.util.UUID;

/**
 *
 */
public interface EntityBase {

    Long getId();

    UUID getUuid();

    @Transient
    String getTableName();

    @Transient
    String getPrimaryKey();

    @Transient
    String getPrimaryKeyWithId();

    void updateSearchindex();
}
