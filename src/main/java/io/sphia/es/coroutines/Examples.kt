package io.sphia.es.coroutines

import io.sphia.es.coroutines.annotations.DocumentID
import io.sphia.es.coroutines.extensions.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.apache.http.HttpHost
import org.elasticsearch.action.get.GetRequest
import org.elasticsearch.action.get.GetResponse
import org.elasticsearch.action.get.MultiGetRequest
import org.elasticsearch.action.index.IndexRequest
import org.elasticsearch.action.search.SearchRequest
import org.elasticsearch.client.RestClient
import org.elasticsearch.client.RestHighLevelClient
import org.elasticsearch.index.query.BoolQueryBuilder
import org.elasticsearch.search.builder.SearchSourceBuilder
import java.time.Instant

internal class Examples {

    data class SampleDoc(
            @DocumentID val docID: String,
            val name: String,
            val weight: Int,
            val birthDay: Instant
    )

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val index = "sample_index"
            val type = "_doc"

            val sampleIndex = ESIndex(index, type, SampleDoc::class.java)
            val client = RestHighLevelClient(RestClient.builder(HttpHost.create("http://abc.com:9200")))


            val doc = SampleDoc("abc", "James", 56, Instant.now())


            GlobalScope.launch {
                client.awaitIndex(IndexRequest(index, type).doc(doc).routing(doc.name).create(true))

                val getRequest = GetRequest(index).id("abc").routing("James")

                val sampleDoc: SampleDoc? = client.awaitGet(getRequest).toDoc()
                val sampleDoc2 = client.awaitGet(getRequest).toDoc(SampleDoc::class)

                val hits = client.awaitSearch(SearchRequest(index).apply {
                    source(SearchSourceBuilder.searchSource().query(BoolQueryBuilder()))
                    requestCache(true)
                }).toDocs(SampleDoc::class)

                val sampleDocs: List<SampleDoc?> = client.awaitMultiGet(MultiGetRequest().add(index, type, "abc")).toDocs()

                sampleIndex.indexRequest(doc).await(client)
            }


        }
    }
}