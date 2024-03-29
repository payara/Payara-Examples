package org.woehlke.jakartaee.petclinic.pet.db;

import jakarta.validation.constraints.NotNull;
import org.woehlke.jakartaee.petclinic.application.framework.db.CrudService;
import org.woehlke.jakartaee.petclinic.owner.Owner;
import org.woehlke.jakartaee.petclinic.pet.Pet;
import org.woehlke.jakartaee.petclinic.visit.Visit;

import java.io.Serializable;
import java.util.List;


/**
 *
 */
public interface PetService extends CrudService<Pet>, Serializable {

    long serialVersionUID = 7113444329343577727L;

    List<Visit> getVisits(@NotNull Pet pet);

    List<Pet> getAllPetsOfAnOwner(@NotNull Owner entity);

}
