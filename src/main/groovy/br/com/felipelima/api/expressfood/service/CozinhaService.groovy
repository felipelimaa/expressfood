package br.com.felipelima.api.expressfood.service

import br.com.felipelima.api.expressfood.domain.model.Cozinha
import br.com.felipelima.api.expressfood.repository.CozinhaRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CozinhaService {

    @Autowired
    CozinhaRepository cozinhaRepository

    List<Cozinha> listar(){
        return cozinhaRepository.findAll()
    }

    Cozinha adicionar(Cozinha cozinha){
        return cozinhaRepository.save(cozinha)
    }
}
