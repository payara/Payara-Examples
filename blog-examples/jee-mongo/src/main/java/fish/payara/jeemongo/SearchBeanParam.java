package fish.payara.jeemongo;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

import jakarta.ws.rs.QueryParam;

@Getter
@Setter
public class SearchBeanParam {
	@QueryParam("firstName")
	private String firstName;

	@QueryParam("lastName")
	private String lastName;

	@QueryParam("employeeId")
	private String employeeId;

	@QueryParam("dateOfBirth")
	private LocalDate dateOfBirth;



	@QueryParam("jobTitle")
	private String jobTitle;




}
