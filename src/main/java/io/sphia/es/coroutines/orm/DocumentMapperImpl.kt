package io.sphia.es.coroutines.orm

import com.google.gson.Gson


class DocumentMapperImpl : DocumentMapper {

    val gson = Gson()

    override fun toSourceMap(doc: Any): Map<String, Any> {
        return toMapWithoutID(doc, DocumentAnalysis.analyzeCached(doc.javaClass))
    }

    private fun toMapWithoutID(doc: Any, analysis: DocumentAnalysis): Map<String, Any> {

        return analysis.documentFields.mapNotNull {
            val value = it.get(doc)
            if (value == null) {
                null
            } else {
                it.name to value
            }
        }.toMap()

    }

    override fun <T> fromSourceJson(json: String, id: String?, docClass: Class<T>): T {
        val obj = gson.fromJson(json, docClass)
        if (id != null) {
            DocumentAnalysis.analyzeCached(docClass).idField?.set(obj, id)
        }
        return obj
    }


}