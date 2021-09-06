package br.com.felipelima.api.expressfood.domain.exception

import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

class EstadoNotFoundExcepetion extends ResponseStatusException {
    EstadoNotFoundExcepetion(){
        super(HttpStatus.NOT_FOUND, "Estado não encontrado!")
    }
}
