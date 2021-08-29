package br.com.felipelima.api.expressfood.controller

import br.com.felipelima.api.expressfood.domain.model.Restaurante
import br.com.felipelima.api.expressfood.service.RestauranteService
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
@RequestMapping("/restaurante")
class RestauranteController {

    @Autowired
    RestauranteService restauranteService

    @GetMapping
    ResponseEntity<Restaurante> findAll(){
        List<Restaurante> restaurantes = restauranteService.findAll()
        return ResponseEntity.ok(restaurantes) as ResponseEntity<Restaurante>
    }

    @GetMapping("/{id}")
    ResponseEntity<Restaurante> get(@PathVariable("id") Long id){
        Restaurante restaurante = restauranteService.get(id)
        return ResponseEntity.ok(restaurante)
    }

    @PostMapping
    ResponseEntity<Restaurante> create(@RequestBody Restaurante restaurante){
        Restaurante restauranteAdded = restauranteService.create(restaurante)
        return ResponseEntity.status(HttpStatus.CREATED).body(restauranteAdded)
    }

    @PutMapping("/{id}")
    ResponseEntity<Restaurante> update(@RequestBody Restaurante restaurante, @PathVariable("id") Long id){
        Restaurante restauranteUpdated = restauranteService.update(restaurante, id)
        return ResponseEntity.ok(restauranteUpdated)
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Restaurante> remove(@PathVariable("id") Long id){
        Restaurante restauranteDeleted = restauranteService.remove(id)
        return ResponseEntity.ok(restauranteDeleted)
    }

}
