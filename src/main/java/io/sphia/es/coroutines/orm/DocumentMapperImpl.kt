package io.sphia.es.coroutines.orm


class DocumentMapperImpl : DocumentMapper {
    override fun toMapWithoutID(doc: Any): Map<String, Any> {
        return toMapWithoutID(doc, DocumentAnalysis.analyzeCached(doc.javaClass))
    }

    fun toMapWithoutID(doc: Any, analysis: DocumentAnalysis): Map<String, Any> {

        return analysis.documentFields.mapNotNull {
            val value = it.get(doc)
            if (value == null) {
                null
            } else {
                it.name to value
            }
        }.toMap()

    }

    override fun <T> fromJsonWithID(json: String, id: String?, docClass: Class<T>): T {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}