package br.com.felipelima.api.expressfood.service

import br.com.felipelima.api.expressfood.domain.model.Estado
import br.com.felipelima.api.expressfood.exception.EstadoNotFoundExcepetion
import br.com.felipelima.api.expressfood.repository.EstadoRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import javax.transaction.Transactional

@Service
class EstadoService {
    @Autowired
    EstadoRepository estadoRepository

    List<Estado> findAll(){
        return estadoRepository.findAll()
    }

    Estado findById(Long id){
        return estadoRepository.findById(id).orElseThrow{new EstadoNotFoundExcepetion()}
    }

    @Transactional
    Estado create(Estado estado){
        return estadoRepository.save(estado)
    }

    @Transactional
    Estado update(Estado estado, Long id){
        def estadoUpdated = findById(id)

        estadoUpdated.nome = estado.nome
        estadoUpdated.uf = estado.uf

        return estadoRepository.save(estadoUpdated)
    }

    @Transactional
    Estado remove(Long id){
        def estadoRemoved = findById(id)

        return estadoRepository.delete(estadoRemoved)
    }
}
