package br.com.felipelima.api.expressfood.api.controller

import br.com.felipelima.api.expressfood.domain.exception.EntidadeNotFoundException
import br.com.felipelima.api.expressfood.domain.model.Cidade
import br.com.felipelima.api.expressfood.domain.service.CidadeService
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
@RequestMapping("/cidades")
class CidadeController {

    @Autowired
    CidadeService cidadeService

    @GetMapping
    ResponseEntity<Cidade> findAll(){
        List<Cidade> cidades = cidadeService.findAll()
        return ResponseEntity.ok(cidades) as ResponseEntity<Cidade>
    }

    @GetMapping("/{id}")
    ResponseEntity<?> findById(@PathVariable Long id){
        try {
            Cidade cidade = cidadeService.findById(id)
            return ResponseEntity.ok(cidade) as ResponseEntity<Cidade>
        } catch(EntidadeNotFoundException e){
            return ResponseEntity.status(e.status).body(e.message)
        }
    }

    @PostMapping
    ResponseEntity<?> create(@RequestBody Cidade cidade){
        try {
            Cidade cidadeAdded = cidadeService.create(cidade)
            return ResponseEntity.status(HttpStatus.CREATED).body(cidadeAdded)
        } catch(EntidadeNotFoundException e){
            return ResponseEntity.status(e.status).body(e.message)
        }
    }

    @PutMapping("/{id}")
    ResponseEntity<?> update(@RequestBody Cidade cidade, @PathVariable Long id){
        try {
            Cidade cidadeUpdated = cidadeService.update(cidade, id)
            return ResponseEntity.ok(cidadeUpdated)
        } catch(EntidadeNotFoundException e){
            return ResponseEntity.status(e.status).body(e.message)
        }
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> remove(@PathVariable Long id){
        try {
            Cidade cidadeRemoved = cidadeService.remove(id)
            return ResponseEntity.noContent().build()
        } catch(EntidadeNotFoundException e){
            return ResponseEntity.status(e.status).body(e.message)
        }
    }
}
