package org.woehlke.jakartaee.petclinic.owner.api;

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
@XmlRootElement(name = "ownerList")
public class OwnerListDto implements Serializable {

    private static final long serialVersionUID = 7608980315748812643L;

    @JsonbProperty
    private List<OwnerDto> owner = new ArrayList<>();

}
