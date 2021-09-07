package br.com.felipelima.api.expressfood.domain.service

import br.com.felipelima.api.expressfood.domain.exception.EntidadeNotFoundException
import br.com.felipelima.api.expressfood.domain.model.Restaurante
import br.com.felipelima.api.expressfood.domain.repository.RestauranteRepository
import org.springframework.beans.BeanUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import javax.transaction.Transactional

@Service
class RestauranteService {

    @Autowired
    RestauranteRepository restauranteRepository

    List<Restaurante> findAll(){
        return restauranteRepository.findAll()
    }

    Restaurante get(Long id){
        return restauranteRepository.findById(id).orElseThrow{
            new EntidadeNotFoundException(String.format("Restaurante de código %d não encontrado.", id))
        }
    }

    @Transactional
    Restaurante create(Restaurante restaurante){
        return restauranteRepository.save(restaurante)
    }

    @Transactional
    Restaurante update(Restaurante restaurante, Long id){
        def restauranteUpdated = get(id)

        BeanUtils.copyProperties(restaurante, restauranteUpdated, "id")

        return restauranteRepository.save(restauranteUpdated)
    }

    @Transactional
    Restaurante remove(Long id){
        def restauranteDeleted = get(id)

        return restauranteRepository.delete(restauranteDeleted)
    }
}
