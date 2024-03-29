package org.woehlke.jakartaee.petclinic.specialty.api;

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
@XmlRootElement(name = "specialtyList")
public class SpecialtyListDto implements Serializable {

    private static final long serialVersionUID = -4974070594228297652L;

    @JsonbProperty
    private List<SpecialtyDto> specialty = new ArrayList<>();

}
