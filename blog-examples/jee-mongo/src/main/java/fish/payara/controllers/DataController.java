package fish.payara.controllers;

import dev.morphia.Datastore;
import dev.morphia.query.FindOptions;
import dev.morphia.query.Query;
import dev.morphia.query.filters.Filters;
import fish.payara.entities.Department;
import fish.payara.entities.Employee;
import fish.payara.entities.HelloEntity;
import fish.payara.jeemongo.SearchBeanParam;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.json.bind.JsonbBuilder;
import jakarta.validation.*;
import org.bson.json.JsonObject;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static dev.morphia.query.Sort.ascending;

@ApplicationScoped
public class DataController {
	public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

	@Inject
	private Datastore datastore;

	private String getGreeting() {

		var timeNow = LocalDateTime.now(ZoneOffset.UTC);
		var timeOfDay = DateTimeFormatter.ofPattern("B").format(timeNow);

		return switch (timeOfDay) {
			case "at night", "in the evening" -> "Good evening";
			case "in the afternoon" -> "Good afternoon";
			case "in the morning" -> "Good morning";
			default -> "Good day";
		};

	}

	public HelloEntity greet(final String name) {

		var greeting = getGreeting();
		var helloEntity = new HelloEntity();

		helloEntity.setGreeting(greeting + " " + name);
		helloEntity.setGreetingTime(LocalDateTime.now(ZoneOffset.UTC));
		helloEntity.setName(name);
		helloEntity.setGreetingTimeFormatted(
				DateTimeFormatter.ofPattern(DATE_TIME_FORMAT).format(helloEntity.getGreetingTime()));
		String json = JsonbBuilder.create().toJson(helloEntity);
		helloEntity.setRandomData(new JsonObject(json));
		helloEntity.setJsonString(json);

		return datastore.save(helloEntity);
	}

	public Employee saveEmployee(final String departmentId, final Employee employee) {
		validate(employee);
		setEmployeeId(employee);
		return assignDepartment(departmentId, employee);
	}

	private void setEmployeeId(final Employee employee) {
		if (employee.getEmployeeId() == null || employee.getEmployeeId().isBlank()) {
			employee.setEmployeeId(UUID.randomUUID().toString().replace("-", ""));
		}
	}

	public List<Employee> loadEmployeesByDepartment(final String departmentCode) {
		Optional<Department> dept = datastore.find(Department.class)
				.filter(Filters.eq("departmentCode", departmentCode))
				.stream().findFirst();
		if (dept.isPresent()) {
			return dept.get().getEmployees();
		}
		return Collections.emptyList();
	}

	public List<Employee> search(final SearchBeanParam beanParam) {
		return datastore.find(Employee.class)
				.filter(Filters.or(Filters.eq("firstName", beanParam.getFirstName()),
								Filters.eq("lastName", beanParam.getLastName()),
								Filters.eq("employeeId", beanParam.getEmployeeId()),
								Filters.eq("dateOfBirth", beanParam.getDateOfBirth())),
						Filters.eq("jobTitle", beanParam.getJobTitle()))
				.iterator(new FindOptions().sort(ascending("lastName")))
				.toList();
	}

	public List<Employee> textSearch(final String searchTerm) {
		return datastore.find(Employee.class)
				.filter(Filters.text(searchTerm)).
				iterator(new FindOptions()
						.sort(ascending("_id")))
				.toList();
	}

	public Department saveDepartment(final Department department) {
		validate(department);
		return datastore.save(department);

	}

	private Employee assignDepartment(final String departmentCode, final Employee employee) {
		Query<Department> query = datastore.find(Department.class).filter(Filters.eq("departmentCode", departmentCode));
		Department department = query.first();
		if (department == null) {
			throw new ValidationException("No department found for department code " + departmentCode);
		}
		var employeeDept = employee.getDepartment();
		if (isAssignable(employeeDept, department)) {
			employee.setDepartment(departmentCode);
			var updateEmployee = datastore.save(employee);
			department.getEmployees().add(updateEmployee);
			datastore.merge(department);
			return updateEmployee;
		}
		return datastore.merge(employee);
	}

	private boolean isAssignable(final String employeeDept, final Department newDepartment) {
		return (employeeDept == null || employeeDept.isBlank() )
				|| newDepartment != null
				&& !Objects.equals(employeeDept, newDepartment.getDepartmentCode());
	}

	private void validate(final Object object) {
		try (final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory()) {
			final Validator validator = validatorFactory.getValidator();
			final Set<ConstraintViolation<Object>> constraintViolations = validator.validate(object);

			if (!constraintViolations.isEmpty()) {
				throw new ConstraintViolationException(constraintViolations);
			}
		}
	}
}
