package org.woehlke.jakartaee.petclinic.visit.views;

import org.woehlke.jakartaee.petclinic.pet.Pet;
import org.woehlke.jakartaee.petclinic.visit.Visit;

import java.io.Serializable;

/**
 *
 */
public interface OwnersPetVisitView extends Serializable {

    long serialVersionUID = 2400107254778567823L;

    String editOwnerPetVisitNewForm(Pet rowPet);
    String cancelOwnerPetVisitNew();
    String saveOwnerPetVisitNew(Pet rowPet);

    Visit getVisit();
    void setVisit(Visit visit);
}
