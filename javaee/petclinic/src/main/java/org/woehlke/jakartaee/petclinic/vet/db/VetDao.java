package org.woehlke.jakartaee.petclinic.vet.db;

import org.woehlke.jakartaee.petclinic.application.framework.db.CrudDao;
import org.woehlke.jakartaee.petclinic.application.framework.SearchableEntity;
import org.woehlke.jakartaee.petclinic.vet.Vet;

/**
 * Created with IntelliJ IDEA.
 * User: tw
 * Date: 02.01.14
 * Time: 08:37
 * To change this template use File | Settings | File Templates.
 */
public interface VetDao extends CrudDao<Vet>, SearchableEntity<Vet> {

    long serialVersionUID = -8002507178196926932L;
}
