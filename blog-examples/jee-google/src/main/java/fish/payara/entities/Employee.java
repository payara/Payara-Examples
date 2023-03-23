package fish.payara.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Getter
@Setter

public class Employee {

	private String id;

	private LocalDateTime createdOn;
	private LocalDateTime updatedOn;

	@NotBlank
	private String firstName;
	private String middleName;
	@NotBlank
	private String lastName;
	private String businessKey;
	@NotNull
	private LocalDate dateOfBirth;
	private String departmentBusinessKey;

	public void stamp() {
		if (createdOn == null) {
			setCreatedOn(LocalDateTime.now(ZoneOffset.UTC));
		}
		if (id != null && !id.isBlank()) {
			setUpdatedOn(LocalDateTime.now(ZoneOffset.UTC));
		}
	}
}
