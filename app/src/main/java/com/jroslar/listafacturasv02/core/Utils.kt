package com.jroslar.listafacturasv02.core

var stateServer: StatesServer = StatesServer.Retrofit

enum class StatesServer {
    Retrofit,
    Retromock,
    Ktor
}