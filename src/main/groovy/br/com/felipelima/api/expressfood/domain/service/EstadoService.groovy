package br.com.felipelima.api.expressfood.domain.service

import br.com.felipelima.api.expressfood.domain.exception.EntidadeEmUsoException
import br.com.felipelima.api.expressfood.domain.exception.EntidadeNotFoundException
import br.com.felipelima.api.expressfood.domain.model.Estado
import br.com.felipelima.api.expressfood.domain.repository.EstadoRepository
import org.springframework.beans.BeanUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service

import javax.transaction.Transactional

@Service
class EstadoService {

    String MSG_ESTADO_NOT_FOUND = "Estado de código %d não encontrado"

    String MSG_ESTADO_EM_USO = "Estado de código %d não pode ser removido, pois está em uso."

    @Autowired
    EstadoRepository estadoRepository

    List<Estado> findAll(){
        return estadoRepository.findAll()
    }

    Estado findById(Long id){
        return estadoRepository.findById(id).orElseThrow{
            new EntidadeNotFoundException(
                    HttpStatus.NOT_FOUND,
                    String.format(MSG_ESTADO_NOT_FOUND, id)
            )
        }
    }

    @Transactional
    Estado create(Estado estado){
        return estadoRepository.save(estado)
    }

    @Transactional
    Estado update(Estado estado, Long id){
        def estadoUpdated = findById(id)

        BeanUtils.copyProperties(estado, estadoUpdated, "id")

        return estadoRepository.save(estadoUpdated)
    }

    @Transactional
    Estado remove(Long id){
        def estadoRemoved = findById(id)
        try {
            estadoRepository.delete(estadoRemoved)
            estadoRepository.flush()
        } catch(DataIntegrityViolationException e){
            throw new EntidadeEmUsoException(String.format(MSG_ESTADO_EM_USO, id))
        }
    }
}
