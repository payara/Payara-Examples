package org.woehlke.jakartaee.petclinic.visit.api;

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
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "visitList")
public class VisitListDto implements Serializable {

    private static final long serialVersionUID = -7588305041391798453L;

    @JsonbProperty("visit")
    private List<VisitDto> visit = new ArrayList<>();

}
