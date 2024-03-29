package org.woehlke.jakartaee.petclinic.visit.api;

import jakarta.json.bind.annotation.JsonbDateFormat;
import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import jakarta.xml.bind.annotation.*;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

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
@XmlRootElement(name = "visit")
public class VisitDto implements Serializable {

    private static final long serialVersionUID = 2357446696894656827L;

    @NotNull
    @JsonbProperty
    private Long id;

    @NotBlank
    @JsonbProperty
    private UUID uuid;

    @NotNull
    @JsonbProperty
    @JsonbDateFormat(value = "yyyy-MM-dd")
    protected Date date;

    @NotBlank
    @JsonbProperty
    private String description;

}
