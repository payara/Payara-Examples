package fish.payara.examples.microprofile.openapi;

import org.eclipse.microprofile.openapi.OASFactory;
import org.eclipse.microprofile.openapi.OASModelReader;
import org.eclipse.microprofile.openapi.models.OpenAPI;
import org.eclipse.microprofile.openapi.models.Operation;
import org.eclipse.microprofile.openapi.models.PathItem;

/**
 * Generates a base model to be used by the OpenAPI.
 */
public class ModelReader implements OASModelReader {

    @Override
    public OpenAPI buildModel() {
        PathItem item = OASFactory.createObject(PathItem.class)
                .GET(OASFactory.createObject(Operation.class)
                        .operationId("fake read resource"));
        OpenAPI result = OASFactory.createObject(OpenAPI.class);
        result.getPaths().addPathItem ("/api/fake/read", item);
        return result;
    }

}