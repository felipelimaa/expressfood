package br.com.felipelima.api.expressfood.domain.service

import br.com.felipelima.api.expressfood.domain.model.Permissao
import br.com.felipelima.api.expressfood.domain.exception.PermissaoNotFoundException
import br.com.felipelima.api.expressfood.domain.repository.PermissaoRepository
import org.springframework.beans.BeanUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import javax.transaction.Transactional

@Service
class PermissaoService {

    @Autowired
    PermissaoRepository permissaoRepository

    List<Permissao> findAll(){
        return permissaoRepository.findAll()
    }

    Permissao findById(Long id){
        return permissaoRepository.findById(id).orElseThrow{ new PermissaoNotFoundException() }
    }

    @Transactional
    Permissao create(Permissao permissao){
        return permissaoRepository.save(permissao)
    }

    @Transactional
    Permissao update(Permissao permissao, Long id){
        def permissaoUpdated = findById(id)

        BeanUtils.copyProperties(permissao, permissaoUpdated, "id")

        return permissaoRepository.save(permissaoUpdated)
    }

    @Transactional
    Permissao remove(Long id){
        def permissaoRemoved = findById(id)

        return permissaoRepository.delete(permissaoRemoved)
    }
}
