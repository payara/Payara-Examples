package fish.payara.example.polyglot.graalvm

import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.PathParam

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