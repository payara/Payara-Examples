package fish.payara.entities;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

import dev.morphia.annotations.*;
import dev.morphia.utils.IndexType;
import jakarta.validation.constraints.NotEmpty;

@Entity
@Getter
@Setter
@Indexes(
		{ @Index(fields = { @Field("departmentCode"), @Field("departmentName") }),
				@Index(fields = @Field(value = "$**", type = IndexType.TEXT))}
)
public class Department {

	@Id
	private String id;

	@NotEmpty
	private String departmentCode;
	@NotEmpty
	private String departmentName;

	@Reference(lazy = true)
	private List<Employee> employees = new ArrayList<>();


}
