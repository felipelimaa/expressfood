package br.com.felipelima.api.expressfood.domain.service

import br.com.felipelima.api.expressfood.domain.exception.EntidadeNotFoundException
import br.com.felipelima.api.expressfood.domain.model.FormaPagamento
import br.com.felipelima.api.expressfood.domain.repository.FormaPagamentoRepository
import org.springframework.beans.BeanUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
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
        return formaPagamentoRepository.findById(id).orElseThrow{
            new EntidadeNotFoundException(
                    HttpStatus.NOT_FOUND,
                    String.format("Forma de Pagamento de código %d não encontrada.", id)
            )
        }
    }

    @Transactional
    FormaPagamento create(FormaPagamento formaPagamento){
        return formaPagamentoRepository.save(formaPagamento)
    }

    @Transactional
    FormaPagamento update(FormaPagamento formaPagamento, Long id){
        def formaPagamentoUpdated = findById(id)

        BeanUtils.copyProperties(formaPagamento, formaPagamentoUpdated, "id")

        return formaPagamentoRepository.save(formaPagamentoUpdated)
    }

    @Transactional
    FormaPagamento delete(Long id){
        def formaPagamentoDeleted = findById(id)

        return formaPagamentoRepository.delete(formaPagamentoDeleted)
    }

}
