package pl.borys.quiz.common.retrofit

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import java.lang.reflect.Type


class BooleanTypeAdapter : JsonDeserializer<Boolean> {
    @Throws(JsonParseException::class)
    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): Boolean? {
        val code = json.asInt
        return when (code) {
            0 -> false
            1 -> true
            else -> null
        }
    }
}
