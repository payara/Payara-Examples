package fish.payara.example.polyglot.graalvm

import javax.ws.rs.ApplicationPath
import javax.ws.rs.core.Application

@ApplicationPath("/")
class ApplicationConfig : Application() {
    override fun getClasses() = setOf(HelloResource::class.java, ChromaResource::class.java)
}