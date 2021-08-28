package br.com.felipelima.api.expressfood.controller

import br.com.felipelima.api.expressfood.domain.model.Restaurante
import br.com.felipelima.api.expressfood.service.RestauranteService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/restaurante")
class RestauranteController {

    @Autowired
    RestauranteService restauranteService

    @GetMapping
    ResponseEntity<Restaurante> listar(){
        List<Restaurante> restaurantes = restauranteService.listar()
        return ResponseEntity.ok(restaurantes) as ResponseEntity<Restaurante>
    }

    @PostMapping
    ResponseEntity<Restaurante> adicionar(@RequestBody Restaurante restaurante){
        Restaurante restauranteAdicionado = restauranteService.adicionar(restaurante)
        return ResponseEntity.status(HttpStatus.CREATED).body(restauranteAdicionado)
    }
}
