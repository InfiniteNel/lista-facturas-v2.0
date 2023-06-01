package com.jroslar.listafacturasv02.di

import co.infinum.retromock.Retromock
import com.google.firebase.auth.FirebaseAuth
import com.jroslar.listafacturasv02.core.Constantes.Companion.URL_SERVIDOR_DETALLES
import com.jroslar.listafacturasv02.core.Constantes.Companion.URL_SERVIDOR_FACTURAS
import com.jroslar.listafacturasv02.data.DetallesRepository
import com.jroslar.listafacturasv02.data.FacturasRepository
import com.jroslar.listafacturasv02.data.network.DetallesService
import com.jroslar.listafacturasv02.data.network.FacturasService
import com.jroslar.listafacturasv02.data.network.FirebaseService
import com.jroslar.listafacturasv02.domain.*
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.http.*
import kotlinx.coroutines.Dispatchers
import org.koin.core.module.dsl.factoryOf
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val dataModule = module {
    single(named(Qualifier.FacturasRetrofit)) {
        Retrofit.Builder()
        .baseUrl(URL_SERVIDOR_FACTURAS)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    }
    single(named(Qualifier.DetallesRetrofit)) {
        Retrofit.Builder()
            .baseUrl(URL_SERVIDOR_DETALLES)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    single(named(Qualifier.FacturasRetromock)) {
        Retromock.Builder()
            .retrofit(get(named(Qualifier.FacturasRetrofit)))
            .build()
    }
    single(named(Qualifier.DetallesRetromock)) {
        Retromock.Builder()
            .retrofit(get(named(Qualifier.DetallesRetrofit)))
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
    single {
        Dispatchers.IO
    }
    single {
        FirebaseAuth.getInstance()
    }

    factory { FacturasService(get(named(Qualifier.FacturasRetrofit)), get(named(Qualifier.FacturasRetromock)), get()) }
    factory { DetallesService(get(named(Qualifier.DetallesRetrofit)), get(named(Qualifier.DetallesRetromock))) }
    factoryOf(::FirebaseService)

    factoryOf(::FacturasRepository)
    factoryOf(::DetallesRepository)

    factoryOf(::GetFacturasFromApiUseCase)
    factoryOf(::GetFacturasLocalUseCase)
    factoryOf(::GetDetallesFromApiUseCase)
    factoryOf(::CreateAccountUseCase)
    factoryOf(::LoginUseCase)
    factoryOf(::SendEmailResetPasswordUseCase)
}

enum class Qualifier {
    FacturasRetrofit,
    DetallesRetrofit,
    DetallesRetromock,
    FacturasRetromock
}