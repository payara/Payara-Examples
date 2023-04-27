package fish.payara.controllers;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import com.mongodb.client.MongoClients;

import dev.morphia.Datastore;
import dev.morphia.Morphia;
import fish.payara.entities.HelloEntity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;

@ApplicationScoped
public class DatastoreProducer {

	@Inject
	@ConfigProperty(name = "connection.string")
	private String connectionString;

	@Inject
	@ConfigProperty(name = "deployment.stage")
	private String deploymentStage;

	@Inject
	@ConfigProperty(name = "database.name")
	private String databaseName;
	@Produces
	@ApplicationScoped
	public Datastore generateDatastore() {
		var datastore = Morphia.createDatastore(MongoClients.create(connectionString), databaseName);
		datastore.getMapper().mapPackage(HelloEntity.class.getPackageName());

		if (DeploymentStage.DEV.name().equalsIgnoreCase(deploymentStage)) {
			datastore.getDatabase().drop();
		}

		datastore.ensureIndexes();
		return datastore;
	}
}
