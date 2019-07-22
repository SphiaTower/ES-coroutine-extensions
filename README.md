# ES-coroutine-extensions
This library provides some lightweight and non-invasive extension methods for the Elasticsearch `RestHighLevelClient`, supporting ORM and Kotlin coroutines.

## The Design Principle
Keep it simple, stupid

## Await ES operations with Kotlin coroutines
```
// just a plain `RestHighLevelClient`
val client = RestHighLevelClient(RestClient.builder(HttpHost.create("http://abc.com:9200")))
val index = "sample_index"

GlobalScope.launch{
  // chain-style extension functions
  val getRequest: GetResponse = GetRequest(index).id("foo").routing("bar").await(client)
  
  // or call suspend functions directly
  client.awaitDelete(DeleteRequest(index, type).id("foo").routing("bar")

}
```

## Chain-style object mapping

```
GlobalScope.launch{
  // using reified type parameters
  val doc1: SampleDoc = GetRequest(index).id("foo").routing("bar").await(client).toDoc()
  
  // or specify the type explicitly
  val doc2 = GetRequest(index).id("foo").routing("bar").await(client).toDoc(SampleDoc::class.java)

} 
```
The `_id` field is handled automatically with `@DocumentID`
```
public class SampleDoc {

    // this field is recognized as the _id
    @DocumentID
    private String docID;

    private String field1;

    private int field2;

    // getters and setters
    // ...
}
```


## Change the default object mapper
```
CoroConfig.documentMapper = object : DocumentMapper {
    override fun toMapWithoutID(doc: Any): Map<String, Any> {
        TODO("not implemented") 
    }

    override fun <T> fromJsonWithID(json: String, id: String?, docClass: Class<T>): T {
        TODO("not implemented") 
    }

}
```

## Make request building easier
```
val sampleIndex = ESIndex("sample_index", "sample_type", SampleDoc::class.java)

val doc = SampleDoc("sampleID", "James", 56)

sampleIndex.indexRequest(doc).routing("James").await(client)

val doc2 = sampleIndex.getRequest("sampleID").routing("James").await(client).toDoc(sampleIndex)

```