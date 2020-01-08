package fish.payara.example.polyglot.graalvm

import org.graalvm.polyglot.Context
import org.graalvm.polyglot.Value

import java.nio.file.Files
import java.nio.file.Paths

/**
 * Load the JavaScript code and process it by Graal Context.
 */
class GraalVMChroma private constructor() {
    val chroma: Value

    init {
        val chromaResource = Thread.currentThread().contextClassLoader.getResource("chroma.min.js")
        val content = String(Files.readAllBytes(Paths.get(chromaResource!!.toURI())))
        val context = Context.create()
        context.eval("js", content)
        chroma = context.eval("js", "chroma")
    }

    companion object {
        private var instance: GraalVMChroma? = null

        fun create(): GraalVMChroma {
            if (instance == null) {
                instance =
                    GraalVMChroma()
            }
            return instance as GraalVMChroma
        }
    }
}
