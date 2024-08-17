package com.kimurashin.sdk.network.retrofit

import com.kimurashin.sdk.dsl.RetrofitDsl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Builder for retrofit
 *
 * This is a DSL to simplify your retrofit build
 *
 * @property baseUrl
 * @property interceptors input your list of [okhttp3.Interceptor]
 *
 * Example usage
 * ```
 * val retrofit = buildRetrofit {
 *     baseUrl = "www.example.com"
 *     okhttpClientBuilder {
 *         addInterceptors(::interceptorList)
 *     }
 * }
 *
 * private fun interceptorList() = listOf(
 *     HttpLoggingInterceptor().apply { setLevel(HttpLoggingInterceptor.Level.BODY) }
 * )
 * ```
 */
@RetrofitDsl
class RetrofitBuilder {
    var baseUrl: String = ""
    var interceptors: List<Interceptor> = emptyList()

    /**
     * Inner builder to add Okhttp configurations
     */
    fun okhttpClientBuilder(block: OKHttpBuilder.() -> Unit) {
        val builder = OKHttpBuilder()
        builder.block()
        interceptors = builder.interceptors
    }

    /**
     * Builds every previous configurations that has been made
     */
    fun build() = Retrofit.Builder().apply {
        baseUrl(baseUrl)
        addConverterFactory(GsonConverterFactory.create())
    }.build()
}

@RetrofitDsl
class OKHttpBuilder {
    var interceptors: List<Interceptor> = emptyList()
    fun addInterceptors(block: () -> List<Interceptor>) {
        interceptors = block.invoke()
    }

    fun build() = OkHttpClient().newBuilder().apply {
        interceptors.forEach { addInterceptor(it) }
    }.build()
}

@RetrofitDsl
fun buildRetrofit(block: RetrofitBuilder.() -> Unit): Retrofit {
    val builder = RetrofitBuilder()
    builder.block()
    return builder.build()
}