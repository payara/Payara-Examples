package fish.payara.configuration;

import java.io.InputStream;

import lombok.extern.java.Log;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;

@ApplicationScoped
@Log
public class CDIFactory {

	@Produces
	@ApplicationScoped
	public Firestore initFirestoreDb() {

		try {
			final InputStream resourceAsStream = this.getClass().getResourceAsStream("/service-account-file.json");
			assert resourceAsStream != null;
			final FirebaseOptions firebaseOptions =
					FirebaseOptions.builder()
							.setCredentials(GoogleCredentials.fromStream(resourceAsStream))
							.build();
			FirebaseApp.initializeApp(firebaseOptions);
			return FirestoreClient.getFirestore();

		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	@Produces
	public Jsonb initJson() {
		final Jsonb json;
		try(var autoClose = JsonbBuilder.create()) {
			json = autoClose;
			return json;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
