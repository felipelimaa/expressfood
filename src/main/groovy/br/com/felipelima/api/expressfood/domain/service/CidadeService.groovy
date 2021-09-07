package br.com.felipelima.api.expressfood.domain.service

import br.com.felipelima.api.expressfood.domain.exception.EntidadeNotFoundException
import br.com.felipelima.api.expressfood.domain.model.Cidade
import br.com.felipelima.api.expressfood.domain.repository.CidadeRepository
import org.springframework.beans.BeanUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import javax.transaction.Transactional

@Service
class CidadeService {

    @Autowired
    CidadeRepository cidadeRepository

    @Autowired
    EstadoService estadoService

    List<Cidade> findAll() {
        return cidadeRepository.findAll()
    }

    Cidade findById(Long id){
        return cidadeRepository.findById(id).orElseThrow{
            new EntidadeNotFoundException(String.format("Cidade de código %d não encontrada.", id))
        }
    }

    @Transactional
    Cidade create(Cidade cidade){
        def cidadeAdded = cidadeRepository.save(cidade)
        def estado = estadoService.findById(cidadeAdded.estado.id)

        cidadeAdded.estado = estado
        return cidadeAdded
    }

    @Transactional
    Cidade update(Cidade cidade, Long id){
        def cidadeUpdated = findById(id)
        def estado = estadoService.findById(cidade.estado.id)

        BeanUtils.copyProperties(cidade, cidadeUpdated, "id")
        BeanUtils.copyProperties(estado, cidadeUpdated.estado)

        cidadeRepository.save(cidadeUpdated)

        return cidadeUpdated
    }

    @Transactional
    Cidade remove(Long id){
        def cidadeRemoved = findById(id)

        return cidadeRepository.delete(cidadeRemoved)
    }

}
