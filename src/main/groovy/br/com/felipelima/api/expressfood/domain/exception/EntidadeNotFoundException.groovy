package br.com.felipelima.api.expressfood.domain.exception

import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

class EntidadeNotFoundException extends ResponseStatusException{
    EntidadeNotFoundException(String mensagem) {
        super(HttpStatus.NOT_FOUND, mensagem)
    }
}
