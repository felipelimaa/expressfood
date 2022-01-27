package br.com.felipelima.api.expressfood.api.controller


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

import javax.validation.Valid

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
        return ResponseEntity.ok(cidadeService.findById(id)) as ResponseEntity<Cidade>
    }

    @PostMapping
    ResponseEntity<?> create(@RequestBody @Valid Cidade cidade){
        return ResponseEntity.status(HttpStatus.CREATED).body(cidadeService.create(cidade))
    }

    @PutMapping("/{id}")
    ResponseEntity<?> update(@RequestBody @Valid Cidade cidade, @PathVariable Long id){
        return ResponseEntity.ok(cidadeService.update(cidade, id))
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> remove(@PathVariable Long id){
        Cidade cidadeRemoved = cidadeService.remove(id)
        return ResponseEntity.noContent().build()
    }

}
