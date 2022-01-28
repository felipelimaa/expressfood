package br.com.felipelima.api.expressfood.service

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

	@Test
	void cozinha_TestarCriacaoComSucesso() {
		// cenário
		Cozinha cozinha = new Cozinha(nome: "Chinesa")

		// ação
		Cozinha cozinhaCreated = cozinhaService.create(cozinha)

		// validação
		assertEquals(cozinhaCreated.nome, cozinha.nome)
		assertTrue(cozinhaCreated.id > 0)
	}

	@Test
	void cozinha_TestarCriacaoSemNome() {
		Cozinha cozinha = new Cozinha(nome: null)
		assertThrows(ConstraintViolationException.class, { cozinhaService.create(cozinha)  })
	}

	@Test
	void cozinha_CriaERecuperaId() {
		Cozinha cozinha = cozinhaService.create(new Cozinha(nome: "Chinesa"))
		Cozinha cozinhaRecovery = cozinhaService.get(cozinha.id)
		assertEquals(cozinhaRecovery.id, cozinha.id)
		assertEquals(cozinhaRecovery.nome, cozinha.nome)
	}

	@Test
	void cozinha_RecuperaIdInvalido() {
		assertThrows(EntidadeNotFoundException.class, { cozinhaService.get(Integer.MAX_VALUE) })
	}

	@Test
	void cozinha_ExcluirComSucesso() {
		Cozinha cozinha = cozinhaService.create(new Cozinha(nome: "Chinesa"))
		Cozinha cozinhaRemoved = cozinhaService.remove(cozinha.id)
	}

	@Test
	void cozinha_ExcluirEmUso() {
		assertThrows(EntidadeEmUsoException.class, { cozinhaService.remove(3)})
	}

	@Test
	void cozinha_ExcluirInexistente() {
		assertThrows(EntidadeNotFoundException.class, { cozinhaService.remove(Integer.MAX_VALUE) })
	}

}
