package br.com.felipelima.api.expressfood.api.controller

import br.com.felipelima.api.expressfood.domain.exception.EntidadeNotFoundException
import br.com.felipelima.api.expressfood.domain.model.Restaurante
import br.com.felipelima.api.expressfood.domain.service.RestauranteService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/restaurantes")
class RestauranteController {

    @Autowired
    RestauranteService restauranteService

    @GetMapping
    ResponseEntity<Restaurante> findAll(){
        List<Restaurante> restaurantes = restauranteService.findAll()
        return ResponseEntity.ok(restaurantes) as ResponseEntity<Restaurante>
    }

    @GetMapping("/{id}")
    ResponseEntity<?> get(@PathVariable Long id){
        try {
            Restaurante restaurante = restauranteService.get(id)
            return ResponseEntity.ok(restaurante)
        } catch(EntidadeNotFoundException e){
            return ResponseEntity.status(e.status).body(e.message)
        }
    }

    @PostMapping
    ResponseEntity<?> create(@RequestBody Restaurante restaurante){
        try {
            Restaurante restauranteAdded = restauranteService.create(restaurante)
            return ResponseEntity.status(HttpStatus.CREATED).body(restauranteAdded)
        } catch(EntidadeNotFoundException e){
            return ResponseEntity.status(e.status).body(e.message)
        }
    }

    @PutMapping("/{id}")
    ResponseEntity<?> update(@RequestBody Restaurante restaurante, @PathVariable Long id){
        try {
            Restaurante restauranteUpdated = restauranteService.update(restaurante, id)
            return ResponseEntity.ok(restauranteUpdated)
        } catch(EntidadeNotFoundException e){
            return ResponseEntity.status(e.status).body(e.message)
        }
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> remove(@PathVariable Long id){
        try {
            Restaurante restauranteDeleted = restauranteService.remove(id)
            return ResponseEntity.noContent().build()
        } catch(EntidadeNotFoundException e){
            return ResponseEntity.status(e.status).body(e.message)
        }
    }

}
