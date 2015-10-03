package fish.payara.examples.payaramicro.spring.boot.controller;

import fish.payara.examples.payaramicro.spring.boot.domain.Person;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mertcaliskan
 */
@RestController
@RequestMapping("/person")
public class PersonRestController {

    Map<Integer, Person> personMap = new HashMap<>();

    @PostConstruct
    public void init() {
        personMap.put(1, new Person(1, "Mert", "Caliskan", "mcaliskan@gmail.com"));
        personMap.put(2, new Person(2, "Steve", "Millidge", "smillidge@c2b2.co.uk"));
        personMap.put(3, new Person(3, "Andrew", "Pielage", "apielage@c2b2.co.uk"));
    }

    @RequestMapping("/all")
    public Collection<Person> getAll() {
        return personMap.values();
    }
}
