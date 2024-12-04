import android.net.Uri
import android.os.Bundle
import androidx.navigation.NavType
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import kotlin.reflect.KType

class SerializableNavType<T>(
    private val type: KType,
    isNullableAllowed: Boolean = false
) : NavType<T>(isNullableAllowed) where T : Any? {

    override fun get(bundle: Bundle, key: String): T {
        return bundle.getString(key)?.let { value ->
            Json.decodeFromString(
                deserializer = serializer(type),
                string = value
            ) as T
        } as T
    }

    override fun parseValue(value: String): T {
        return if (value == "null") {
            null as T
        } else {
            Json.decodeFromString(
                deserializer = serializer(type),
                string = Uri.decode(value)
            ) as T
        }
    }

    override fun put(bundle: Bundle, key: String, value: T) {
        if (value == null) {
            bundle.putString(key, null)
        } else {
            bundle.putString(
                key,
                Json.encodeToString(
                    serializer = serializer(type),
                    value = value
                )
            )
        }
    }

    override fun serializeAsValue(value: T): String {
        return if (value == null) {
            "null"
        } else {
            Uri.encode(
                Json.encodeToString(
                    serializer = serializer(type),
                    value = value
                )
            )
        }
    }
}