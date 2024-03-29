package org.woehlke.jakartaee.petclinic.pettype.db;

import org.woehlke.jakartaee.petclinic.application.framework.db.CrudService;
import org.woehlke.jakartaee.petclinic.application.framework.db.SearchableService;
import org.woehlke.jakartaee.petclinic.pettype.PetType;

import java.io.Serializable;

/**
 *
 */
public interface PetTypeService extends CrudService<PetType>, SearchableService<PetType> {

    long serialVersionUID = 6637453269836393L;

    PetType findByName(String name);
}
