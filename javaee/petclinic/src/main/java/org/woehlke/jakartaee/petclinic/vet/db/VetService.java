package org.woehlke.jakartaee.petclinic.vet.db;

import org.woehlke.jakartaee.petclinic.application.framework.db.CrudService;
import org.woehlke.jakartaee.petclinic.application.framework.db.SearchableService;
import org.woehlke.jakartaee.petclinic.vet.Vet;

import java.io.Serializable;

public interface VetService extends CrudService<Vet>, SearchableService<Vet> {
    long serialVersionUID = 6211608636423556157L;
}
