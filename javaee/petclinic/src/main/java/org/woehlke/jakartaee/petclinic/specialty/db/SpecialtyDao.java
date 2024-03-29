package org.woehlke.jakartaee.petclinic.specialty.db;

import jakarta.validation.constraints.NotNull;
import org.woehlke.jakartaee.petclinic.application.framework.db.CrudDao;
import org.woehlke.jakartaee.petclinic.application.framework.SearchableEntity;
import org.woehlke.jakartaee.petclinic.specialty.Specialty;

/**
 * Created with IntelliJ IDEA.
 * User: tw
 * Date: 04.01.14
 * Time: 12:02
 * To change this template use File | Settings | File Templates.
 */
public interface SpecialtyDao extends CrudDao<Specialty>, SearchableEntity<Specialty> {

    long serialVersionUID = -3942585068863132460L;

    Specialty findSpecialtyByName(@NotNull String name);
}
