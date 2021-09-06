package br.com.felipelima.api.expressfood.domain.exception

import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

class RestauranteNotFoundException extends ResponseStatusException{
    RestauranteNotFoundException(){
        super(HttpStatus.NOT_FOUND, "Restaurante não encontrado!")
    }
}
