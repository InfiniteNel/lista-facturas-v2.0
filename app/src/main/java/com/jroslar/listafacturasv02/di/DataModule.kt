package com.jroslar.listafacturasv02.di

import co.infinum.retromock.Retromock
import com.jroslar.listafacturasv02.data.FacturasRepository
import com.jroslar.listafacturasv02.data.network.FacturasService
import com.jroslar.listafacturasv02.domain.GetFacturasFromApiUseCase
import com.jroslar.listafacturasv02.domain.GetFacturasLocalUseCase
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import org.koin.core.module.dsl.factoryOf
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val dataModule = module {
    single(named(Qualifier.ApiRequestListaFacturas)) { "https://viewnextandroid.wiremockapi.cloud/" }
    single {
        Retrofit.Builder()
        .baseUrl(get<String>(named(Qualifier.ApiRequestListaFacturas)))
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    }
    single {
        Retromock.Builder()
            .retrofit(get())
            .build()
    }
    single {
        HttpClient(Android) {
            install(Logging) {
                level = LogLevel.ALL
            }
            install(JsonFeature) {
                serializer = KotlinxSerializer()
            }
        }
    }

    factoryOf(::FacturasService)
    factoryOf(::FacturasRepository)

    factoryOf(::GetFacturasFromApiUseCase)
    factoryOf(::GetFacturasLocalUseCase)
}

enum class Qualifier {
    ApiRequestListaFacturas
}