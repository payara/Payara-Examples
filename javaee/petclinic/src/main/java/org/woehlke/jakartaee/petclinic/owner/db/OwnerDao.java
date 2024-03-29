package org.woehlke.jakartaee.petclinic.owner.db;

import org.woehlke.jakartaee.petclinic.application.framework.db.CrudDao;
import org.woehlke.jakartaee.petclinic.application.framework.SearchableEntity;
import org.woehlke.jakartaee.petclinic.owner.Owner;

/**
 * Created with IntelliJ IDEA.
 * User: tw
 * Date: 06.01.14
 * Time: 09:38
 * To change this template use File | Settings | File Templates.
 */
public interface OwnerDao extends CrudDao<Owner>, SearchableEntity<Owner> {

    long serialVersionUID = 4561420558388982124L;

}
