package com.jroslar.listafacturasv02

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jroslar.listafacturasv02.data.FacturasRepository
import com.jroslar.listafacturasv02.core.Constantes.Companion.DescEstado
import com.jroslar.listafacturasv02.data.model.FacturaModel
import com.jroslar.listafacturasv02.domain.GetFacturasLocalUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(manifest= Config.NONE)
class GetFacturasLocalUseCaseTest {

    @RelaxedMockK
    private lateinit var facturasRepository: FacturasRepository

    lateinit var getFacturasLocalUseCase: GetFacturasLocalUseCase

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        getFacturasLocalUseCase = GetFacturasLocalUseCase(facturasRepository)
    }

    @Test
    fun `when database is empty then return emptylist`() = runBlocking {
        //Give
        coEvery { facturasRepository.getAllFacturasLocal() } returns emptyList()

        //When
        val response = getFacturasLocalUseCase()

        //Then
        assert(response.isEmpty())
    }
    @Test
    fun `when database is not empty then return list facturas`() = runBlocking {
        //Give
        val myList = listOf(FacturaModel(DescEstado.PedienteDePago.descEstado, 30.3F, "20/12/2018"))
        coEvery { facturasRepository.getAllFacturasLocal() } returns myList

        //When
        val response = getFacturasLocalUseCase()

        //Then
        assert(myList == response)
    }
}