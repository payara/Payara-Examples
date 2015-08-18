package fish.payara.team.info.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Fraser Savage
 */
@Entity
public class MemberEntity implements Serializable {
    private static final Logger log = Logger.getLogger(MemberEntity.class.getCanonicalName());
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(max = 20, message = "{name.length.too.large}")
    private String name;
    private byte age;
    private String memberAge;
    private DateFormat dateFormat;
    private Calendar birthday;
    private Calendar hireday;
    private Date birthdate;
    private Date hiredate;
    private String dateOfBirth;
    private String dateOfHire;
    @NotNull
    @Size(max = 50, message = "{email.length.too.large}")
    private String email;
    @Size(max = 255, message = "{bio.length.too.large}")
    private String bio;
    private Calendar todaysDate;

    public MemberEntity() {
        birthday = GregorianCalendar.getInstance();
        hireday = GregorianCalendar.getInstance();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateOfBirth = dateFormat.format(new Date());
        dateOfHire = dateFormat.format(new Date());
        todaysDate = GregorianCalendar.getInstance();
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte getAge() {
        calculateAge();
        return age;
    }

    /**
     * Calculates the age of a team member.
     */
    private void calculateAge() {
        try {
            log.log(Level.INFO,dateOfBirth);
            birthdate = dateFormat.parse(dateOfBirth);
            birthday.setTime(birthdate);
        } catch (ParseException e) {
            log.log(Level.WARNING, "The given date of birth that could not be parsed.");
        }

        age = (byte) (todaysDate.get(Calendar.YEAR) - birthday.get(Calendar.YEAR));

        if (todaysDate.before(birthday)) {
            age--;
        }
    }

    public String getMemberAge() {
        calculateMemberAge();
        return memberAge;
    }

    /**
     * Calculates how long a member has belonged to the team in the format: X years, Y months.
     */
    private void calculateMemberAge() {
        try {
            log.log(Level.INFO,dateOfHire);
            hiredate = dateFormat.parse(dateOfHire);
            hireday.setTime(hiredate);
        } catch (ParseException e) {
            log.log(Level.WARNING, "The given date of hire could not be parsed.");
        }

        byte hireAgeYears = (byte) (todaysDate.get(Calendar.YEAR) - hireday.get(Calendar.YEAR));

        if ((todaysDate.get(Calendar.MONTH) < hireday.get(Calendar.MONTH)) ||
            (todaysDate.get(Calendar.MONTH) == hireday.get(Calendar.MONTH) &&
             todaysDate.get(Calendar.DATE) < hireday.get(Calendar.DATE)))
        {
            hireAgeYears--;
        }

        if (todaysDate.get(Calendar.MONTH) < hireday.get(Calendar.MONTH)) {
            byte hireAgeMonths = (byte) (12 - (hireday.get(Calendar.MONTH) - todaysDate.get(Calendar.MONTH)));
            memberAge = hireAgeYears + " years, " +  hireAgeMonths + " months.";
        } else if (todaysDate.get(Calendar.MONTH) > hireday.get(Calendar.MONTH)) {
            byte hireAgeMonths = (byte) (todaysDate.get(Calendar.MONTH) - hireday.get(Calendar.MONTH));
            memberAge = hireAgeYears + " years, " + hireAgeMonths + " months.";
        } else if (todaysDate.get(Calendar.MONTH) == hireday.get(Calendar.MONTH)) {
            memberAge = hireAgeYears + " years.";
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setDateOfHire(String dateOfHire) {
        this.dateOfHire = dateOfHire;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "fish.payara.team.info.models.MemberEntity[ id=" + id + " ]";
    }
}
