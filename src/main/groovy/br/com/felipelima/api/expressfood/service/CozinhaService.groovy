package br.com.felipelima.api.expressfood.service

import br.com.felipelima.api.expressfood.domain.model.Cozinha
import br.com.felipelima.api.expressfood.repository.CozinhaRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import javax.transaction.Transactional

@Service
class CozinhaService {

    @Autowired
    CozinhaRepository cozinhaRepository

    List<Cozinha> findAll(){
        return cozinhaRepository.findAll()
    }

    Optional<Cozinha> findOne(Long id){
        return cozinhaRepository.findById(id)
    }

    @Transactional
    Cozinha create(Cozinha cozinha){
        return cozinhaRepository.save(cozinha)
    }
}
