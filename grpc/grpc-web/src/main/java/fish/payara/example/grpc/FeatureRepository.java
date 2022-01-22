package fish.payara.example.grpc;

import io.grpc.examples.routeguide.Feature;
import io.grpc.examples.routeguide.Point;
import io.grpc.examples.routeguide.RouteNote;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@ApplicationScoped
public class FeatureRepository {

    private Collection<Feature> features;

    private  ConcurrentMap<Point, List<RouteNote>> routeNotes =
            new ConcurrentHashMap<>();

    @Inject
    private RouteGuideJSONReader reader;

    @PostConstruct
    public void init() {
        try {
            features = reader.readFeatures();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Gets the feature at the given point.
     *
     * @param location the location to check.
     * @return The feature object at the point. Note that an empty name indicates no feature.
     */
    public Feature findFeature(Point location) {
        for (Feature feature : features) {
            if (feature.getLocation().getLatitude() == location.getLatitude()
                    && feature.getLocation().getLongitude() == location.getLongitude()) {
                return feature;
            }
        }

        // No feature was found, return an unnamed feature.
        return Feature.newBuilder().setName("").setLocation(location).build();
    }

    public Collection<Feature> getFeatures() {
        return new ArrayList<>(features);
    }

    /**
     * Get the notes list for the given location. If missing, create it.
     */
    public List<RouteNote> getOrCreateNotes(Point location) {
        List<RouteNote> notes = Collections.synchronizedList(new ArrayList<>());
        List<RouteNote> prevNotes = routeNotes.putIfAbsent(location, notes);
        return prevNotes != null ? prevNotes : notes;
    }

}
