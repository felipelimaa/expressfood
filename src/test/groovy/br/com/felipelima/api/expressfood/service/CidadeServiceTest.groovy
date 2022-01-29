package br.com.felipelima.api.expressfood.service

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

class CidadeServiceTest extends GeneralTest {

    @Autowired
    CidadeService cidadeService

    @Autowired
    EstadoService estadoService

    Estado buscaEstado(Long id) {
        return estadoService.findById(id)
    }

    //TODO: Testar a criação com sucesso
    @Test
    void cidade_InserirComSucesso() {
        Cidade cidade = new Cidade(nome: "Monte Alegre", estado: buscaEstado(1))
        Cidade cidadeCreated = cidadeService.create(cidade)

        assertEquals(cidadeCreated.nome, cidade.nome)
        assertTrue(cidadeCreated.id > 0)
    }

    //TODO: Testar a criação sem nome
    @Test
    void cidade_InserirSemNome() {
        Cidade cidade = new Cidade(nome: "", estado: buscaEstado(1))

        assertThrows(ConstraintViolationException.class, { cidadeService.create(cidade)})
    }

    //TODO: Testar a criação e recuperação de um ID
    @Test
    void cidade_CriaERecuperaId(){
        Cidade cidade = cidadeService.create(new Cidade(nome: "Monte Alegre", estado: buscaEstado(1)))
        Cidade cidadeRecovered = cidadeService.findById(cidade.id)

        assertEquals(cidadeRecovered.id, cidade.id)
        assertEquals(cidadeRecovered.nome, cidade.nome)
    }

    //TODO: Testar a recuperação de um ID invalido
    @Test
    void cidade_RecuperaIdInvalido(){
        assertThrows(EntidadeNotFoundException.class, { cidadeService.findById(Integer.MAX_VALUE) })
    }

    //TODO: Testar a exclusão com sucesso
    @Test
    void cidade_ExcluirComSucesso() {
        Cidade cidade = cidadeService.create(new Cidade(nome: "Monte Alegre", estado: buscaEstado(1)))

        Cidade cidadeRemoved = cidadeService.remove(cidade.id)

        assertNull(cidadeRemoved)
    }

    //TODO: Testar a exclusão de um ID invalido
    @Test
    void cidade_ExcluirInexistente() {
        assertThrows(EntidadeNotFoundException.class, { cidadeService.remove(Integer.MAX_VALUE) })
    }

}
