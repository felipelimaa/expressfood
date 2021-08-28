package br.com.felipelima.api.expressfood.controller

import br.com.felipelima.api.expressfood.domain.model.Cozinha
import br.com.felipelima.api.expressfood.service.CozinhaService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/cozinha")
class CozinhaController {
    @Autowired
    CozinhaService cozinhaService

    @GetMapping
    ResponseEntity<Cozinha> listar(){
        List<Cozinha> cozinhas = cozinhaService.listar()
        return ResponseEntity.ok(cozinhas) as ResponseEntity<Cozinha>
    }

    @PostMapping
    ResponseEntity<Cozinha> adicionar(@RequestBody Cozinha cozinha){
        Cozinha cozinhaAdicionado = cozinhaService.adicionar(cozinha)
        return ResponseEntity.status(HttpStatus.CREATED).body(cozinhaAdicionado)
    }
}
