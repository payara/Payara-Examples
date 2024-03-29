package org.woehlke.jakartaee.petclinic.application.framework;

import java.io.Serializable;
import java.util.UUID;

/**
 *
 */
public abstract class EntityBaseObject extends Object implements EntityBase, Serializable {

    private static final long serialVersionUID = -3378330831315654285L;

    @Override
    public abstract Long getId();

    @Override
    public abstract UUID getUuid();

    @Override
    public abstract String getTableName();

    @Override
    public abstract String getPrimaryKey();

    @Override
    public abstract String getPrimaryKeyWithId();

}
