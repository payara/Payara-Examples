package org.woehlke.jakartaee.petclinic.pettype.api;

import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.*;

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
@XmlRootElement(name = "petTypeList")
public class PetTypeListDto implements Serializable {

    private static final long serialVersionUID = -5455359835551484530L;

    @JsonbProperty
    private List<PetTypeDto> petType = new ArrayList<>();

}
