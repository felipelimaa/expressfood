package br.com.felipelima.api.expressfood.controller

import br.com.felipelima.api.expressfood.api.exceptionHandler.ProblemExceptionType
import br.com.felipelima.api.expressfood.domain.model.Cidade
import br.com.felipelima.api.expressfood.domain.model.Estado
import br.com.felipelima.api.expressfood.domain.service.CidadeService
import br.com.felipelima.api.expressfood.domain.service.EstadoService
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

class EstadoControllerTest extends GeneralTest {

    @Autowired
    MockMvc mvc

    @Autowired
    ObjectMapper objectMapper

    @Autowired
    EstadoService estadoService

    @Autowired
    CidadeService cidadeService

    Estado criaEstado(){
        return new Estado(nome: "Rio Grande do Norte", uf: "RN")
    }

    //Testar a criação com sucesso
    @Test
    void estado_InserirComSucesso() {
        def estado = criaEstado()

        def response = mvc.perform(
            post("/estados")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(estado)))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isCreated())
            .andReturn().response

        Estado estadoReturn = objectMapper.readerFor(Estado.class).readValue(response.contentAsString)

        assertEquals(estadoReturn.nome, estado.nome)
        assertEquals(estadoReturn.uf, estado.uf)
        assertNotNull(estadoReturn.id)
    }

    //Testar a criação sem nome
    @Test
    void estado_InserirSemNome() {
        def estado = new Estado(nome: "", uf: "RN")

        def response = mvc.perform(
            post("/estados")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(estado)))
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
    void estado_CriaERecuperaId(){
        def estado = estadoService.create(criaEstado())

        def response = mvc.perform(get("/estados/${estado.id}"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk())
            .andReturn().response

        Estado estadoReturned = objectMapper.readerFor(Estado.class).readValue(response.contentAsString)

        assertEquals(estadoReturned.nome, estado.nome)
        assertEquals(estadoReturned.uf, estado.uf)
        assertNotNull(estadoReturned.id)
    }

    //Testar a recuperação de um ID invalido
    @Test
    void estado_RecuperaIdInvalido(){
        def response = mvc.perform(get("/estados/${Integer.MAX_VALUE}"))
            .andDo(MockMvcResultHandlers.print())
            .andExpectAll(
                status().isNotFound(),
                content().contentType(MediaType.APPLICATION_JSON),
                jsonPath("title").value(ProblemExceptionType.RECURSO_NAO_ENCONTRADO.getTitle())
            )
            .andReturn().response
    }

    //Testar a exclusão com sucesso
    @Test
    void estado_ExcluirComSucesso() {
        def estado = estadoService.create(criaEstado())

        def response = mvc.perform(delete("/estados/${estado.id}"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isNoContent())
            .andReturn().response
    }

    //Testar a exclusão de um ID invalido
    @Test
    void estado_ExcluirInexistente() {
        def response = mvc.perform(delete("/estados/${Integer.MAX_VALUE}"))
            .andDo(MockMvcResultHandlers.print())
            .andExpectAll(
                status().isNotFound(),
                content().contentType(MediaType.APPLICATION_JSON),
                jsonPath("title").value(ProblemExceptionType.RECURSO_NAO_ENCONTRADO.getTitle())
            )
            .andReturn().response
    }

    //Testar a exclusão da entidade em uso
    @Test
    void estado_ExcluirEmUso(){
        def estado = estadoService.create(criaEstado())
        def cidade = cidadeService.create(new Cidade(nome: "Monte Alegre", estado: estado))

        def response = mvc.perform(delete("/estados/${estado.id}"))
            .andDo(MockMvcResultHandlers.print())
            .andExpectAll(
                status().isConflict(),
                content().contentType(MediaType.APPLICATION_JSON),
                jsonPath("title").value(ProblemExceptionType.ENTIDADE_EM_USO.getTitle())
            )
            .andReturn().response
    }
}
