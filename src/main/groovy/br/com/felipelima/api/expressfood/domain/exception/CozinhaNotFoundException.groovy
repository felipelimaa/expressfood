package br.com.felipelima.api.expressfood.domain.exception

import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

class CozinhaNotFoundException extends ResponseStatusException{
    CozinhaNotFoundException() {
        super(HttpStatus.NOT_FOUND, "Cozinha não encontrada!")
    }
}
