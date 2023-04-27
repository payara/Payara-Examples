package fish.payara.entities;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

import org.bson.json.JsonObject;
import org.bson.types.ObjectId;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;

@Entity("hello")
@Getter
@Setter
public class HelloEntity  {
	@Id
	private ObjectId id;
	private String name;
	private LocalDateTime greetingTime;
	private String greeting;
	private String greetingTimeFormatted;

	private JsonObject randomData;

	private String jsonString;
}
