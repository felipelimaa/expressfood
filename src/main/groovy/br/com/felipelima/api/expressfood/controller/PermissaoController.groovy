package br.com.felipelima.api.expressfood.controller

import br.com.felipelima.api.expressfood.domain.model.Permissao
import br.com.felipelima.api.expressfood.service.PermissaoService
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
@RequestMapping("/permissao")
class PermissaoController {

    @Autowired
    PermissaoService permissaoService

    @GetMapping
    ResponseEntity<Permissao> findAll() {
        List<Permissao> permissoes = permissaoService.findAll()
        return ResponseEntity.ok(permissoes) as ResponseEntity<Permissao>
    }

    @GetMapping("/{id}")
    ResponseEntity<Permissao> findById(@PathVariable("id") Long id){
        Permissao permissao = permissaoService.findById(id)
        return ResponseEntity.ok(permissao) as ResponseEntity<Permissao>
    }

    @PostMapping
    ResponseEntity<Permissao> create(@RequestBody Permissao permissao){
        Permissao permissaoCreated = permissaoService.create(permissao)
        return ResponseEntity.status(HttpStatus.CREATED).body(permissaoCreated)
    }

    @PutMapping("/{id}")
    ResponseEntity<Permissao> update(@RequestBody Permissao permissao, @PathVariable("id") Long id){
        Permissao permissaoUpdated = permissaoService.update(permissao, id)
        return ResponseEntity.ok(permissaoUpdated)
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Permissao> remove(@PathVariable("id") Long id){
        Permissao permissaoRemoved = permissaoService.remove(id)
        return ResponseEntity.ok(permissaoRemoved)
    }


}
