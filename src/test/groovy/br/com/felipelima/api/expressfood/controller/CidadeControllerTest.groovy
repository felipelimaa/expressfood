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

class CidadeControllerTest extends GeneralTest {

    @Autowired
    MockMvc mvc

    @Autowired
    ObjectMapper objectMapper

    @Autowired
    CidadeService cidadeService

    @Autowired
    EstadoService estadoService

    Estado buscaEstado(Long id) {
        return estadoService.findById(id)
    }

    //Testar a criação com sucesso
    @Test
    void cidade_InserirComSucesso() {
        def cidade = new Cidade(nome: "Monte Alegre", estado: buscaEstado(1))

        def response = mvc.perform(
                post("/cidades")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cidade)))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isCreated())
            .andReturn().response

        Cidade cidadeReturn = objectMapper.readerFor(Cidade.class).readValue(response.contentAsString)

        assertEquals(cidadeReturn.nome, cidade.nome)
        assertEquals(cidadeReturn.estado.id, cidade.estado.id)
        assertNotNull(cidadeReturn.id)


    }

    //Testar a criação sem nome
    @Test
    void cidade_InserirSemNome() {
        def cidade = new Cidade(nome: "", estado: buscaEstado(1))

        def response = mvc.perform(
            post("/cidades")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cidade)))
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
    void cidade_CriaERecuperaId(){
        def cidade = cidadeService.create(new Cidade(nome: "Monte Alegre", estado: buscaEstado(1)))

        def response = mvc.perform(get("/cidades/${cidade.id}"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk())
            .andReturn().response

        Cidade cidadeReturned = objectMapper.readerFor(Cidade.class).readValue(response.contentAsString)

        assertEquals(cidadeReturned.nome, cidade.nome)
        assertEquals(cidadeReturned.estado.id, cidade.estado.id)
        assertNotNull(cidadeReturned.id)
    }

    //Testar a recuperação de um ID invalido
    @Test
    void cidade_RecuperaIdInvalido(){
        def response = mvc.perform(get("/cidades/${Integer.MAX_VALUE}"))
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
    void cidade_ExcluirComSucesso() {
        def cidade = cidadeService.create(new Cidade(nome: "Monte Alegre", estado: buscaEstado(1)))

        def response = mvc.perform(delete("/cidades/${cidade.id}"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isNoContent())
            .andReturn().response
    }

    //Testar a exclusão de um ID invalido
    @Test
    void cidade_ExcluirInexistente() {
        def response = mvc.perform(delete("/cidades/${Integer.MAX_VALUE}"))
            .andDo(MockMvcResultHandlers.print())
            .andExpectAll(
                status().isNotFound(),
                content().contentType(MediaType.APPLICATION_JSON),
                jsonPath("title").value(ProblemExceptionType.RECURSO_NAO_ENCONTRADO.getTitle())
            )
            .andReturn().response
    }
}
