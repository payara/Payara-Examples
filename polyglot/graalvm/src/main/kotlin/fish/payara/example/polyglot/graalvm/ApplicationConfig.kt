package fish.payara.example.polyglot.graalvm

import jakarta.ws.rs.ApplicationPath
import jakarta.ws.rs.core.Application

@ApplicationPath("/")
class ApplicationConfig : Application() {
    override fun getClasses() = setOf(HelloResource::class.java, ChromaResource::class.java)
}