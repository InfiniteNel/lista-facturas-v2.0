package com.jroslar.listafacturasv02

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jroslar.listafacturasv02.data.DetallesRepository
import com.jroslar.listafacturasv02.data.model.DetallesModel
import com.jroslar.listafacturasv02.domain.GetDetallesFromApiUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(manifest= Config.NONE)
class GetDetallesFromApiUseCaseTest {
    @RelaxedMockK
    private lateinit var detallesRepository: DetallesRepository

    lateinit var getDetallesFromApiUseCase: GetDetallesFromApiUseCase

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        getDetallesFromApiUseCase = GetDetallesFromApiUseCase(detallesRepository)
    }

    @Test
    fun `when the api return something then get api data`() = runBlocking {
        //Give
        val myData = DetallesModel(
            "ES0021000000001994LJ1FA000",
            "No hemos recibido ninguna solicitud de autoconsumo",
            "Con excedentes y compensación Individual - Consumo",
            "Precio PVPC",
            "5kWp"
        )
        coEvery { detallesRepository.getDetallesFromApi() } returns myData

        //When
        val response = getDetallesFromApiUseCase()

        //Then
        coVerify(exactly = 1) { detallesRepository.getDetallesFromApi() }
        assert(myData == response)
    }
}