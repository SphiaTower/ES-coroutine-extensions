package io.sphia.es.coroutines.orm

import io.sphia.es.coroutines.annotations.DocumentID
import io.sphia.es.coroutines.annotations.DocumentRouting
import java.lang.reflect.Field
import java.lang.reflect.Modifier
import java.util.concurrent.ConcurrentHashMap

class DocumentAnalysis private constructor(
        val documentClass: Class<*>
) {
    companion object {
        private val cache = ConcurrentHashMap<Class<*>, DocumentAnalysis>()

        fun analyzeCached(documentClass: Class<*>): DocumentAnalysis {
            return cache.computeIfAbsent(documentClass) { DocumentAnalysis(documentClass) }
        }

    }

    val idField: Field?
    val routingField: Field?
    val documentFields: List<Field>
    val allFields: List<Field>

    init {
        this.allFields = documentClass.declaredFields.filterNot { Modifier.isStatic(it.modifiers) }.onEach { it.isAccessible = true }

        val (idFields, documentFields) = allFields.partition { it.isAnnotationPresent(DocumentID::class.java) }
        this.idField = idFields.singleOrNull()
        this.documentFields = documentFields

        this.routingField = allFields.find { it.isAnnotationPresent(DocumentRouting::class.java) }

        cache[documentClass] = this
    }
}
