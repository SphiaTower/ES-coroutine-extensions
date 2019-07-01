package io.sphia.es.coroutines.extensions

import org.elasticsearch.action.bulk.BulkRequest
import org.elasticsearch.action.delete.DeleteRequest
import org.elasticsearch.action.get.GetRequest
import org.elasticsearch.action.get.MultiGetRequest
import org.elasticsearch.action.index.IndexRequest
import org.elasticsearch.action.search.MultiSearchRequest
import org.elasticsearch.action.search.SearchRequest
import org.elasticsearch.client.RestHighLevelClient

suspend fun GetRequest.await(restHighLevelClient: RestHighLevelClient) = restHighLevelClient.awaitGet(this)
suspend fun MultiGetRequest.await(restHighLevelClient: RestHighLevelClient) = restHighLevelClient.awaitMultiGet(this)
suspend fun IndexRequest.await(restHighLevelClient: RestHighLevelClient) = restHighLevelClient.awaitIndex(this)
suspend fun DeleteRequest.await(restHighLevelClient: RestHighLevelClient) = restHighLevelClient.awaitDelete(this)
suspend fun BulkRequest.await(restHighLevelClient: RestHighLevelClient) = restHighLevelClient.awaitBulk(this)
suspend fun SearchRequest.await(restHighLevelClient: RestHighLevelClient) = restHighLevelClient.awaitSearch(this)
suspend fun MultiSearchRequest.await(restHighLevelClient: RestHighLevelClient) = restHighLevelClient.awaitMultiSearch(this)
