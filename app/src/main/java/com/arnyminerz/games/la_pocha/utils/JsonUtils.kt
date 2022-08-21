package com.arnyminerz.games.la_pocha.utils

import org.json.JSONArray
import org.json.JSONObject

/**
 * Indicates that the class can be serialized into a [JSONObject].
 * @author Arnau Mora
 * @since 20220821
 */
interface JsonSerializable {
    /**
     * Serializes the class into a [JSONObject].
     * @author Arnau Mora
     * @since 20220821
     * @return A new [JSONObject] instance with the data of the class.
     */
    fun toJson(): JSONObject
}

/**
 * Used in companion objects to tell the class that it can be serialized into [T] from a [JSONObject].
 * @author Arnau Mora
 * @since 20220821
 */
interface JsonSerializer<T> {
    /**
     * Instantiates a new instance of [T] from a [JSONObject].
     * @author Arnau Mora
     * @since 20220821
     * @param json The [JSONObject] with the data of the class.
     */
    fun fromJson(json: JSONObject): T
}

/**
 * Maps a JSON Array into a new type.
 * @author Arnau Mora
 * @since 20220821
 * @param getter Used for obtaining the value at index from the array.
 * @param iterator The constructor for the mapped items.
 * @see INT_GETTER
 * @see STRING_GETTER
 * @see JSON_OBJECT_GETTER
 */
fun <I, O> JSONArray.map(
    getter: JsonGetter<I>,
    iterator: (item: I) -> O,
): List<O> = arrayListOf<O>().apply {
    for (i in 0 until length())
        add(iterator(getter(this@map, i)))
}

/**
 * Used with [JSONArray.map] to instantiate the elements of the array.
 * @author Arnau Mora
 * @since 20220821
 * @param T The return type of the getter, this is, the type that the getter constructs. Must be one
 * of the getters from a [JSONArray].
 * @see JSONArray.getJSONObject
 * @see JSONArray.getJSONArray
 * @see JSONArray.getString
 * @see JSONArray.getInt
 * @see JSONArray.getBoolean
 * @see JSONArray.getDouble
 * @see JSONArray.getLong
 */
interface JsonGetter<T> {
    operator fun invoke(array: JSONArray, index: Int): T
}

val INT_GETTER = object : JsonGetter<Int> {
    override fun invoke(array: JSONArray, index: Int) = array.getInt(index)
}

val STRING_GETTER = object : JsonGetter<String> {
    override fun invoke(array: JSONArray, index: Int) = array.getString(index)
}

val JSON_OBJECT_GETTER = object : JsonGetter<JSONObject> {
    override fun invoke(array: JSONArray, index: Int) = array.getJSONObject(index)
}

/**
 * Converts a list of [JsonSerializable] to a [JSONArray].
 * @author Arnau Mora
 * @since 20220821
 * @return A [JSONArray] with the same elements than [this] but converted into JSON.
 */
fun <T : JsonSerializable> Collection<T>.toJson(): JSONArray = JSONArray().apply {
    forEach { put(it.toJson()) }
}
