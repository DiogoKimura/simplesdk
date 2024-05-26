package com.kimurashin.sdk.network.retrofit

import com.kimurashin.sdk.dsl.RetrofitDsl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@RetrofitDsl
class RetrofitBuilder {
    var baseUrl: String = ""
    var interceptors: List<Interceptor> = emptyList()
    fun okhttpClientBuilder(block: OKHttpBuilder.() -> Unit) {
        val builder = OKHttpBuilder()
        builder.block()
        interceptors = builder.interceptors
    }


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