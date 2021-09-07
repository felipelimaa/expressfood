package br.com.felipelima.api.expressfood.domain.service

import br.com.felipelima.api.expressfood.domain.exception.EntidadeEmUsoException
import br.com.felipelima.api.expressfood.domain.exception.EntidadeNotFoundException
import br.com.felipelima.api.expressfood.domain.model.Cozinha
import br.com.felipelima.api.expressfood.domain.repository.CozinhaRepository
import org.springframework.beans.BeanUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DataIntegrityViolationException
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
        return cozinhaRepository.findById(id).orElseThrow{
            new EntidadeNotFoundException(String.format("Cozinha de código %d não encontrada.", id))
        }
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

        try {
            cozinhaRepository.delete(cozinhaRemoved)
            cozinhaRepository.flush()
        } catch (DataIntegrityViolationException e){
            throw new EntidadeEmUsoException(String.format("Cozinha de código %d não pode ser removida, pois está em uso.", id))
        }
    }

}
