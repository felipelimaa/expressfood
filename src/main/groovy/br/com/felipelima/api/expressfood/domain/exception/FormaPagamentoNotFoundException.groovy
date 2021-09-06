package br.com.felipelima.api.expressfood.domain.exception

import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

class FormaPagamentoNotFoundException extends ResponseStatusException {
    FormaPagamentoNotFoundException(){
        super(HttpStatus.NOT_FOUND, "Forma de Pagamento não encontrada")
    }
}
