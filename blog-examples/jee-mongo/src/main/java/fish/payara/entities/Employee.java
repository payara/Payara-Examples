package fish.payara.entities;

import dev.morphia.annotations.*;
import dev.morphia.utils.IndexType;
import jakarta.json.bind.annotation.JsonbDateFormat;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneOffset;

@Entity
@Getter
@Setter
@Indexes(
		{ @Index(fields = { @Field("firstName"), @Field("lastName") }),
				@Index(fields = @Field("salary"), options = @IndexOptions(name = "employeeSalary")),
				@Index(fields = @Field(value = "$**", type = IndexType.TEXT))}
)
public class Employee {

	@Id
	private ObjectId id;
	@NotEmpty
	private String firstName;
	@NotEmpty
	private String lastName;
	private String employeeId;
	@NotNull
	@JsonbDateFormat(value = "dd/MM/yyyy", locale = "Locale.ENGLISH")
	private LocalDate dateOfBirth;
	@NotNull
	@Property("basicSalary")
	private BigDecimal salary;
	@NotNull
	private String jobTitle;


	private String department;
	@Transient
	private int age;

	@PostLoad
	private void postDataLoad() {
		age = Period.between(dateOfBirth, LocalDate.now(ZoneOffset.UTC)).getYears();
	}

	private Address address;


}
