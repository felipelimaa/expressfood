package br.com.felipelima.api.expressfood.api.controller

import br.com.felipelima.api.expressfood.domain.exception.EntidadeNotFoundException
import br.com.felipelima.api.expressfood.domain.model.Restaurante
import br.com.felipelima.api.expressfood.domain.service.RestauranteService
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.util.ReflectionUtils
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

import java.lang.reflect.Field

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

    @GetMapping("/taxa-frete-gratis")
    ResponseEntity<Restaurante> findByTaxaFreteGratis(){
        List<Restaurante> restaurantes = restauranteService.findByTaxaFreteGratis()
        return ResponseEntity.ok(restaurantes) as ResponseEntity<Restaurante>
    }

    @GetMapping("/{id}")
    ResponseEntity<Restaurante> get(@PathVariable Long id){
        try {
            Restaurante restaurante = restauranteService.get(id)
            return ResponseEntity.ok(restaurante)
        } catch(EntidadeNotFoundException e){
            return ResponseEntity.status(e.status).body(e.message) as ResponseEntity<Restaurante>
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

    @PatchMapping("/{id}")
    ResponseEntity<?> partialUpdate(@RequestBody Map<String, Object> campos, @PathVariable Long id){
        Restaurante restauranteExists = restauranteService.get(id)

        merge(campos, restauranteExists)

        return update(restauranteExists, id)
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

    private merge(Map<String, Object> dadosOrigem, Restaurante restauranteDestino){
        ObjectMapper objectMapper = new ObjectMapper()
        Restaurante restauranteOrigem = objectMapper.convertValue(dadosOrigem, Restaurante)

        dadosOrigem.forEach((nomePropriedade, valorPropriedade) -> {
            Field field = ReflectionUtils.findField(Restaurante, nomePropriedade)
            field.setAccessible(true)

            Object novoValor = ReflectionUtils.getField(field, restauranteOrigem)

            ReflectionUtils.setField(field, restauranteDestino, novoValor)
        })

    }

}
