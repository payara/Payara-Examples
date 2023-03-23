package fish.payara.entities;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashSet;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.NotEmpty;

@Getter
@Setter

public class Department {
	private String id; //Technical ID
	@NotEmpty
	private String departmentName;
	@NotEmpty
	private String description;

	@NotEmpty
	private String departmentCode;
	private String businessKey;
	private Set<String> employeeBusinessKeys = new HashSet<>();

	private LocalDateTime createdOn;
	private LocalDateTime updatedOn;

	public void stamp() {
		if (createdOn == null) {
			setCreatedOn(LocalDateTime.now(ZoneOffset.UTC));
		}
		if (id != null && !id.isBlank()) {
			setUpdatedOn(LocalDateTime.now(ZoneOffset.UTC));
		}
	}
}
