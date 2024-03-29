package org.woehlke.jakartaee.petclinic.owner;

import jakarta.validation.constraints.*;
import lombok.*;
import org.woehlke.jakartaee.petclinic.application.framework.EntityBaseObject;
import org.woehlke.jakartaee.petclinic.owner.db.OwnerListener;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: tw
 * Date: 01.01.14
 * Time: 21:08
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = Owner.TABLENAME,
        uniqueConstraints = {
                @UniqueConstraint(
                        name = Owner.TABLENAME + "_unique_uuid",
                        columnNames = {Owner.COL_UUID}
                ),
                @UniqueConstraint(
                        name = Owner.TABLENAME + "_unique_email",
                        columnNames = {Owner.COL_EMAIL}
                ),
                @UniqueConstraint(
                        name = Owner.TABLENAME + "_unique_names",
                        columnNames = {
                                Owner.COL_FIRSTNAME,
                                Owner.COL_LASTNAME,
                                Owner.COL_CITY,
                                Owner.COL_PHONENUMBER
                        }
                )
        }
)
@NamedQueries({
        @NamedQuery(
                name = "Owner.getAll",
                query = "select o from Owner o order by o.lastName,o.firstName asc"
        ),
        @NamedQuery(
                name = "Owner.search",
                query = "select o from Owner o where o.searchindex like :searchterm order by o.lastName,o.firstName asc"
        )
})
@EntityListeners(OwnerListener.class)
public class Owner extends EntityBaseObject implements Comparable<Owner>, Serializable {

    public final static String TABLENAME = "owner";
    public final static String COL_ID = "id";
    public final static String COL_UUID = "uuid";
    public final static String COL_FIRSTNAME = "first_name";
    public final static String COL_LASTNAME = "lastName";
    public final static String COL_ADDRESS = "address";
    public final static String COL_HOUSENUMBER = "housenumber";
    public final static String COL_ADDRESS_INFO = "address_info";
    public final static String COL_CITY = "city";
    public final static String COL_ZIPCODE = "zipcode";
    public final static String COL_PHONENUMBER = "phonenumber";
    public final static String COL_EMAIL = "email";
    public final static String COL_SEARCHINDEX = "searchindex";
    private static final long serialVersionUID = 7995827646591579744L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "owner_gen")
    @SequenceGenerator(name="owner_gen", sequenceName="owner_seq")
    private Long id;

    @Column(name = COL_UUID, nullable = false, unique = true, length = 36)
    private UUID uuid;

    @Column(name = COL_SEARCHINDEX)
    private String searchindex;

    @Column(name = COL_FIRSTNAME, nullable = false)
    @NotEmpty
    private String firstName;

    @Column(name = COL_LASTNAME, nullable = false)
    @NotEmpty
    private String lastName;

    @Column(name = COL_ADDRESS, nullable = false)
    @NotEmpty
    private String address;

    @Column(name = COL_HOUSENUMBER, nullable = false)
    @NotEmpty
    private String houseNumber;

    @Column(name = COL_ADDRESS_INFO)
    private String addressInfo;

    @Column(name = COL_CITY, nullable = false)
    @NotEmpty
    private String city;

    @Column(name = COL_ZIPCODE, nullable = false)
    @NotEmpty
    //@Digits(fraction = 0, integer = 5)
    @Pattern(regexp = "\\w{1,7}\\s{0,1}\\w{1,7}\\s{0,1}\\w{1,7}", message = "{invalid.zipCode}")
    private String zipCode;

    @Column(name = COL_PHONENUMBER, nullable = false)
    @NotEmpty
    @Pattern(regexp = "\\+[1-9][0-9]\\s{0,1}[0-9]{0,5}\\s{0,1}[0-9]{4,16}",
            message = "{invalid.phoneNumber}")
    private String phoneNumber;

    @Column(name = COL_EMAIL, nullable = false, unique = true)
    @NotEmpty
    @Email
    private String email;

    @Transient
    public String getFullname(){
        return this.lastName +", "+this.firstName;
    }

    @Transient
    public String getTableName() {
        return TABLENAME;
    }

    @Transient
    public String getPrimaryKey() {
        return "" + this.getFullName() + " ( " + this.getCity() + " ) ";
    }

    public void setPrimaryKey(String primaryKey) {
    }

    @Transient
    public String getPrimaryKeyWithId() {
        return this.getPrimaryKey() + "(" + this.getId() + "," + this.getUuid() + ")";
    }

    @Override
    public void updateSearchindex() {
        List<String> l = new ArrayList<>();
        l.add(this.getFirstName());
        l.add(this.getLastName());
        l.add(this.getAddress());
        if(null != this.getAddressInfo()){
            l.add(this.getAddressInfo());
        }
        l.add(this.getHouseNumber());
        l.add(this.getZipCode());
        l.add(this.getCity());
        l.add(this.getPhoneNumber());
        l.add(this.getEmail());
        StringBuilder b = new StringBuilder();
        for(String ll:l){
            for(String e:ll.split("\\W")){
                b.append(e);
                b.append(" ");
            }
        }
        this.setSearchindex(b.toString());
    }

    @Transient
    public String getFullName() {
        return this.lastName + ", " + this.firstName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Owner)) return false;
        Owner owner = (Owner) o;
        return getFirstName().equals(owner.getFirstName()) && getLastName().equals(owner.getLastName()) && Objects.equals(getAddress(), owner.getAddress()) && Objects.equals(getHouseNumber(), owner.getHouseNumber()) && Objects.equals(getAddressInfo(), owner.getAddressInfo()) && Objects.equals(getCity(), owner.getCity()) && Objects.equals(getZipCode(), owner.getZipCode()) && getPhoneNumber().equals(owner.getPhoneNumber()) && getEmail().equals(owner.getEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFirstName(), getLastName(), getAddress(), getHouseNumber(), getAddressInfo(), getCity(), getZipCode(), getPhoneNumber(), getEmail());
    }

    @Override
    public int compareTo(Owner o) {
        return this.getFullname().compareTo(o.getFullname());
    }
}
