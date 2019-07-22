package io.sphia.es.coroutines.orm

interface DocumentMapper {
    fun toSourceMap(doc: Any): Map<String, Any>

    fun <T> fromSourceJson(json: String, id: String?, docClass: Class<T>): T

}
