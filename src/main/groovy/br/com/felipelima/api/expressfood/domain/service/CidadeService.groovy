package br.com.felipelima.api.expressfood.domain.service

import br.com.felipelima.api.expressfood.domain.exception.EntidadeEmUsoException
import br.com.felipelima.api.expressfood.domain.exception.EntidadeNotFoundException
import br.com.felipelima.api.expressfood.domain.model.Cidade
import br.com.felipelima.api.expressfood.domain.repository.CidadeRepository
import org.springframework.beans.BeanUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service

import javax.transaction.Transactional

@Service
class CidadeService {

    String MSG_CIDADE_NOT_FOUND = "Cidade de código %d não encontrada."

    String MSG_CIDADE_EM_USO = "Cidade de código %d não pode ser removido, pois está em uso."

    @Autowired
    CidadeRepository cidadeRepository

    @Autowired
    EstadoService estadoService

    List<Cidade> findAll() {
        return cidadeRepository.findAll()
    }

    Cidade findById(Long id){
        return cidadeRepository.findById(id).orElseThrow{
            new EntidadeNotFoundException(
                    HttpStatus.NOT_FOUND,
                    String.format(MSG_CIDADE_NOT_FOUND, id)
            )
        }
    }

    @Transactional
    Cidade create(Cidade cidade){
        def estadoId = cidade.estado.id
        def estadoExists = estadoService.findById(estadoId)

        cidade.estado = estadoExists

        return cidadeRepository.save(cidade)
    }

    @Transactional
    Cidade update(Cidade cidade, Long id){
        def cidadeUpdated = findById(id)
        def estadoId = cidade.estado.id
        def estadoExists = estadoService.findById(estadoId)

        BeanUtils.copyProperties(cidade, cidadeUpdated, "id")
        BeanUtils.copyProperties(estadoExists, cidadeUpdated.estado)

        return cidadeRepository.save(cidadeUpdated)
    }

    @Transactional
    Cidade remove(Long id){
        def cidadeRemoved = findById(id)

        try {
            cidadeRepository.delete(cidadeRemoved)
            cidadeRepository.flush()
        } catch(DataIntegrityViolationException e){
            throw new EntidadeEmUsoException(String.format(MSG_CIDADE_EM_USO, id))
        }
    }

}
