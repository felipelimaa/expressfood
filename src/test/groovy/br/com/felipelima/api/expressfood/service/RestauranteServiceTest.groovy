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

    Restaurante criaRestaurante() {
        return new Restaurante(nome: "Casa do Sabor", taxaFrete: 2.99, cozinha: recuperaCozinha())
    }

    Cozinha recuperaCozinha() {
        Cozinha cozinha = cozinhaService.get(1)
        if (cozinha.id == null) {
            Cozinha cozinhaCreated = cozinhaService.create(new Cozinha(nome: "Chinesa"))
            return cozinhaCreated
        }
        return cozinha
    }

    //Testar a criacao de um restaurante com sucesso
    @Test
    void restaurante_InserirComSucesso() {
        Restaurante restaurante = criaRestaurante()
        Restaurante restauranteCreated = restauranteService.create(restaurante)

        assertEquals(restauranteCreated.nome, restaurante.nome)
        assertEquals(restauranteCreated.taxaFrete, restaurante.taxaFrete)
        assertEquals(restauranteCreated.cozinha.id, restaurante.cozinha.id)
        assertTrue(restauranteCreated.id > 0)

    }

    //Testar restaurante sem nome
    @Test
    void restaurante_InserirSemNome() {
        Restaurante restaurante = new Restaurante(nome: "", taxaFrete: 2, cozinha: recuperaCozinha())

        assertThrows(ConstraintViolationException.class, { restauranteService.create(restaurante) })
    }

    //Testar a criação e recuperação de um ID
    @Test
    void restaurante_CriaERecuperaId() {
        Restaurante restaurante = restauranteService.create(criaRestaurante())

        Restaurante restauranteRecovered = restauranteService.get(restaurante.id)

        assertEquals(restauranteRecovered.id, restaurante.id)
        assertEquals(restauranteRecovered.nome, restaurante.nome)
        assertEquals(restauranteRecovered.taxaFrete, restaurante.taxaFrete)

    }

    //Testar a recuperação de um ID invalido
    @Test
    void restaurante_RecuperaIdInvalido() {
        assertThrows(EntidadeNotFoundException.class, { restauranteService.get(Integer.MAX_VALUE) })
    }

    //Testar a exclusão com sucesso
    @Test
    void restaurante_ExcluirComSucesso() {
        Restaurante restaurante = restauranteService.create(criaRestaurante())

        Restaurante restauranteRemoved = restauranteService.remove(restaurante.id)

        assertNull(restauranteRemoved)

    }

    //Testar a exclusão de um ID invalido
    @Test
    void restaurante_ExcluirInexistente() {
        Cozinha cozinha = cozinhaService.get(1)
        assertThrows(EntidadeNotFoundException.class, { restauranteService.remove(Integer.MAX_VALUE) })
    }

    //Testar restaurante com frete negativo
    @Test
    void restaurante_InserirFreteNegativo() {
        Restaurante restaurante = new Restaurante(nome: "Casa do Sabor", taxaFrete: -1, cozinha: recuperaCozinha())

        assertThrows(ConstraintViolationException.class, { restauranteService.create(restaurante) })

    }

    //Testar restaurante com frete gratis sem descricao "Frete Grátis"
    @Test
    void restaurante_InserirFreteGratisSemDescricao() {
        Restaurante restaurante = new Restaurante(nome: "Casa do Sabor", taxaFrete: 0, cozinha: recuperaCozinha())

        assertThrows(ConstraintViolationException.class, { restauranteService.create(restaurante) })
    }

    //Testar restaurante com frete grátis e descrição "Frete Grátis"
    @Test
    void restaurante_InserirFreteGratisComDescricaoCorreta(){
        Restaurante restaurante = new Restaurante(nome: "Casa do Sabor - Frete Grátis", taxaFrete: 0, cozinha: recuperaCozinha())

        Restaurante restauranteCreated = restauranteService.create(restaurante)

        assertEquals(restauranteCreated.nome, restaurante.nome)
        assertEquals(restauranteCreated.taxaFrete, restaurante.taxaFrete)
        assertEquals(restauranteCreated.cozinha.id, restaurante.cozinha.id)
        assertTrue(restauranteCreated.id > 0)
    }

}
