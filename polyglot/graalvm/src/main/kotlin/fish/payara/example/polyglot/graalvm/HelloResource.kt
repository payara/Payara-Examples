package fish.payara.example.polyglot.graalvm

import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.PathParam

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