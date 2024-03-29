package org.woehlke.jakartaee.petclinic.vet.api;

import jakarta.json.bind.annotation.JsonbProperty;
import lombok.*;

import jakarta.xml.bind.annotation.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: tw
 * Date: 01.01.14
 * Time: 21:37
 * To change this template use File | Settings | File Templates.
 */
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "VetList")
public class VetListDto implements Serializable {

    private static final long serialVersionUID = 6396791677094922721L;

    @JsonbProperty
    private List<VetDto> vetList = new ArrayList<>();

}
