package br.com.felipelima.api.expressfood.controller

import br.com.felipelima.api.expressfood.domain.model.Cozinha
import br.com.felipelima.api.expressfood.service.CozinhaService
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
@RequestMapping("/cozinha")
class CozinhaController {
    @Autowired
    CozinhaService cozinhaService

    @GetMapping
    ResponseEntity<Cozinha> findAll(){
        List<Cozinha> cozinhas = cozinhaService.findAll()
        return ResponseEntity.ok(cozinhas) as ResponseEntity<Cozinha>
    }

    @GetMapping("/{id}")
    ResponseEntity<Cozinha> get(@PathVariable("id") Long id){
        Cozinha cozinha = cozinhaService.get(id)
        return ResponseEntity.ok(cozinha) as ResponseEntity<Cozinha>
    }

    @PostMapping
    ResponseEntity<Cozinha> create(@RequestBody Cozinha cozinha){
        Cozinha cozinhaAdded = cozinhaService.create(cozinha)
        return ResponseEntity.status(HttpStatus.CREATED).body(cozinhaAdded)
    }

    @PutMapping("/{id}")
    ResponseEntity<Cozinha> update(@RequestBody Cozinha cozinha, @PathVariable("id") Long id){
        Cozinha cozinhaUpdated = cozinhaService.update(cozinha, id)
        return ResponseEntity.ok(cozinhaUpdated)
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Cozinha> remove(@PathVariable("id") Long id){
        Cozinha cozinhaRemoved = cozinhaService.remove(id)
        return ResponseEntity.ok(cozinhaRemoved)
    }

}
