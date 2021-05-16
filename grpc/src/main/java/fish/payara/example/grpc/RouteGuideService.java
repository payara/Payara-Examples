package fish.payara.example.grpc;

import io.grpc.examples.routeguide.*;
import io.grpc.stub.StreamObserver;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.util.concurrent.TimeUnit.NANOSECONDS;

@ApplicationScoped
public class RouteGuideService extends RouteGuideGrpc.RouteGuideImplBase {

    private static final Logger logger = Logger.getLogger(RouteGuideService.class.getName());

    @Inject
    private RouteGuideUtil routeGuideUtil;

    @Inject
    private FeatureRepository featureRepository;

    /**
     * Gets the {@link Feature} at the requested {@link Point}. If no feature at that location
     * exists, an unnamed feature is returned at the provided location.
     *
     * @param request          the requested location for the feature.
     * @param responseObserver the observer that will receive the feature at the requested point.
     */
    @Override
    public void getFeature(Point request, StreamObserver<Feature> responseObserver) {
        responseObserver.onNext(featureRepository.findFeature(request));
        responseObserver.onCompleted();
    }

    /**
     * Gets all features contained within the given bounding {@link Rectangle}.
     *
     * @param request          the bounding rectangle for the requested features.
     * @param responseObserver the observer that will receive the features.
     */
    @Override
    public void listFeatures(Rectangle request, StreamObserver<Feature> responseObserver) {
        int left = min(request.getLo().getLongitude(), request.getHi().getLongitude());
        int right = max(request.getLo().getLongitude(), request.getHi().getLongitude());
        int top = max(request.getLo().getLatitude(), request.getHi().getLatitude());
        int bottom = min(request.getLo().getLatitude(), request.getHi().getLatitude());

        for (Feature feature : featureRepository.getFeatures()) {
            if (!routeGuideUtil.exists(feature)) {
                continue;
            }

            int lat = feature.getLocation().getLatitude();
            int lon = feature.getLocation().getLongitude();
            if (lon >= left && lon <= right && lat >= bottom && lat <= top) {
                responseObserver.onNext(feature);
            }
        }
        responseObserver.onCompleted();
    }

    /**
     * Gets a stream of points, and responds with statistics about the "trip": number of points,
     * number of known features visited, total distance traveled, and total time spent.
     *
     * @param responseObserver an observer to receive the response summary.
     * @return an observer to receive the requested route points.
     */
    @Override
    public StreamObserver<Point> recordRoute(final StreamObserver<RouteSummary> responseObserver) {
        return new StreamObserver<Point>() {
            int pointCount;
            int featureCount;
            int distance;
            Point previous;
            final long startTime = System.nanoTime();

            @Override
            public void onNext(Point point) {
                pointCount++;
                if (routeGuideUtil.exists(featureRepository.findFeature(point))) {
                    featureCount++;
                }
                // For each point after the first, add the incremental distance from the previous point to
                // the total distance value.
                if (previous != null) {
                    distance += routeGuideUtil.calcDistance(previous, point);
                }
                previous = point;
            }

            @Override
            public void onError(Throwable t) {
                logger.log(Level.WARNING, "recordRoute cancelled");
            }

            @Override
            public void onCompleted() {
                long seconds = NANOSECONDS.toSeconds(System.nanoTime() - startTime);
                responseObserver.onNext(RouteSummary.newBuilder().setPointCount(pointCount)
                        .setFeatureCount(featureCount).setDistance(distance)
                        .setElapsedTime((int) seconds).build());
                responseObserver.onCompleted();
            }
        };
    }

    /**
     * Receives a stream of message/location pairs, and responds with a stream of all previous
     * messages at each of those locations.
     *
     * @param responseObserver an observer to receive the stream of previous messages.
     * @return an observer to handle requested message/location pairs.
     */
    @Override
    public StreamObserver<RouteNote> routeChat(final StreamObserver<RouteNote> responseObserver) {
        return new StreamObserver<RouteNote>() {
            @Override
            public void onNext(RouteNote note) {
                List<RouteNote> notes = featureRepository.getOrCreateNotes(note.getLocation());

                // Respond with all previous notes at this location.
                for (RouteNote prevNote : notes.toArray(new RouteNote[0])) {
                    responseObserver.onNext(prevNote);
                }

                // Now add the new note to the list
                notes.add(note);
            }

            @Override
            public void onError(Throwable t) {
                logger.log(Level.WARNING, "routeChat cancelled");
            }

            @Override
            public void onCompleted() {
                responseObserver.onCompleted();
            }
        };
    }

}
