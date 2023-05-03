package com.jroslar.listafacturasv02

import com.jroslar.listafacturasv02.data.FacturasRepository
import com.jroslar.listafacturasv02.data.model.DescEstado
import com.jroslar.listafacturasv02.data.model.FacturaModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jroslar.listafacturasv02.domain.GetFacturasFromApiUseCase
import org.robolectric.annotation.Config


@RunWith(AndroidJUnit4::class)
@Config(manifest=Config.NONE)
class GetFacturasFromApiUseCaseTest {

    @RelaxedMockK
    private lateinit var facturasRepository: FacturasRepository

    lateinit var getFacturasFromApiUseCase: GetFacturasFromApiUseCase

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        getFacturasFromApiUseCase = GetFacturasFromApiUseCase(facturasRepository)
    }

    @Test
    fun `when the api doesnt return anything then get database data`() = runBlocking {
        //Give
        coEvery { facturasRepository.getAllFacturasFromApi() } returns emptyList()

        //When
        getFacturasFromApiUseCase()

        //Then
        coVerify(exactly = 1) { facturasRepository.getAllFacturasLocal() }
    }
    @Test
    fun `when the api return something then get api data`() = runBlocking {
        //Give
        val myList = listOf(FacturaModel(DescEstado.pedientedepago.descEstado, 30.3F, "20/12/2018"))
        coEvery { facturasRepository.getAllFacturasFromApi() } returns myList

        //When
        val response = getFacturasFromApiUseCase()

        //Then
        coVerify(exactly = 1) { facturasRepository.clearFacturas() }
        coVerify(exactly = 1) { facturasRepository.insertFacturas(any()) }
        coVerify(exactly = 0) { facturasRepository.getAllFacturasLocal() }
        assert(myList == response)
    }
}