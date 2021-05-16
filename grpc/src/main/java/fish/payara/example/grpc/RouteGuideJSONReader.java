package fish.payara.example.grpc;

import com.google.protobuf.util.JsonFormat;
import io.grpc.examples.routeguide.Feature;
import io.grpc.examples.routeguide.FeatureDatabase;

import javax.enterprise.context.ApplicationScoped;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;

@ApplicationScoped
public class RouteGuideJSONReader {

    /**
     * Gets the default features file from classpath.
     */
    private URL getDefaultFeaturesFile() {
        return RouteGuideService.class.getResource("route_guide_db.json");
    }

    /**
     * Parses the JSON input file containing the list of features.
     */
    public List<Feature> readFeatures() throws IOException {
        InputStream input = getDefaultFeaturesFile().openStream();
        try {
            Reader reader = new InputStreamReader(input, Charset.forName("UTF-8"));
            try {
                FeatureDatabase.Builder database = FeatureDatabase.newBuilder();
                JsonFormat.parser().merge(reader, database);
                return database.getFeatureList();
            } finally {
                reader.close();
            }
        } finally {
            input.close();
        }
    }
}
