package io.sphia.es.coroutines.extensions

import org.apache.http.Header
import org.elasticsearch.action.bulk.BulkRequest
import org.elasticsearch.action.bulk.BulkResponse
import org.elasticsearch.action.delete.DeleteRequest
import org.elasticsearch.action.delete.DeleteResponse
import org.elasticsearch.action.get.GetRequest
import org.elasticsearch.action.get.GetResponse
import org.elasticsearch.action.get.MultiGetRequest
import org.elasticsearch.action.get.MultiGetResponse
import org.elasticsearch.action.index.IndexRequest
import org.elasticsearch.action.index.IndexResponse
import org.elasticsearch.action.search.*
import org.elasticsearch.action.update.UpdateRequest
import org.elasticsearch.action.update.UpdateResponse
import org.elasticsearch.client.RestHighLevelClient


suspend fun RestHighLevelClient.awaitGet(getRequest: GetRequest, vararg headers: Header): GetResponse {
    return cpsConvert(getRequest, this::getAsync, headers)
}

suspend fun RestHighLevelClient.awaitSearch(searchRequest: SearchRequest, vararg headers: Header): SearchResponse {
    return cpsConvert(searchRequest, this::searchAsync, headers)
}

suspend fun RestHighLevelClient.awaitDelete(deleteRequest: DeleteRequest, vararg headers: Header): DeleteResponse {
    return cpsConvert(deleteRequest, this::deleteAsync, headers)
}

suspend fun RestHighLevelClient.awaitMultiGet(multiGetRequest: MultiGetRequest, vararg headers: Header): MultiGetResponse {
    return cpsConvert(multiGetRequest, this::multiGetAsync, headers)
}

suspend fun RestHighLevelClient.awaitMultiSearch(multiSearchRequest: MultiSearchRequest, vararg headers: Header): MultiSearchResponse {
    return cpsConvert(multiSearchRequest, this::multiSearchAsync, headers)
}

suspend fun RestHighLevelClient.awaitIndex(indexRequest: IndexRequest, vararg headers: Header): IndexResponse {
    return cpsConvert(indexRequest, this::indexAsync, headers)
}

suspend fun RestHighLevelClient.awaitUpdate(updateRequest: UpdateRequest, vararg headers: Header): UpdateResponse {
    return cpsConvert(updateRequest, this::updateAsync, headers)
}

suspend fun RestHighLevelClient.awaitBulk(bulkRequest: BulkRequest, vararg headers: Header): BulkResponse {
    return cpsConvert(bulkRequest, this::bulkAsync, headers)
}

suspend fun RestHighLevelClient.awaitExists(existsRequest: GetRequest, vararg headers: Header): Boolean {
    return cpsConvert(existsRequest, this::existsAsync, headers)
}

suspend fun RestHighLevelClient.awaitScroll(scrollRequest: SearchScrollRequest, vararg headers: Header): SearchResponse {
    return cpsConvert(scrollRequest, this::searchScrollAsync, headers)
}

suspend fun RestHighLevelClient.awaitClearScroll(clearScrollRequest: ClearScrollRequest, vararg headers: Header): ClearScrollResponse {
    return cpsConvert(clearScrollRequest, this::clearScrollAsync, headers)
}

