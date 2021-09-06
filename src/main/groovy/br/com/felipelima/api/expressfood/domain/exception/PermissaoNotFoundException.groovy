package br.com.felipelima.api.expressfood.domain.exception

import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

class PermissaoNotFoundException extends ResponseStatusException {
    PermissaoNotFoundException() {
        super(HttpStatus.NOT_FOUND, "Permissão não encontrada!")
    }
}
