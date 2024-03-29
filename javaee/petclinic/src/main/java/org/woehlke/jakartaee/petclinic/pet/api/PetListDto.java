package org.woehlke.jakartaee.petclinic.pet.api;

import jakarta.json.bind.annotation.JsonbProperty;
import lombok.*;

import jakarta.xml.bind.annotation.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "petList")
public class PetListDto implements Serializable {

    private static final long serialVersionUID = -5829352711555277375L;

    @JsonbProperty("pet")
    private List<PetDto> pet = new ArrayList<>();

}
