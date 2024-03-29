package org.woehlke.jakartaee.petclinic.specialty;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.woehlke.jakartaee.petclinic.application.framework.EntityBase;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.*;
import org.woehlke.jakartaee.petclinic.application.framework.EntityBaseObject;
import org.woehlke.jakartaee.petclinic.pettype.PetType;

import java.util.Objects;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: tw
 * Date: 01.01.14
 * Time: 21:11
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = Specialty.TABLENAME,
        uniqueConstraints = {
                @UniqueConstraint(
                        name = Specialty.TABLENAME + "_unique_uuid",
                        columnNames = {Specialty.COL_UUID}
                ),
                @UniqueConstraint(
                        name = Specialty.TABLENAME + "_unique_names",
                        columnNames = {
                                Specialty.COL_NAME
                        }
                )
        }
)
@NamedQueries({
        @NamedQuery(
                name = "Specialty.getAll",
                query = "select s from Specialty s order by s.name asc"
        ),
        @NamedQuery(
                name = "Specialty.findSpecialtyByName",
                query = "select s from Specialty s where s.name=:name"
        ),
        @NamedQuery(
                name = "Specialty.search",
                query = "select v from Specialty v where v.searchindex like :searchterm order by v.name asc "
        )
})
public class Specialty extends EntityBaseObject implements EntityBase,Comparable<Specialty> {

    public final static String TABLENAME = "specialty";
    public final static String COL_ID = "id";
    public final static String COL_UUID = "uuid";
    public final static String COL_NAME = "name";
    public final static String COL_SEARCHINDEX = "searchindex";
    private static final long serialVersionUID = -836560513920170089L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "specialty_gen")
    @SequenceGenerator(name="specialty_gen", sequenceName="specialty_seq")
    private Long id;

    @Column(name = COL_UUID, nullable = false, unique = true, length = 36)
    private UUID uuid;

    @Column(name = COL_SEARCHINDEX, nullable = true)
    private String searchindex;

    @NotEmpty
    @XmlElement(required = true)
    @Column(name = COL_NAME, unique = true, nullable = false)
    private String name;

    public static Specialty newEntity(){
        Specialty s = new Specialty();
        s.uuid = UUID.randomUUID();
        s.searchindex = "";
        s.name = "";
        return s;
    }

    public static Specialty newEntity(@NotBlank String name){
        Specialty s = new Specialty();
        s.uuid = UUID.randomUUID();
        s.searchindex = "";
        s.name = name;
        return s;
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

    public Specialty(@NotBlank String name) {
        this.name = name;
    }

    @Override
    public String getTableName() {
        return TABLENAME;
    }

    @Override
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
        if (!(o instanceof Specialty)) return false;
        Specialty specialty = (Specialty) o;
        return getName().equals(specialty.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }

    @Override
    public int compareTo(Specialty o) {
        return this.name.compareTo(o.name);
    }
}
