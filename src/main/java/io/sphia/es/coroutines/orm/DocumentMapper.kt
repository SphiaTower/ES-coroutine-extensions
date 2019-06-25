package io.sphia.es.coroutines.orm

interface DocumentMapper {
    fun toMapWithoutID(doc: Any): Map<String, Any>

    fun <T> fromJsonWithID(json: String, id: String?, docClass: Class<T>): T

}
