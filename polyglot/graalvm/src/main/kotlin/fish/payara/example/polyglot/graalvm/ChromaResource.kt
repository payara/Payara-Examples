package fish.payara.example.polyglot.graalvm

import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.PathParam

/**
 * JAX-RS endpoint using the Chroma JavaScript library.
 */
@Path("/chroma")
open class ChromaResource {

    @GET
    @Path("/darken/{color}")
    open fun read(@PathParam("color") color: String): String {
        return Chroma.create(color).darken().hex()
    }
}