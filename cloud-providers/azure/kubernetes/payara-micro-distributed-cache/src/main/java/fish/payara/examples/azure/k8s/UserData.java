package fish.payara.examples.azure.k8s;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class UserData implements Serializable {

    private static final long serialVersionUID = 1024713371902278434L;

    private Integer id;

    @NotNull
    @NotEmpty
    private String name;

    @NotNull
    @NotEmpty
    private String organization;

    private String createdOn;

    @JsonbCreator
    public UserData(@JsonbProperty("name") String name, @JsonbProperty("organization") String organization) {
        this.name = name;
        this.organization = organization;
    }

    public UserData(Integer id, UserData data, String createdOn) {
        this.id = id;
        this.name = data.name;
        this.organization = data.organization;
        this.createdOn = createdOn;
    }

    @JsonbProperty("id")
    public Integer getId() {
        return id;
    }

    @JsonbProperty("name")
    public String getName() {
        return name;
    }

    @JsonbProperty("organization")
    public String getOrganization() {
        return organization;
    }

    @JsonbProperty("createdOnInstance")
    public String getCreatedOn() {
        return createdOn;
    }
}

