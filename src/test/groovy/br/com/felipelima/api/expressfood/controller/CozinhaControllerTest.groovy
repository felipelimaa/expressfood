package br.com.felipelima.api.expressfood.controller

import br.com.felipelima.api.expressfood.api.exceptionHandler.ProblemExceptionType
import br.com.felipelima.api.expressfood.domain.model.Cozinha
import br.com.felipelima.api.expressfood.domain.service.CozinhaService
import br.com.felipelima.api.expressfood.test.GeneralTest
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultHandlers

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import static org.junit.jupiter.api.Assertions.*

class CozinhaControllerTest extends GeneralTest {

    @Autowired
    MockMvc mvc

    @Autowired
    ObjectMapper objectMapper

    @Autowired
    CozinhaService cozinhaService

    @Test
    void cozinha_TestarCriacaoComSucesso() {
        def cozinha = new Cozinha(nome: "Indiana")

        def response = mvc.perform(
                post("/cozinhas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cozinha)))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(status().isCreated())
        .andReturn().response

        Cozinha cozinhaReturn = objectMapper.readerFor(Cozinha.class).readValue(response.contentAsString)
        assertEquals(cozinhaReturn.nome, cozinha.nome)
        assertNotNull(cozinhaReturn.id)
    }

    @Test
    void cozinha_CriaERecuperaId() {
        def cozinha = cozinhaService.create(new Cozinha(nome: "Indiana"))

        def response = mvc.perform(get("/cozinhas/${cozinha.id}"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk())
            .andReturn().response

        Cozinha cozinhaReturn = objectMapper.readerFor(Cozinha.class).readValue(response.contentAsString)
        assertEquals(cozinhaReturn.nome, cozinha.nome)
        assertNotNull(cozinhaReturn.id)
    }

    @Test
    void cozinha_RecuperaIdInvalido() {
        def response = mvc.perform(get("/cozinhas/${Integer.MAX_VALUE}"))
            .andDo(MockMvcResultHandlers.print())
            .andExpectAll (
                status().isNotFound() ,
                content().contentType(MediaType.APPLICATION_JSON) ,
                jsonPath("title").value(ProblemExceptionType.RECURSO_NAO_ENCONTRADO.getTitle())
            )
            .andReturn().response
    }

    @Test
    void cozinha_ExcluirEmUso() {
        def response = mvc.perform(delete("/cozinhas/${1L}"))
            .andDo(MockMvcResultHandlers.print())
            .andExpectAll(
                status().isConflict(),
                content().contentType(MediaType.APPLICATION_JSON),
                jsonPath("title").value(ProblemExceptionType.ENTIDADE_EM_USO.getTitle())
            )
            .andReturn().response
    }

    @Test
    void cozinha_ExcluirInexistente() {
        def response = mvc.perform(delete("/cozinhas/${Integer.MAX_VALUE}"))
            .andDo(MockMvcResultHandlers.print())
            .andExpectAll(
                status().isNotFound(),
                content().contentType(MediaType.APPLICATION_JSON),
                jsonPath("title").value(ProblemExceptionType.RECURSO_NAO_ENCONTRADO.getTitle())
            )
    }

}
