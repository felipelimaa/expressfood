package br.com.felipelima.api.expressfood.service

import br.com.felipelima.api.expressfood.domain.model.FormaPagamento
import br.com.felipelima.api.expressfood.exception.FormaPagamentoNotFoundException
import br.com.felipelima.api.expressfood.repository.FormaPagamentoRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import javax.transaction.Transactional

@Service
class FormaPagamentoService {

    @Autowired
    FormaPagamentoRepository formaPagamentoRepository

    List<FormaPagamento> findAll(){
        return formaPagamentoRepository.findAll()
    }

    FormaPagamento findById(Long id){
        return formaPagamentoRepository.findById(id).orElseThrow{new FormaPagamentoNotFoundException()}
    }

    @Transactional
    FormaPagamento create(FormaPagamento formaPagamento){
        return formaPagamentoRepository.save(formaPagamento)
    }

    @Transactional
    FormaPagamento update(FormaPagamento formaPagamento, Long id){
        def formaPagamentoUpdated = findById(id)

        formaPagamentoUpdated.descricao = formaPagamento.descricao

        return formaPagamentoRepository.save(formaPagamentoUpdated)
    }

    @Transactional
    FormaPagamento delete(Long id){
        def formaPagamentoDeleted = findById(id)

        return formaPagamentoRepository.delete(formaPagamentoDeleted)
    }

}
