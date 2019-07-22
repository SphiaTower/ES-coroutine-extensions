package io.sphia.es.coroutines.extensions

import io.sphia.es.coroutines.CoroConfig
import io.sphia.es.coroutines.orm.DocumentAnalysis
import org.elasticsearch.action.bulk.BulkRequest
import org.elasticsearch.action.delete.DeleteRequest
import org.elasticsearch.action.get.GetRequest
import org.elasticsearch.action.get.MultiGetRequest
import org.elasticsearch.action.index.IndexRequest
import org.elasticsearch.action.search.ClearScrollRequest
import org.elasticsearch.action.search.MultiSearchRequest
import org.elasticsearch.action.search.SearchRequest
import org.elasticsearch.action.search.SearchScrollRequest
import org.elasticsearch.action.update.UpdateRequest
import org.elasticsearch.client.RestHighLevelClient

suspend fun GetRequest.await(restHighLevelClient: RestHighLevelClient) = restHighLevelClient.awaitGet(this)
suspend fun MultiGetRequest.await(restHighLevelClient: RestHighLevelClient) = restHighLevelClient.awaitMultiGet(this)
suspend fun IndexRequest.await(restHighLevelClient: RestHighLevelClient) = restHighLevelClient.awaitIndex(this)
suspend fun DeleteRequest.await(restHighLevelClient: RestHighLevelClient) = restHighLevelClient.awaitDelete(this)
suspend fun BulkRequest.await(restHighLevelClient: RestHighLevelClient) = restHighLevelClient.awaitBulk(this)
suspend fun SearchRequest.await(restHighLevelClient: RestHighLevelClient) = restHighLevelClient.awaitSearch(this)
suspend fun MultiSearchRequest.await(restHighLevelClient: RestHighLevelClient) = restHighLevelClient.awaitMultiSearch(this)
suspend fun UpdateRequest.await(restHighLevelClient: RestHighLevelClient) = restHighLevelClient.awaitUpdate(this)
suspend fun SearchScrollRequest.await(restHighLevelClient: RestHighLevelClient) = restHighLevelClient.awaitScroll(this)
suspend fun ClearScrollRequest.await(restHighLevelClient: RestHighLevelClient) = restHighLevelClient.awaitClearScroll(this)

fun IndexRequest.doc(doc: Any): IndexRequest {
    val id = DocumentAnalysis.analyzeCached(doc.javaClass).idField?.get(doc) as String?
    return this.source(CoroConfig.documentMapper.toSourceMap(doc)).id(id)
}
