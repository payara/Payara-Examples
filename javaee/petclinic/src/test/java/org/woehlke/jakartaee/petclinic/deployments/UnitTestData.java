package org.woehlke.jakartaee.petclinic.deployments;

import org.woehlke.jakartaee.petclinic.owner.Owner;
import org.woehlke.jakartaee.petclinic.pet.Pet;
import org.woehlke.jakartaee.petclinic.pettype.PetType;
import org.woehlke.jakartaee.petclinic.specialty.Specialty;
import org.woehlke.jakartaee.petclinic.vet.Vet;
import org.woehlke.jakartaee.petclinic.visit.Visit;

import java.util.*;
import java.util.Date;

public abstract class UnitTestData {

    protected String name01 = "wooollooomooollooo";
    protected String name02 = "huulluuumuuulluuu";
    protected UUID uuid01 = UUID.randomUUID();
    protected UUID uuid02 = UUID.randomUUID();
    protected UUID uuid = UUID.randomUUID();
    protected Long id01 = 1L;
    protected Long id02 = 2L;
    protected Long id = 3L;
    protected Date dob01 = new Date(2021, 6, 14);;
    protected Date dob02 = new Date(2021, 5, 12);

    protected static Owner owner1 = new Owner();

    protected static Pet pet1 = new Pet();
    protected static Pet pet2 = new Pet();

    protected static List<Specialty> specialtyList = new ArrayList<>();
    protected static Set<Specialty> specialtySet = new HashSet<>();
    protected static Map<String,String> vetNames = new HashMap<>();
    protected static List<Vet> vetList = new ArrayList<>();
    protected static List<Date> dateOfBirthList = new ArrayList<>();
    protected static List<PetType> petTypeList = new ArrayList<>();
    protected static List<Pet> petList = new ArrayList<>();
    protected static Set<Pet> petSet = new HashSet<>();
    protected static List<Visit> visitList = new ArrayList<>();
    protected static List<Owner> ownerList = new ArrayList<>();

    protected static final String[][] vetNames1Array = {
            {"A Hippocrates 1", "A of Kos 1"},
            {"A Hippocrates 2", "A of Kos 2"},
            {"A Hippocrates 3", "A of Kos 3"}
    };

    protected static final String[][] vetNames2Array = {
            {"von Kos",             "Hippokrates"},
            {"Koch",                "Robert"},
            {"Fleming",             "Alexander"},
            {"Virchow",             "Rudolf"},
            {"Freud",               "Sigmund"},
            {"Stertinius Xenophon", "Gaius"},
            {"von Behring",         "Emil"},
            {"Ehrlich",             "Paul"},
            {"Shibasaburō",         "Kitasato"},
            {"Hispalensis",         "Isidorus"},
            {"Schweitzer",          "Albert"},
            {"Montessori",          "Maria Tecla Artemisia"}
    };

    protected static final String[] specialtyNameArray = {
            "A Hippocrates 1",
            "A Hippocrates 2",
            "A Hippocrates 3"
    };

    protected static final String[] specialtyNames = {
            "A Surgeon",
            "A Oncologist",
            "A Radiologist",
            "A Neurosurgeon",
            "A Cardiologist",
            "A Dermatologist",
            "A Orthopedist",
            "A Dentist",
            "A Impostor",
            "A Faith Healer"
    };

    protected static final String[] petTypeNameArray = {
            "A Animal 1 Farm",
            "A Animal 2 Farm",
            "A Animal 3 Farm"
    };

    protected static final String[] petTypeNames = {
            "A Dog",
            "A Cat",
            "A Elephant",
            "A Kitten",
            "A Donkey",
            "A Cow",
            "A Pig",
            "A Chicken"
    };

    protected static final String[] petNames = {
            "A Jessie",
            "A Lucifer Sam",
            "A Nelly",
            "A Kitten",
            "A Benjamin",
            "A Pauline Wayne",
            "A Snowball",
            "A Gallus gallus domesticus"
    };

    static {
        vetNames.put("von Kos","Hippokrates");
        vetNames.put("Koch","Robert");
        vetNames.put("Fleming","Alexander");
        vetNames.put("Virchow","Rudolf");
        vetNames.put("Freud","Sigmund");
        vetNames.put("Stertinius Xenophon","Gaius");
        vetNames.put("von Behring","Emil");
        vetNames.put("Ehrlich","Paul");
        vetNames.put("Shibasaburō","Kitasato");
        vetNames.put("Hispalensis","Isidorus");
        vetNames.put("Schweitzer","Albert");
        vetNames.put("Montessori","Maria Tecla Artemisia");

        for (String specialtyName : specialtyNames){
            Specialty specialty = new Specialty();
            specialty.setName(specialtyName);
            specialty.setUuid(UUID.randomUUID());
            specialtyList.add(specialty);
            specialtySet.add(specialty);
        }

        for (String petTypeName : petTypeNames){
            PetType o = new PetType();
            o.setName(petTypeName);
            o.setUuid(UUID.randomUUID());
            petTypeList.add(o);
        }

        int year = 2010;
        int month = 1;
        int day = 5;
        for (String petTypeName : petTypeNames) {
            Date dob = new Date(year,month,day);
            dateOfBirthList.add(dob);
            year++;
            month++;
            day++;
        }

        Owner o = new Owner();
        o.setUuid(UUID.randomUUID());
        o.setFirstName("A1 Kurt");
        o.setLastName("A1 Tucholsky");
        o.setAddress("Lübecker Straße");
        o.setHouseNumber("13");
        o.setCity("Berlin");
        o.setZipCode("10559");
        o.setPhoneNumber("+49 30 3946364");
        o.setEmail("kurt.tucholsky@vistaberlin.de");

        owner1 = o;
        for(Pet pet:petSet){
            pet.setOwner(owner1);
        }
        ownerList.add(o);

        int i=0;
        int k=0;
        for (String petName : petNames){
            Date dob = dateOfBirthList.get(k);
            Pet pet = new Pet();
            pet.setName(petName);
            pet.setBirthDate(dob);
            pet.setType(petTypeList.get(i));
            pet.setUuid(UUID.randomUUID());
            pet.setOwner(owner1);
            petList.add(pet);
            petSet.add(pet);
            i++;
            i %= petTypeList.size();
            k++;
        }

        for(Map.Entry<String,String> vetName:vetNames.entrySet()){
            Vet v = new Vet();
            v.setLastName(vetName.getKey());
            v.setFirstName(vetName.getValue());
            v.setUuid(UUID.randomUUID());
            v.setSpecialties(specialtySet);
            vetList.add(v);
        }

        o = new Owner();
        o.setUuid(UUID.randomUUID());
        o.setFirstName("A2 Heinrich");
        o.setLastName("A2 Heine");
        o.setAddress("Bolkerstraße");
        o.setHouseNumber("53");
        o.setCity("Düsseldorf");
        o.setZipCode("40213");
        o.setPhoneNumber("+4921120054294");
        o.setEmail("heinrich.heine@heinehaus.de");
        ownerList.add(o);
        o = new Owner();
        o.setUuid(UUID.randomUUID());
        o.setFirstName("A3 Alan");
        o.setLastName("A3 Turing");
        o.setAddress("Bletchley Park");
        o.setHouseNumber("Block H");
        o.setCity("Milton Keynes");
        o.setZipCode("MK3 6EB");
        o.setPhoneNumber("+441908374708");
        o.setEmail("alan.turing@tnmoc.org");
        ownerList.add(o);
        o = new Owner();
        o.setUuid(UUID.randomUUID());
        o.setFirstName("A4 Grace");
        o.setLastName("A4 Hopper");
        o.setAddress("St Ronan St");
        o.setHouseNumber("160");
        o.setAddressInfo("Yale University");
        o.setCity("New Haven");
        o.setZipCode("CT 06520");
        o.setPhoneNumber("+12034324771");
        o.setEmail("grace.hopper@yale.edu");
        ownerList.add(o);
        o = new Owner();
        o.setUuid(UUID.randomUUID());
        o.setFirstName("A5 Mahatma");
        o.setLastName("A5 Ghandi");
        o.setAddress("Sevagram");
        o.setHouseNumber("PMH7 H42");
        o.setAddressInfo("Mahatma Gandhi Ashram");
        o.setCity("Maharashtra");
        o.setZipCode("442102");
        o.setPhoneNumber("+91 7152 284753");
        o.setEmail("mahatma.ghandi@gandhiashramsevagram.org");
        ownerList.add(o);

        pet1.setBirthDate(dateOfBirthList.get(0));
        pet1.setType(petTypeList.get(2));
        pet1.setName(petNames[2]);
        pet1.setUuid(UUID.randomUUID());
        pet1.setOwner(owner1);

        pet2.setBirthDate(dateOfBirthList.get(1));
        pet2.setType(petTypeList.get(3));
        pet2.setName(petNames[3]);
        pet2.setUuid(UUID.randomUUID());
        pet1.setOwner(owner1);

        long index = 0;
        Pet p = petList.get(2);
        for(Date date : dateOfBirthList){
            index++;
            Visit v = new Visit();
            v.setPet(p);
            v.setDate(date);
            v.setDescription("woooolloooomoooolloooo "+index);
            v.setUuid(UUID.randomUUID());
            v.setId(index);
            visitList.add(v);
        }
    }
}
