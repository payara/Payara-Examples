package fish.payara.simple.login.models;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

/**
 * @author Fraser Savage
 * Used to store information about a user and calculate age and membership time.
 */
public class UserBean implements Serializable {
    private String username;
    private String password;
    private String bio;
    private byte age;
    private String registerAge;
    private String dateOfBirth;
    private String dateOfRegister;
    private Calendar birthday;
    private Calendar registerDay;
    private DateFormat birthDateFormat;
    private Date birthDate;
    private Date registerDate;
    private Calendar todaysDate;

    public UserBean() {
        username = "";
        password = "";
        bio = "";
        age = 0;
        registerAge = "";
        birthday = GregorianCalendar.getInstance();
        registerDay = GregorianCalendar.getInstance();
        birthDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateOfBirth = birthDateFormat.format(new Date());
        dateOfRegister = birthDateFormat.format(new Date());
        birthDate = new Date();
        registerDate = new Date();
        todaysDate = GregorianCalendar.getInstance();
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getBio() {
        return bio;
    }

    public byte getAge() {
        calculateAge();
        return age;
    }

    public String getRegisterAge() {
        calculateRegisterAge();
        return registerAge;
    }

    /**
     * Calculates the users age in years, by using the provided date of birth.
     */
    private void calculateAge() {
        try {
            birthDate = birthDateFormat.parse(dateOfBirth);
            birthday.setTime(birthDate);
        } catch (ParseException e) {
            System.out.println("Could not parse dateOfBirth as a DateFormat.");
        }

        age = (byte) (todaysDate.get(Calendar.YEAR) - birthday.get(Calendar.YEAR));

        birthday.add(Calendar.YEAR, age);

        if (todaysDate.before(birthday)) {
            age--;
        }
    }

    /**
     * Calculates how long the user has been registered for in years and months.
     */
    private void calculateRegisterAge() {
        try {
            registerDate = birthDateFormat.parse(dateOfRegister);
            registerDay.setTime(registerDate);
        } catch (ParseException e) {
            System.out.println("Could not parse the dateOfRegister as a DateFormat.");
        }


        byte registerAgeYears = (byte) (todaysDate.get(Calendar.YEAR) - registerDay.get(Calendar.YEAR));

        if ((todaysDate.get(Calendar.MONTH) < registerDay.get(Calendar.MONTH)) ||
           (todaysDate.get(Calendar.MONTH) == registerDay.get(Calendar.YEAR) &&
            todaysDate.get(Calendar.DATE) < registerDay.get(Calendar.DATE)))
        {
            registerAgeYears--;
        }

        if (todaysDate.get(Calendar.MONTH) < registerDay.get(Calendar.MONTH)) {
            byte registerAgeMonths = (byte) (12 - (registerDay.get(Calendar.MONTH) - todaysDate.get(Calendar.MONTH)));
            registerAge = registerAgeYears + " years, " + registerAgeMonths + " months.";
        } else if (todaysDate.get(Calendar.MONTH) > registerDay.get(Calendar.MONTH)) {
            byte registerAgeMonths = (byte) (todaysDate.get(Calendar.MONTH) - registerDay.get(Calendar.MONTH));
            registerAge = registerAgeYears + " years, " + registerAgeMonths + " months.";
        } else if (todaysDate.get(Calendar.MONTH) == registerDay.get(Calendar.MONTH)) {
            registerAge = registerAgeYears + " years.";
        }
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setDateOfRegister(String dateOfRegister) {
        this.dateOfRegister = dateOfRegister;
    }
}
