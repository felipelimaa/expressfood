package br.com.felipelima.api.expressfood.controller

import br.com.felipelima.api.expressfood.api.exceptionHandler.ProblemExceptionType
import br.com.felipelima.api.expressfood.domain.model.Cozinha
import br.com.felipelima.api.expressfood.domain.model.Restaurante
import br.com.felipelima.api.expressfood.domain.service.CozinhaService
import br.com.felipelima.api.expressfood.domain.service.RestauranteService
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

    @Autowired
    RestauranteService restauranteService

    static Cozinha criaCozinha(){
        return new Cozinha(nome: "Indiana")
    }

    //Testar a criação com sucesso
    @Test
    void cozinha_InserirComSucesso() {
        def cozinha = criaCozinha()

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

    //Testar a criação sem nome
    @Test
    void cozinha_InserirSemNome() {
        def cozinha = new Cozinha(nome: "")

        def response = mvc.perform(
            post("/cozinhas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cozinha)))
            .andDo(MockMvcResultHandlers.print())
            .andExpectAll(
                status().isBadRequest(),
                content().contentType(MediaType.APPLICATION_JSON),
                jsonPath("title").value(ProblemExceptionType.DADOS_INVALIDOS.getTitle()),
                jsonPath("objects[*].name").value("nome")
            )
            .andReturn().response

    }

    //Testar a criação e recuperação de um ID
    @Test
    void cozinha_CriaERecuperaId() {
        def cozinha = cozinhaService.create(criaCozinha())

        def response = mvc.perform(get("/cozinhas/${cozinha.id}"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk())
            .andReturn().response

        Cozinha cozinhaReturn = objectMapper.readerFor(Cozinha.class).readValue(response.contentAsString)
        assertEquals(cozinhaReturn.nome, cozinha.nome)
        assertNotNull(cozinhaReturn.id)
    }

    //Testar a recuperação de um ID invalido
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

    //Testar a exclusão com sucesso
    @Test
    void cozinha_ExcluirComSucesso() {
        def cozinha = cozinhaService.create(criaCozinha())

        def response = mvc.perform(delete("/cozinhas/${cozinha.id}"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isNoContent())
            .andReturn().response
    }

    //Testar a exclusão da entidade em uso
    @Test
    void cozinha_ExcluirEmUso() {
        def cozinha = cozinhaService.create(criaCozinha())
        def restaurante = restauranteService.create(new Restaurante(nome: "Casa do Sabor", taxaFrete: 2.99, cozinha: cozinha))

        def response = mvc.perform(delete("/cozinhas/${cozinha.id}"))
            .andDo(MockMvcResultHandlers.print())
            .andExpectAll(
                status().isConflict(),
                content().contentType(MediaType.APPLICATION_JSON),
                jsonPath("title").value(ProblemExceptionType.ENTIDADE_EM_USO.getTitle())
            )
            .andReturn().response
    }

    //Testar a exclusão de um ID invalido
    @Test
    void cozinha_ExcluirInexistente() {
        def response = mvc.perform(delete("/cozinhas/${Integer.MAX_VALUE}"))
            .andDo(MockMvcResultHandlers.print())
            .andExpectAll(
                status().isNotFound(),
                content().contentType(MediaType.APPLICATION_JSON),
                jsonPath("title").value(ProblemExceptionType.RECURSO_NAO_ENCONTRADO.getTitle())
            )
            .andReturn().response
    }

}
