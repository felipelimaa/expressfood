package br.com.felipelima.api.expressfood.domain.service

import br.com.felipelima.api.expressfood.domain.model.Cozinha
import br.com.felipelima.api.expressfood.exception.CozinhaNotFoundException
import br.com.felipelima.api.expressfood.domain.repository.CozinhaRepository
import org.springframework.beans.BeanUtils
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

    Cozinha get(Long id){
        return cozinhaRepository.findById(id).orElseThrow{new CozinhaNotFoundException()}
    }

    @Transactional
    Cozinha create(Cozinha cozinha){
        return cozinhaRepository.save(cozinha)
    }

    @Transactional
    Cozinha update(Cozinha cozinha, Long id){
        def cozinhaUpdated = get(id)

        BeanUtils.copyProperties(cozinha, cozinhaUpdated, "id")

        return cozinhaRepository.save(cozinhaUpdated)
    }

    @Transactional
    Cozinha remove(Long id){
        def cozinhaRemoved = get(id)

        return cozinhaRepository.delete(cozinhaRemoved)
    }

}
