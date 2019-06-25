package io.sphia.es.coroutines.extensions

import org.apache.http.Header
import org.elasticsearch.action.ActionListener
import org.elasticsearch.action.ActionRequest
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

suspend inline fun <T : ActionRequest, R> cpsConvert(request: T,
                                                     crossinline function: AsyncClientFunction<T, R>,
                                                     headers: Array<out Header>): R {
    return suspendCoroutine {
        function(request, object : ActionListener<R> {
            override fun onFailure(exception: Exception) {
                it.resumeWithException(exception)
            }

            override fun onResponse(response: R) {
                it.resume(response)
            }

        }, headers)
    }
}

typealias AsyncClientFunction<T, R> = Function3<
        @ParameterName(name = "req") T,
        @ParameterName(name = "listener") ActionListener<R>,
        @ParameterName(name = "headers") Array<out Header>,
        Unit>