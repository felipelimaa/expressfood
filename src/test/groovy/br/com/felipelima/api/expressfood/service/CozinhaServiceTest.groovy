package br.com.felipelima.api.expressfood.service

import br.com.felipelima.api.expressfood.domain.model.Restaurante
import br.com.felipelima.api.expressfood.domain.service.RestauranteService
import br.com.felipelima.api.expressfood.test.GeneralTest
import br.com.felipelima.api.expressfood.domain.exception.EntidadeEmUsoException
import br.com.felipelima.api.expressfood.domain.exception.EntidadeNotFoundException
import br.com.felipelima.api.expressfood.domain.model.Cozinha
import br.com.felipelima.api.expressfood.domain.service.CozinhaService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

import javax.validation.ConstraintViolationException

import static org.junit.jupiter.api.Assertions.*

class CozinhaServiceTest extends GeneralTest {

	@Autowired
	CozinhaService cozinhaService

	@Autowired
	RestauranteService restauranteService

	static Cozinha criaCozinha(){
		return new Cozinha(nome: "Chinesa")
	}

	//Testar a criação com sucesso
	@Test
	void cozinha_InserirComSucesso() {
		Cozinha cozinha = criaCozinha()

		Cozinha cozinhaCreated = cozinhaService.create(cozinha)

		assertEquals(cozinhaCreated.nome, cozinha.nome)
		assertTrue(cozinhaCreated.id > 0)
	}

	//Testar a criação sem nome
	@Test
	void cozinha_InserirSemNome() {
		Cozinha cozinha = new Cozinha(nome: null)
		assertThrows(ConstraintViolationException.class, { cozinhaService.create(cozinha)  })
	}

	//Testar a criação e recuperação de um ID
	@Test
	void cozinha_CriaERecuperaId() {
		Cozinha cozinha = cozinhaService.create(criaCozinha())
		Cozinha cozinhaRecovery = cozinhaService.get(cozinha.id)
		assertEquals(cozinhaRecovery.id, cozinha.id)
		assertEquals(cozinhaRecovery.nome, cozinha.nome)
	}

	//Testar a recuperação de um ID invalido
	@Test
	void cozinha_RecuperaIdInvalido() {
		assertThrows(EntidadeNotFoundException.class, { cozinhaService.get(Integer.MAX_VALUE) })
	}

	//Testar a exclusão com sucesso
	@Test
	void cozinha_ExcluirComSucesso() {
		Cozinha cozinha = cozinhaService.create(criaCozinha())
		Cozinha cozinhaRemoved = cozinhaService.remove(cozinha.id)

		assertNull(cozinhaRemoved)
	}

	//Testar a exclusão da entidade em uso
	@Test
	void cozinha_ExcluirEmUso() {
		Cozinha cozinha = cozinhaService.create(criaCozinha())
		Restaurante restaurante = restauranteService.create(new Restaurante(nome: "Casa do Sabor", taxaFrete: 2.99, cozinha: cozinha))

		assertThrows(EntidadeEmUsoException.class, { cozinhaService.remove(cozinha.id)})
	}

	//Testar a exclusão de um ID invalido
	@Test
	void cozinha_ExcluirInexistente() {
		assertThrows(EntidadeNotFoundException.class, { cozinhaService.remove(Integer.MAX_VALUE) })
	}

}
