package io.sphia.es.coroutines.extensions

import io.sphia.es.coroutines.CoroConfig
import io.sphia.es.coroutines.ESIndex
import org.elasticsearch.action.get.GetResponse
import org.elasticsearch.action.get.MultiGetResponse
import org.elasticsearch.action.search.MultiSearchResponse
import org.elasticsearch.action.search.SearchResponse
import kotlin.reflect.KClass


inline fun <reified T : Any> GetResponse.toDoc(): T? {
    return toDoc(T::class)
}

fun <T : Any> GetResponse.toDoc(type: KClass<T>): T? {
    return this.toDoc(type.java)
}

fun <T : Any> GetResponse.toDoc(type: Class<T>): T? {
    return if (this.isExists) {
        CoroConfig.documentMapper.fromSourceJson(this.sourceAsString, this.id, type)
    } else {
        null
    }
}

fun <T : Any> GetResponse.toDoc(index: ESIndex<T>): T? {
    return this.toDoc(index.docClass)
}


inline fun <reified T : Any> MultiGetResponse.toDocs(): List<T?> {
    return toDocs(T::class)
}

fun <T : Any> MultiGetResponse.toDocs(type: KClass<T>): List<T?> {
    return this.responses.map { it.response.toDoc(type) }
}

inline fun <reified T : Any> SearchResponse.toDocs(): List<T> {
    return toDocs(T::class)
}

fun <T : Any> SearchResponse.toDocs(type: KClass<T>): List<T> {
    return this.hits.map { CoroConfig.documentMapper.fromSourceJson(it.sourceAsString, it.id, type.java) }
}

inline fun <reified T : Any> MultiSearchResponse.parse(): List<List<T>> {
    return this.responses.map { it.response.toDocs(T::class) }
}

fun <T : Any> MultiSearchResponse.parse(type: KClass<T>): List<List<T>> {
    return this.responses.map { it.response.toDocs(type) }
}

