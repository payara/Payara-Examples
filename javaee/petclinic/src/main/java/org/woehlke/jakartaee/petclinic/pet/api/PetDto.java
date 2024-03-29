package org.woehlke.jakartaee.petclinic.pet.api;

import jakarta.json.bind.annotation.JsonbDateFormat;
import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.woehlke.jakartaee.petclinic.pettype.api.PetTypeDto;

import jakarta.xml.bind.annotation.*;
import org.woehlke.jakartaee.petclinic.visit.api.VisitListDto;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;


/**
 * Created with IntelliJ IDEA.
 * User: tw
 * Date: 01.01.14
 * Time: 21:09
 * To change this template use File | Settings | File Templates.
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "pet")
public class PetDto implements Serializable {

    private static final long serialVersionUID = 1007513582768464905L;

    @NotNull
    @JsonbProperty
    private Long id;

    @NotBlank
    @JsonbProperty
    private UUID uuid;

    @NotEmpty
    @JsonbProperty
    private String name;

    @NotNull
    @JsonbProperty
    @JsonbDateFormat(value = "yyyy-MM-dd")
    private Date birthDate;

    @NotNull
    @JsonbProperty
    private PetTypeDto petType;

    @NotNull
    @JsonbProperty("visitList")
    private VisitListDto visitList = new VisitListDto();

}
