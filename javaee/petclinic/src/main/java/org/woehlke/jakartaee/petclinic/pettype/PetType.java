package org.woehlke.jakartaee.petclinic.pettype;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.woehlke.jakartaee.petclinic.application.framework.EntityBase;
import org.woehlke.jakartaee.petclinic.application.framework.EntityBaseObject;
import org.woehlke.jakartaee.petclinic.pettype.db.PetTypeListener;

import jakarta.persistence.*;
import org.woehlke.jakartaee.petclinic.specialty.Specialty;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: tw
 * Date: 01.01.14
 * Time: 21:12
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = PetType.TABLENAME,
        uniqueConstraints = {
                @UniqueConstraint(
                        name = PetType.TABLENAME + "_unique_uuid",
                        columnNames = {PetType.COL_UUID}
                ),
                @UniqueConstraint(
                        name = PetType.TABLENAME + "_unique_names",
                        columnNames = {PetType.COL_NAME}
                )
        }
)
@NamedQueries({
        @NamedQuery(
                name = "PetType.getAll",
                query = "select p from PetType p order by p.name ASC"
        ),
        @NamedQuery(
                name = "PetType.findByName",
                query = "select s from PetType s where s.name=:name"
        ),
        @NamedQuery(
                name = "PetType.search",
                query = "select v from PetType v where v.searchindex like :searchterm order by v.name ASC"
        )
})
@EntityListeners(PetTypeListener.class)
public class PetType extends EntityBaseObject implements EntityBase, Comparable<PetType>, Serializable {

    public final static String TABLENAME = "owner_pet_pettype";
    public final static String COL_ID = "id";
    public final static String COL_UUID = "uuid";
    public final static String COL_NAME = "name";
    public final static String COL_SEARCHINDEX = "searchindex";
    private static final long serialVersionUID = -2213412509142145275L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "owner_pet_pettype_gen")
    @SequenceGenerator(name="owner_pet_pettype_gen", sequenceName="owner_pet_pettype_seq")
    private Long id;

    @Column(name = COL_UUID, nullable = false, unique = true, length = 36)
    private UUID uuid;

    @Column(name = COL_SEARCHINDEX, nullable = true)
    private String searchindex;

    @NotEmpty
    @Column(name = COL_NAME, nullable = false, unique = true)
    private String name;

    public static PetType newEntity(){
        PetType p = new PetType();
        p.uuid = UUID.randomUUID();
        p.searchindex = "";
        p.name = "";
        return p;
    }

    public static PetType newEntity(@NotBlank String name){
        PetType p = new PetType();
        p.uuid = UUID.randomUUID();
        p.searchindex = "";
        p.name = name;
        return p;
    }

    public void updateSearchindex() {
        String[] element = this.getName().split("\\W");
        StringBuilder b = new StringBuilder();
        for(String e: element){
            b.append(e);
            b.append(" ");
        }
        this.setSearchindex(b.toString());
    }

    public PetType(@NotBlank String name) {
        this.name = name;
    }

    @Transient
    public String getTableName() {
        return TABLENAME;
    }

    @Transient
    public String getPrimaryKey() {
        return this.getName();
    }

    @Transient
    public String getPrimaryKeyWithId() {
        return this.getPrimaryKey() + "(" + this.getId() + "," + this.getUuid() + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PetType)) return false;
        PetType petType = (PetType) o;
        return getName().equals(petType.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }

    @Override
    public int compareTo(PetType o) {
        return this.name.compareTo(o.name);
    }
}
