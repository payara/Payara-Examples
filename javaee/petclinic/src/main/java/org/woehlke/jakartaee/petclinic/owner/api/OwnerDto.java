package org.woehlke.jakartaee.petclinic.owner.api;

import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.validation.constraints.*;
import lombok.*;

import jakarta.xml.bind.annotation.*;
import org.woehlke.jakartaee.petclinic.pet.api.PetListDto;

import java.io.Serializable;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: tw
 * Date: 01.01.14
 * Time: 21:08
 * To change this template use File | Settings | File Templates.
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "owner")
public class OwnerDto implements Serializable {

    private static final long serialVersionUID = 7995827646591579744L;

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

    @NotEmpty
    @JsonbProperty
    private String address;

    @NotEmpty
    @JsonbProperty
    private String houseNumber;

    @NotEmpty
    @JsonbProperty
    private String addressInfo;

    @NotEmpty
    @JsonbProperty
    private String city;

    @NotEmpty
    @JsonbProperty
    @Digits(fraction = 0, integer = 5)
    private String zipCode;

    @NotEmpty
    @JsonbProperty
    @Pattern(regexp = "\\+[1-9][0-9]{9,14}", message = "{invalid.phoneNumber}")
    private String phoneNumber;

    @JsonbProperty
    private PetListDto petList = new PetListDto();

}
