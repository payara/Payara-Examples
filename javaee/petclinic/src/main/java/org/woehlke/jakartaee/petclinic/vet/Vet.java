package org.woehlke.jakartaee.petclinic.vet;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.woehlke.jakartaee.petclinic.application.framework.EntityBase;
import org.woehlke.jakartaee.petclinic.application.framework.EntityBaseObject;
import org.woehlke.jakartaee.petclinic.specialty.Specialty;

import jakarta.persistence.*;
import org.woehlke.jakartaee.petclinic.vet.db.VetListener;

import java.io.Serializable;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: tw
 * Date: 01.01.14
 * Time: 21:10
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = Vet.TABLENAME,
        uniqueConstraints = {
                @UniqueConstraint(
                        name = Vet.TABLENAME + "_unique_uuid",
                        columnNames = {Vet.COL_UUID}
                ),
                @UniqueConstraint(
                        name = Vet.TABLENAME + "_unique_names",
                        columnNames = {Vet.COL_FIRSTNAME,Vet.COL_LASTNAME}
                )
        }
)
@NamedQueries({
        @NamedQuery(
                name = "Vet.getAll",
                query = "select s from Vet s order by s.lastName, s.firstName"
        ),
        @NamedQuery(
                name = "Vet.search",
                query = "select v from Vet v where v.searchindex like :searchterm"
        )
})
@EntityListeners(VetListener.class)
public class Vet extends EntityBaseObject implements EntityBase, Comparable<Vet>, Serializable {

    private static final long serialVersionUID = 6749793465861123385L;

    public final static String XML_ROOT_ELEMENT_NAME = "vet";
    public final static String TABLENAME = "vet";
    public final static String COL_ID = "id";
    public final static String COL_UUID = "uuid";
    public final static String COL_FIRSTNAME = "first_name";
    public final static String COL_LASTNAME = "lastName";
    public final static String COL_VET_SPECIALTIES = "vet_specialties";
    public final static String COL_JOIN_VET_ID = "vet_id";
    public final static String COL_JOIN_SPECIALTY_ID = "specialty_id";
    public final static String COL_SEARCHINDEX = "searchindex";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vet_gen")
    @SequenceGenerator(name="vet_gen", sequenceName="vet_seq")
    private Long id;

    @Column(name = COL_UUID, nullable = false, unique = true, length = 36)
    private UUID uuid;

    @Column(name = COL_SEARCHINDEX, nullable = true)
    private String searchindex;

    @NotBlank
    @Column(name = COL_FIRSTNAME, nullable = false)
    private String firstName;

    @NotBlank
    @Column(name = COL_LASTNAME, nullable = false)
    private String lastName;

    @NotNull
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = COL_VET_SPECIALTIES,
            joinColumns = @JoinColumn(name = COL_JOIN_VET_ID),
            inverseJoinColumns = @JoinColumn(name = COL_JOIN_SPECIALTY_ID))
    private Set<Specialty> specialties = new HashSet<>();

    public Vet(@NotBlank String firstName, @NotBlank String lastName) {
        this.specialties = new HashSet<>();
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Transient
    public String getFullname(){
        return this.lastName +", "+this.firstName;
    }

    @Transient
    public String getSpecialtiesAsString() {
        StringBuilder stringBuilder = new StringBuilder();
        if (getNrOfSpecialties() == 0) {
            stringBuilder.append("none");
        } else {
            for (Specialty specialty : getSpecialties()) {
                stringBuilder.append(specialty.getName());
                stringBuilder.append(" ");
            }
        }
        return stringBuilder.toString();
    }

    @Transient
    public List<Specialty> getSpecialtiesAsList() {
        List<Specialty> listSpecialty = new ArrayList<>();
        Set<Specialty> setSpecialty = this.getSpecialties();
        for(Specialty specialty: setSpecialty){
            listSpecialty.add(specialty);
        }
        Collections.sort(listSpecialty);
        return listSpecialty;
    }

    public int getNrOfSpecialties() {
        return this.specialties.size();
    }

    public void addSpecialty(Specialty specialty) {
        this.specialties.add(specialty);
    }

    public void removeSpecialties() {
        this.specialties.clear();
    }

    @Transient
    @Override
    public String getTableName() {
        return TABLENAME;
    }

    @Transient
    @Override
    public String getPrimaryKey() {
        return lastName + " " + firstName;
    }

    @Transient
    @Override
    public String getPrimaryKeyWithId() {
        return this.getPrimaryKey() + "(" + this.getId() + "," + this.getUuid() + ")";
    }

    @Override
    public void updateSearchindex() {
        List<String> vetElements = new ArrayList<>();
        for(String element :this.getFirstName().split("\\W")){
            vetElements.add(element);
        }
        for(String element :this.getLastName().split("\\W")){
            vetElements.add(element);
        }
        for(Specialty s: this.getSpecialties()){
            for(String element :s.getName().split("\\W")) {
                vetElements.add(element);
            }
        }
        StringBuilder b = new StringBuilder();
        for(String e: vetElements){
            b.append(e);
            b.append(" ");
        }
        this.setSearchindex(b.toString());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vet)) return false;
        Vet vet = (Vet) o;
        return getFirstName().equals(vet.getFirstName()) && getLastName().equals(vet.getLastName()) && getSpecialties().equals(vet.getSpecialties());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFirstName(), getLastName(), getSpecialties());
    }

    @Override
    public int compareTo(Vet o) {
        return this.getFullname().compareTo(o.getFullname());
    }
}
