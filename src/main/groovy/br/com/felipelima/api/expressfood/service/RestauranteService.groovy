package br.com.felipelima.api.expressfood.service

import br.com.felipelima.api.expressfood.domain.model.Restaurante
import br.com.felipelima.api.expressfood.repository.RestauranteRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class RestauranteService {

    @Autowired
    RestauranteRepository restauranteRepository

    List<Restaurante> listar(){
        return restauranteRepository.findAll()
    }

    Restaurante adicionar(Restaurante restaurante){
        return restauranteRepository.save(restaurante)
    }

}
