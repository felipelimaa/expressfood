package br.com.felipelima.api.expressfood.service

import br.com.felipelima.api.expressfood.domain.exception.EntidadeEmUsoException
import br.com.felipelima.api.expressfood.domain.exception.EntidadeNotFoundException
import br.com.felipelima.api.expressfood.domain.model.Cidade
import br.com.felipelima.api.expressfood.domain.model.Estado
import br.com.felipelima.api.expressfood.domain.service.CidadeService
import br.com.felipelima.api.expressfood.domain.service.EstadoService
import br.com.felipelima.api.expressfood.test.GeneralTest
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

import javax.validation.ConstraintViolationException

import static org.junit.jupiter.api.Assertions.*

class EstadoServiceTest extends GeneralTest {

    @Autowired
    EstadoService estadoService

    @Autowired
    CidadeService cidadeService

    //TODO: Testar a criação com sucesso
    @Test
    void estado_InserirComSucesso() {
        Estado estado = new Estado(nome: "Rio Grande do Norte", uf: "RN")
        Estado estadoCreated = estadoService.create(estado)

        assertEquals(estadoCreated.nome, estado.nome)
        assertEquals(estadoCreated.uf, estado.uf)
        assertTrue(estadoCreated.id > 0)
    }

    //TODO: Testar a criação sem nome
    @Test
    void estado_InserirSemNome() {
        Estado estado = new Estado(nome: "", uf: "RN")
        assertThrows(ConstraintViolationException.class, { estadoService.create(estado) })
    }

    //TODO: Testar a criação e recuperação de um ID
    @Test
    void estado_CriaERecuperaId(){
        Estado estado = estadoService.create(new Estado(nome: "Rio Grande do Norte", uf: "RN"))
        Estado estadoRecovered = estadoService.findById(estado.id)

        assertEquals(estadoRecovered.id, estado.id)
        assertEquals(estadoRecovered.nome, estado.nome)
        assertEquals(estadoRecovered.uf, estado.uf)
    }

    //TODO: Testar a recuperação de um ID invalido
    @Test
    void estado_RecuperaIdInvalido(){
        assertThrows(EntidadeNotFoundException.class, { estadoService.findById(Integer.MAX_VALUE) })
    }

    //TODO: Testar a exclusão com sucesso
    @Test
    void estado_ExcluirComSucesso() {
        Estado estado = estadoService.create(new Estado(nome: "Rio Grande do Norte", uf: "RN"))
        Estado estadoRemoved = estadoService.remove(estado.id)

        assertNull(estadoRemoved)
    }

    //TODO: Testar a exclusão de um ID invalido
    @Test
    void estado_ExcluirInexistente() {
        assertThrows(EntidadeNotFoundException.class, { estadoService.remove(Integer.MAX_VALUE) })
    }

    //TODO: Testar a exclusão da entidade em uso
    @Test
    void estado_ExcluirEmUso(){
        Estado estado = estadoService.create(new Estado(nome: "Rio Grande do Norte", uf: "RN"))

        Cidade cidade = cidadeService.create(new Cidade(nome: "Monte Alegre", estado: estado))

        assertThrows(EntidadeEmUsoException.class, { estadoService.remove(estado.id) } )
    }

}
