package org.woehlke.jakartaee.petclinic.pet.views;

import org.woehlke.jakartaee.petclinic.pet.Pet;
import org.woehlke.jakartaee.petclinic.pettype.PetType;

import java.io.Serializable;
import java.util.List;

public interface OwnersPetView extends Serializable {

    long serialVersionUID = 889970231263134104L;

    String showOwnerPetNewForm();
    String cancelOwnerPetNew();
    String saveOwnerPetNew();

    String showOwnerPetEditForm(Pet pet);
    String cancelOwnerPetEdit();
    String saveOwnerPetEdit();

    Pet getPet();
    void setPet(Pet pet);

    long getPetTypeId();
    void setPetTypeId(long petTypeId);

    List<PetType> getPetTypeList();
    void setPetTypeList(List<PetType> petTypeList);

    List<PetType> getAllPetTypes();
}
