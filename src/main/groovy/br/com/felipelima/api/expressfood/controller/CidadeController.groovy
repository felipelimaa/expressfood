package br.com.felipelima.api.expressfood.controller

import br.com.felipelima.api.expressfood.domain.model.Cidade
import br.com.felipelima.api.expressfood.service.CidadeService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
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
@RequestMapping(value = "/cidades", produces = [ MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE ])
class CidadeController {

    @Autowired
    CidadeService cidadeService

    @GetMapping
    ResponseEntity<Cidade> findAll(){
        List<Cidade> cidades = cidadeService.findAll()
        return ResponseEntity.ok(cidades) as ResponseEntity<Cidade>
    }

    @GetMapping("/{id}")
    ResponseEntity<Cidade> findById(@PathVariable("id") Long id){
        Cidade cidade = cidadeService.findById(id)
        return ResponseEntity.ok(cidade) as ResponseEntity<Cidade>
    }

    @PostMapping
    ResponseEntity<Cidade> create(@RequestBody Cidade cidade){
        Cidade cidadeAdded = cidadeService.create(cidade)
        return ResponseEntity.status(HttpStatus.CREATED).body(cidadeAdded)
    }

    @PutMapping("/{id}")
    ResponseEntity<Cidade> update(@RequestBody Cidade cidade, @PathVariable("id") Long id){
        Cidade cidadeUpdated = cidadeService.update(cidade, id)
        return ResponseEntity.ok(cidadeUpdated)
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Cidade> remove(@PathVariable("id") Long id){
        Cidade cidadeRemoved = cidadeService.remove(id)
        return ResponseEntity.ok(cidadeRemoved)
    }
}
