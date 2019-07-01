package io.sphia.es.coroutines

import io.sphia.es.coroutines.extensions.doc
import org.elasticsearch.action.bulk.BulkRequest
import org.elasticsearch.action.delete.DeleteRequest
import org.elasticsearch.action.get.GetRequest
import org.elasticsearch.action.get.MultiGetRequest
import org.elasticsearch.action.index.IndexRequest
import org.elasticsearch.action.search.MultiSearchRequest
import org.elasticsearch.action.search.SearchRequest
import org.elasticsearch.action.search.SearchScrollRequest
import org.elasticsearch.search.builder.SearchSourceBuilder

data class ESIndex<T : Any>(val index: String, val type: String = "_doc", val docClass: Class<T>) {

    fun indexRequest() = IndexRequest(index, type)

    fun indexRequest(doc: T) = IndexRequest(index, type).doc(doc)

    fun getRequest(id: String) = GetRequest(index, type, id)

    fun multiGetRequest() = MultiGetRequest()

    fun searchRequest(source: SearchSourceBuilder) = SearchRequest().indices(index).types(type).source(source)

    fun multiSearchRequest() = MultiSearchRequest()

    fun deleteRequest(id: String) = DeleteRequest(index, type, id)

    fun searchScrollRequest(scrollId: String) = SearchScrollRequest(scrollId)

    fun bulkRequest() = BulkRequest()


}
