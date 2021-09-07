package br.com.felipelima.api.expressfood.domain.exception

import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

class EntidadeEmUsoException extends ResponseStatusException {

    EntidadeEmUsoException(String mensagem) {
        super(HttpStatus.CONFLICT, mensagem)
    }
}
