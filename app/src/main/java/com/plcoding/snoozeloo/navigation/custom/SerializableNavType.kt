import android.net.Uri
import android.os.Bundle
import androidx.navigation.NavType
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import kotlin.reflect.KType

class SerializableNavType<T: Any>(
    private val type: KType,
    isNullableAllowed: Boolean = false
) : NavType<T>(isNullableAllowed) {

    @Suppress("UNCHECKED_CAST")
    override fun get(bundle: Bundle, key: String): T {
        return Json.decodeFromString(
            deserializer = serializer(type),
            string = bundle.getString(key) ?: throw IllegalArgumentException("Null not allowed")
        ) as T
    }

    @Suppress("UNCHECKED_CAST")
    override fun parseValue(value: String): T {
        return Json.decodeFromString(
            deserializer = serializer(type),
            string = Uri.decode(value)
        ) as T
    }

    override fun put(bundle: Bundle, key: String, value: T) {
        bundle.putString(
            key,
            Json.encodeToString(
                serializer = serializer(type),
                value = value
            )
        )
    }

    override fun serializeAsValue(value: T): String {
        return Uri.encode(
            Json.encodeToString(
                serializer = serializer(type),
                value = value
            )
        )
    }
}