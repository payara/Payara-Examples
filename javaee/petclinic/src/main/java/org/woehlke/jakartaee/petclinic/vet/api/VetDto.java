package org.woehlke.jakartaee.petclinic.vet.api;


import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import jakarta.xml.bind.annotation.*;
import org.woehlke.jakartaee.petclinic.specialty.api.SpecialtyListDto;

import java.io.Serializable;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: tw
 * Date: 01.01.14
 * Time: 21:10
 * To change this template use File | Settings | File Templates.
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "Vet")
public class VetDto implements Serializable {

    private static final long serialVersionUID = 6749793465861123385L;

    @NotNull
    @JsonbProperty
    private Long id;

    @NotBlank
    @JsonbProperty
    private UUID uuid;

    @NotEmpty
    @JsonbProperty
    private String firstName;

    @NotEmpty
    @JsonbProperty
    private String lastName;

    @NotNull
    @JsonbProperty
    private SpecialtyListDto specialtyList = new SpecialtyListDto();

}
