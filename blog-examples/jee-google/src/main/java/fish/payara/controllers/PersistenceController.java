package fish.payara.controllers;

import java.lang.reflect.Type;
import java.util.*;
import java.util.logging.Level;
import java.util.stream.Collectors;

import lombok.extern.java.Log;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;

import fish.payara.entities.Department;
import fish.payara.entities.Employee;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.util.TypeLiteral;
import jakarta.inject.Inject;
import jakarta.json.bind.Jsonb;
import jakarta.validation.*;

@ApplicationScoped
@Log
public class PersistenceController {
	public static final String BUSINESS_KEY_FIELD = "businessKey";
	public static final String EMPLOYEE_COLLECTION = "jakarta-ee-employee";
	public static final String DEPARTMENT_COLLECTION = "jakarta-ee-department";
	@Inject
	Firestore db;
	@Inject
	Jsonb json;
	Type type;
	DocumentReference documentReference;
	CollectionReference employeeCollection;
	CollectionReference departmentCollection;

	@PostConstruct
	void init() {
		employeeCollection = db.collection(EMPLOYEE_COLLECTION);
		departmentCollection = db.collection(DEPARTMENT_COLLECTION);
		type = new TypeLiteral<Map<String, Object>>() {
		}.getType();

	}

	public Employee saveEmployee(final String department, final Employee employee) {
		validate(department);

		documentReference = employeeCollection.document();

		Department dept = findDepartment(department);
		if (dept != null) {
			documentReference = employeeCollection.document();

			if (isEmptyString(employee.getBusinessKey())) {
				employee.setBusinessKey(UUID.randomUUID().toString());
			}
			employee.stamp();
			employee.setId(documentReference.getId());
			employee.setDepartmentBusinessKey(dept.getBusinessKey());
			if (dept.getEmployeeBusinessKeys() == null) {
				dept.setEmployeeBusinessKeys(new HashSet<>());
			}
			dept.getEmployeeBusinessKeys().add(employee.getBusinessKey());

			log.log(Level.INFO, () -> String.format("About to save employee to the db --> %s", json.toJson(employee)));

			documentReference.set(toJsonMap(employee));

			updateDepartment(dept);
			return findEmployee(employee.getBusinessKey());
		}
		throw new RuntimeException("No department found for dept business key " + department);
	}

	public Department updateDepartment(final Department department) {
		DocumentReference document = departmentCollection.document(department.getId());
		department.stamp();
		document.set(toJsonMap(department), SetOptions.merge());
		return findDepartment(department.getBusinessKey());
	}
	public Employee findEmployee(final String businessKey) {
		ApiFuture<QuerySnapshot> query = employeeCollection.whereEqualTo(BUSINESS_KEY_FIELD, businessKey).get();

		try {
			List<QueryDocumentSnapshot> documents = query.get().getDocuments();
			Employee employee = convert(documents, Employee.class);
			log.log(Level.INFO,
					() -> String.format("Returning employee %s with business key %s", json.toJson(employee),
							businessKey));
			return employee;
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}

	public List<Employee> findEmployeesByDepartment(final String departmentBusinessKey) {
		ApiFuture<QuerySnapshot> querySnapshot =
				employeeCollection.whereEqualTo("departmentBusinessKey", departmentBusinessKey).get();

		try {
			List<QueryDocumentSnapshot> documents = querySnapshot.get().getDocuments();
			return  convertToList( documents,Employee.class);
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}
	public Department saveDepartment(final Department department) {
		validate(department);
		documentReference = departmentCollection.document();

		if (isEmptyString(department.getBusinessKey())) {
			department.setBusinessKey(UUID.randomUUID().toString());
		}
		department.stamp();
		department.setId(documentReference.getId());

		log.log(Level.INFO, () -> String.format("About to save person to the db --> %s", json.toJson(department)));
		documentReference.set(toJsonMap(department));
		return findDepartment(department.getBusinessKey());

	}

	public Department findDepartment(final String businessKey) {
		ApiFuture<QuerySnapshot> query = departmentCollection.whereEqualTo(BUSINESS_KEY_FIELD, businessKey).get();

		try {
			List<QueryDocumentSnapshot> documents = query.get().getDocuments();
			Department department = convert(documents, Department.class);
			log.log(Level.INFO,
					() -> String.format("Returning department %s with business key %s", json.toJson(department),
							businessKey));
			return department;
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}



	private boolean isEmptyString(final String value) {
		return value == null || value.isBlank();
	}
	private <T> T convert(final List<QueryDocumentSnapshot> docs, final Class<T> clazz) {
		Optional<T> optional =
				docs.stream().map(d -> json.fromJson(json.toJson(d.getData()), clazz)).findFirst();
		return optional.orElse(null);
	}

	private <T> List<T> convertToList(final List<QueryDocumentSnapshot> docs, final Class<T> clazz) {
		return docs.stream().map(d -> json.fromJson(json.toJson(d.getData()), clazz)).collect(Collectors.toList());

	}

	private Map<String, Object> toJsonMap(final Object object) {
		return json.fromJson(json.toJson(object), type);
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
