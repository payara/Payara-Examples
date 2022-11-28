package fish.payara.example.polyglot.graalvm

import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.PathParam

/**
 * Hello World style Kotlin JAX-RS resource for testing purposes.
 */
@Path("/hello")
open class HelloResource {

    @GET
    @Path("/{name}")
    open fun read(@PathParam("name") name: String): String {
        return "Hello $name"


    }
}