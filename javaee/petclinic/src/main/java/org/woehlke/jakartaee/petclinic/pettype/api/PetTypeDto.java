package org.woehlke.jakartaee.petclinic.pettype.api;


import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.*;


import java.io.Serializable;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: tw
 * Date: 01.01.14
 * Time: 21:12
 * To change this template use File | Settings | File Templates.
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "petType")
public class PetTypeDto implements Serializable {

    private static final long serialVersionUID = -2213412509142145275L;

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
