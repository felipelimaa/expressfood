package br.com.felipelima.api.expressfood.domain.service


import br.com.felipelima.api.expressfood.domain.exception.EntidadeEmUsoException
import br.com.felipelima.api.expressfood.domain.exception.EntidadeNotFoundException
import br.com.felipelima.api.expressfood.domain.model.Restaurante
import br.com.felipelima.api.expressfood.domain.repository.RestauranteRepository
import org.springframework.beans.BeanUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service

import javax.transaction.Transactional

@Service
class RestauranteService {

    String MSG_RESTAURANTE_NOT_FOUND = "Restaurante de código %d não encontrado."

    String MSG_COZINHA_NOT_FOUND = "Cozinha de código %d não encontrado."

    String MSG_RESTAURANTE_EM_USO = "Restaurante de código %d não pode ser removido, pois está em uso."

    @Autowired
    RestauranteRepository restauranteRepository

    @Autowired
    CozinhaService cozinhaService

    List<Restaurante> findAll(){
        return restauranteRepository.findAll()
    }

    Restaurante get(Long id){
        return restauranteRepository.findById(id).orElseThrow{
            new EntidadeNotFoundException(HttpStatus.NOT_FOUND, String.format(MSG_RESTAURANTE_NOT_FOUND, id))
        }
    }

    List<Restaurante> findByTaxaFreteGratis(){
        return restauranteRepository.findByTaxaFreteEquals(0.0)
    }

    @Transactional
    Restaurante create(Restaurante restaurante){
        def cozinhaId = restaurante.cozinha.id
        def cozinhaExists = cozinhaService.get(cozinhaId)

        restaurante.cozinha = cozinhaExists

        return restauranteRepository.save(restaurante)
    }

    @Transactional
    Restaurante update(Restaurante restaurante, Long id){
        def restauranteUpdated = get(id)
        def cozinhaId = restaurante.cozinha.id
        def cozinhaExists = cozinhaService.get(cozinhaId)

        restaurante.cozinha = cozinhaExists

        BeanUtils.copyProperties(restaurante, restauranteUpdated, "id", "formasPagamento", "endereco", "dataCadastro", "produtos")

        return restauranteRepository.save(restauranteUpdated)
    }

    @Transactional
    Restaurante remove(Long id){
        def restauranteDeleted = get(id)

        try {
            restauranteRepository.delete(restauranteDeleted)
            restauranteRepository.flush()
        } catch(DataIntegrityViolationException e){
            throw new EntidadeEmUsoException(String.format(MSG_RESTAURANTE_EM_USO, id))
        }
    }
}
