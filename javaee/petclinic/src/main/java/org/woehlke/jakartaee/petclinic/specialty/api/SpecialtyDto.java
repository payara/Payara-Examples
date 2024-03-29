package org.woehlke.jakartaee.petclinic.specialty.api;


import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import jakarta.xml.bind.annotation.*;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: tw
 * Date: 01.01.14
 * Time: 21:11
 * To change this template use File | Settings | File Templates.
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "specialty")
public class SpecialtyDto implements Serializable {

    private static final long serialVersionUID = -836560513920170089L;

    @NotNull
    @JsonbProperty
    private Long id;

    @NotBlank
    @JsonbProperty
    private UUID uuid;

    @NotEmpty
    @JsonbProperty
    private String name;

}
