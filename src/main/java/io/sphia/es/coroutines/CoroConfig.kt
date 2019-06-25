package io.sphia.es.coroutines

import io.sphia.es.coroutines.orm.DocumentMapper
import io.sphia.es.coroutines.orm.DocumentMapperImpl

object CoroConfig {

    var documentMapper: DocumentMapper = DocumentMapperImpl()
}