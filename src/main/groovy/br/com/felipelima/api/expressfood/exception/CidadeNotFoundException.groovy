package br.com.felipelima.api.expressfood.exception

import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

class CidadeNotFoundException extends ResponseStatusException {
    CidadeNotFoundException() {
        super(HttpStatus.NOT_FOUND, "Cidade não encontrada!")
    }
}
