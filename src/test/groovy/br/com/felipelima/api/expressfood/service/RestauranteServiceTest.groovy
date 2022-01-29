package br.com.felipelima.api.expressfood.service

import br.com.felipelima.api.expressfood.domain.exception.EntidadeNotFoundException
import br.com.felipelima.api.expressfood.domain.model.Cozinha
import br.com.felipelima.api.expressfood.domain.model.Restaurante
import br.com.felipelima.api.expressfood.domain.service.CozinhaService
import br.com.felipelima.api.expressfood.domain.service.RestauranteService
import br.com.felipelima.api.expressfood.test.GeneralTest
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

import javax.validation.ConstraintViolationException

import static org.junit.jupiter.api.Assertions.*

class RestauranteServiceTest extends GeneralTest {

    @Autowired
    CozinhaService cozinhaService

    @Autowired
    RestauranteService restauranteService

    //TODO: Testar a criacao de um restaurante com sucesso
    @Test
    void restaurante_InserirComSucesso() {
        Cozinha cozinha = cozinhaService.get(1)

        Restaurante restaurante = new Restaurante(nome: "Casa do Sabor", taxaFrete: 2.99, cozinha: cozinha)
        Restaurante restauranteCreated = restauranteService.create(restaurante)

        assertEquals(restauranteCreated.nome, restaurante.nome)
        assertEquals(restauranteCreated.taxaFrete, restaurante.taxaFrete)
        assertEquals(restauranteCreated.cozinha.id, restaurante.cozinha.id)
        assertTrue(restauranteCreated.id > 0)

    }

    //TODO: Testar restaurante com frete negativo
    @Test
    void restaurante_InserirFreteNegativo() {
        Cozinha cozinha = cozinhaService.get(1)

        Restaurante restaurante = new Restaurante(nome: "Casa do Sabor", taxaFrete: -1, cozinha: cozinha)

        assertThrows(ConstraintViolationException.class, { restauranteService.create(restaurante) })

    }

    //TODO: Testar restaurante com frete gratis sem descricao "Frete Grátis"
    @Test
    void restaurante_InserirFreteGratisSemDescricao() {
        Cozinha cozinha = cozinhaService.get(1)

        Restaurante restaurante = new Restaurante(nome: "Casa do Sabor", taxaFrete: 0, cozinha: cozinha)

        assertThrows(ConstraintViolationException.class, { restauranteService.create(restaurante) })
    }

    //TODO: Testar restaurante sem nome
    @Test
    void restaurante_InserirSemNome() {
        Cozinha cozinha = cozinhaService.get(1)

        Restaurante restaurante = new Restaurante(nome: "", taxaFrete: 2, cozinha: cozinha)

        assertThrows(ConstraintViolationException.class, { restauranteService.create(restaurante) })
    }

    //TODO: Testar a criação e recuperação de um ID
    @Test
    void restaurante_CriaERecuperaId() {
        Cozinha cozinha = cozinhaService.get(1)
        Restaurante restaurante = restauranteService.create(new Restaurante(nome: "Casa do Sabor", taxaFrete: 2.00, cozinha: cozinha))

        Restaurante restauranteRecovered = restauranteService.get(restaurante.id)

        assertEquals(restauranteRecovered.id, restaurante.id)
        assertEquals(restauranteRecovered.nome, restaurante.nome)
        assertEquals(restauranteRecovered.taxaFrete, restaurante.taxaFrete)

    }

    //TODO: Testar a recuperação de um ID invalido
    @Test
    void restaurante_RecuperaIdInvalido() {
        assertThrows(EntidadeNotFoundException.class, { restauranteService.get(Integer.MAX_VALUE) })
    }

    //TODO: Testar a exclusão com sucesso
    @Test
    void restaurante_ExcluirComSucesso() {
        Cozinha cozinha = cozinhaService.get(1)
        Restaurante restaurante = restauranteService.create(new Restaurante(nome: "Casa do Sabor", taxaFrete: 2.00, cozinha: cozinha))

        Restaurante restauranteRemoved = restauranteService.remove(restaurante.id)

        assertNull(restauranteRemoved)

    }

    //TODO: Testar a exclusão de um ID invalido
    @Test
    void restaurante_ExcluirInexistente() {
        Cozinha cozinha = cozinhaService.get(1)
        assertThrows(EntidadeNotFoundException.class, { restauranteService.remove(Integer.MAX_VALUE) })
    }

}
