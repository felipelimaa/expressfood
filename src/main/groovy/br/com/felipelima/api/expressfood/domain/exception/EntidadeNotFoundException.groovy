package br.com.felipelima.api.expressfood.domain.exception

import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

class EntidadeNotFoundException extends ResponseStatusException{
    EntidadeNotFoundException(Enum<HttpStatus> status, String mensagem) {
        super(status as HttpStatus, mensagem)
    }
}
