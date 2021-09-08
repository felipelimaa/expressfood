package br.com.felipelima.api.expressfood.domain.service

import br.com.felipelima.api.expressfood.domain.exception.EntidadeNotFoundException
import br.com.felipelima.api.expressfood.domain.model.Restaurante
import br.com.felipelima.api.expressfood.domain.repository.CozinhaRepository
import br.com.felipelima.api.expressfood.domain.repository.RestauranteRepository
import org.springframework.beans.BeanUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service

import javax.transaction.Transactional

@Service
class RestauranteService {

    @Autowired
    RestauranteRepository restauranteRepository

    @Autowired
    CozinhaRepository cozinhaRepository

    List<Restaurante> findAll(){
        return restauranteRepository.findAll()
    }

    Restaurante get(Long id){
        return restauranteRepository.findById(id).orElseThrow{
            new EntidadeNotFoundException(HttpStatus.NOT_FOUND, String.format("Restaurante de código %d não encontrado.", id))
        }
    }

    @Transactional
    Restaurante create(Restaurante restaurante){
        def cozinhaId = restaurante.cozinha.id
        def cozinhaExists = cozinhaRepository.findById(cozinhaId).orElseThrow{
            new EntidadeNotFoundException(
                    HttpStatus.BAD_REQUEST,
                    String.format("Cozinha de código %d não encontrada.", cozinhaId)
            )
        }

        restaurante.cozinha = cozinhaExists

        return restauranteRepository.save(restaurante)
    }

    @Transactional
    Restaurante update(Restaurante restaurante, Long id){
        def restauranteUpdated = get(id)
        def cozinhaId = restaurante.cozinha.id
        def cozinhaExists = cozinhaRepository.findById(cozinhaId).orElseThrow{
            new EntidadeNotFoundException(
                    HttpStatus.BAD_REQUEST,
                    String.format("Cozinha de código %d não encontrada.", cozinhaId)
            )
        }

        restaurante.cozinha = cozinhaExists

        BeanUtils.copyProperties(restaurante, restauranteUpdated, "id")

        return restauranteRepository.save(restauranteUpdated)
    }

    @Transactional
    Restaurante remove(Long id){
        def restauranteDeleted = get(id)

        return restauranteRepository.delete(restauranteDeleted)
    }
}
