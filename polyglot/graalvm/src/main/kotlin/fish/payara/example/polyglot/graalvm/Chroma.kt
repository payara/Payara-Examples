package fish.payara.example.polyglot.graalvm

import org.graalvm.polyglot.Value

import java.util.ArrayList

/**
 * Call the JavaScript members.
 */
class Chroma(private val chromaInstance: Value) {

    fun darken(): Chroma {
        return Chroma(chromaInstance.getMember("darken").execute())
    }

    fun saturate(value: Int): Chroma {
        return Chroma(chromaInstance.getMember("saturate").execute(value))
    }

    fun hex(): String {
        return chromaInstance.getMember("hex").execute().asString()
    }

    fun rgba(): List<Int> {
        val result = ArrayList<Int>()
        val value = chromaInstance.getMember("rgba").execute()
        if (value.hasArrayElements()) {
            for (i in 0 until value.arraySize) {
                result.add(value.getArrayElement(i).asInt())
            }
        }
        return result
    }

    companion object {

        internal fun create(color: String): Chroma {
            val chroma = GraalVMChroma.create().chroma
            val instance = chroma.newInstance(color)
            return Chroma(instance)
        }
    }
}
