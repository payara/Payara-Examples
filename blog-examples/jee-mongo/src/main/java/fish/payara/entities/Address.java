package fish.payara.entities;

import lombok.Getter;
import lombok.Setter;

import dev.morphia.annotations.Entity;

@Entity
@Getter
@Setter
public class Address {
	private String street;
	private String apartment;
	private String zip;
	private String country;

	}
