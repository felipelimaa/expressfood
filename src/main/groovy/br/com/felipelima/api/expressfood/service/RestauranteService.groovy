package br.com.felipelima.api.expressfood.service

import br.com.felipelima.api.expressfood.domain.model.Restaurante
import br.com.felipelima.api.expressfood.repository.RestauranteRepository
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

    Optional<Restaurante> findOne(Long id){
        return restauranteRepository.findById(id)
    }

    @Transactional
    Restaurante create(Restaurante restaurante){
        return restauranteRepository.save(restaurante)
    }

}
