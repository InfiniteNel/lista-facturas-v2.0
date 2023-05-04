package com.jroslar.listafacturasv02.di

import co.infinum.retromock.Retromock
import com.jroslar.listafacturasv02.core.Constantes.Companion.URL_SERVIDOR_FACTURAS
import com.jroslar.listafacturasv02.data.DetallesRepository
import com.jroslar.listafacturasv02.data.FacturasRepository
import com.jroslar.listafacturasv02.data.network.DetallesService
import com.jroslar.listafacturasv02.data.network.FacturasService
import com.jroslar.listafacturasv02.domain.GetDetallesFromApiUseCase
import com.jroslar.listafacturasv02.domain.GetFacturasFromApiUseCase
import com.jroslar.listafacturasv02.domain.GetFacturasLocalUseCase
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.http.*
import org.koin.core.module.dsl.factoryOf
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val dataModule = module {
    single(named(Qualifier.ApiRequestListaFacturas)) { URL_SERVIDOR_FACTURAS }
    single {
        Retrofit.Builder()
        .baseUrl(get<String>(named(Qualifier.ApiRequestListaFacturas)))
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    }
    single(named(Qualifier.FacturasRetromock)) {
        Retromock.Builder()
            .retrofit(get())
            .build()
    }
    single(named(Qualifier.DetallesRetromock)) {
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
                val json = kotlinx.serialization.json.Json { ignoreUnknownKeys = true }
                serializer = KotlinxSerializer(json)
                acceptContentTypes = acceptContentTypes + ContentType.Any
            }
        }
    }

    factory { FacturasService(get(), get<Retromock>(named(Qualifier.FacturasRetromock)), get()) }
    factory { DetallesService(get(), get<Retromock>(named(Qualifier.DetallesRetromock))) }
    factoryOf(::FacturasRepository)
    factoryOf(::DetallesRepository)

    factoryOf(::GetFacturasFromApiUseCase)
    factoryOf(::GetFacturasLocalUseCase)
    factoryOf(::GetDetallesFromApiUseCase)
}

enum class Qualifier {
    ApiRequestListaFacturas,
    ApiRequestDetalles,
    DetallesRetromock,
    FacturasRetromock
}