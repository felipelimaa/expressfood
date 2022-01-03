package br.com.felipelima.api.expressfood.api.controller

import br.com.felipelima.api.expressfood.domain.exception.EntidadeEmUsoException
import br.com.felipelima.api.expressfood.domain.model.Estado
import br.com.felipelima.api.expressfood.domain.service.EstadoService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/estados")
class EstadoController {

    @Autowired
    EstadoService estadoService

    @GetMapping
    ResponseEntity<Estado> findAll(){
        List<Estado> estados = estadoService.findAll()
        return ResponseEntity.ok(estados) as ResponseEntity<Estado>
    }

    @GetMapping("/{id}")
    ResponseEntity<Estado> findById(@PathVariable Long id){
        Estado estado = estadoService.findById(id)
        return ResponseEntity.ok(estado) as ResponseEntity<Estado>
    }

    @PostMapping
    ResponseEntity<Estado> create(@RequestBody Estado estado){
        Estado estadoAdded = estadoService.create(estado)
        return ResponseEntity.status(HttpStatus.CREATED).body(estadoAdded)
    }

    @PutMapping("/{id}")
    ResponseEntity<Estado> update(@RequestBody Estado estado, @PathVariable Long id){
        Estado estadoUpdated = estadoService.update(estado, id)
        return ResponseEntity.ok(estadoUpdated)
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> remove(@PathVariable Long id){
        /*try {
            Estado estadoRemoved = estadoService.remove(id)
            return ResponseEntity.noContent().build()
        } catch(EntidadeEmUsoException e){
            print(e.message)
            throw new EntidadeEmUsoException(e.message)
        }*/

        Estado estadoRemoved = estadoService.remove(id)
        return ResponseEntity.noContent().build()

    }
}
