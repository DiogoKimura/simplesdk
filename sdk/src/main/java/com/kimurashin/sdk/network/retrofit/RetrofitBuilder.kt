package com.kimurashin.sdk.network.retrofit

import com.kimurashin.sdk.dsl.RetrofitDsl
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@RetrofitDsl
class RetrofitBuilder {
    var baseUrl: String = ""

    fun build() = Retrofit.Builder().apply {
        baseUrl(baseUrl)
        addConverterFactory(GsonConverterFactory.create())
    }.build()
}

@RetrofitDsl
fun retrofit(block: RetrofitBuilder.() -> Unit): Retrofit {
    val builder = RetrofitBuilder()
    builder.block()
    return builder.build()
}