package org.woehlke.jakartaee.petclinic.pet.db;

import jakarta.validation.constraints.NotNull;
import org.woehlke.jakartaee.petclinic.application.framework.db.CrudDao;
import org.woehlke.jakartaee.petclinic.owner.Owner;
import org.woehlke.jakartaee.petclinic.pet.Pet;
import org.woehlke.jakartaee.petclinic.visit.Visit;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: tw
 * Date: 06.01.14
 * Time: 20:34
 * To change this template use File | Settings | File Templates.
 */
public interface PetDao extends CrudDao<Pet> {
    long serialVersionUID = -7894365380280920804L;

    List<Pet> getPetsAsList(@NotNull Owner owner);

    List<Visit> getVisits(@NotNull Pet pet);

}
