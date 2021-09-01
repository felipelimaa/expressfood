package br.com.felipelima.api.expressfood.controller

import br.com.felipelima.api.expressfood.domain.model.FormaPagamento
import br.com.felipelima.api.expressfood.service.FormaPagamentoService
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
@RequestMapping("/forma_pagamento")
class FormaPagamentoController {
    @Autowired
    FormaPagamentoService formaPagamentoService

    @GetMapping
    ResponseEntity<FormaPagamento> findAll(){
        List<FormaPagamento> formasPagamento = formaPagamentoService.findAll()

        return ResponseEntity.ok(formasPagamento) as ResponseEntity<FormaPagamento>
    }

    @GetMapping("/{id}")
    ResponseEntity<FormaPagamento> findById(@PathVariable("id") Long id){
        FormaPagamento formaPagamento = formaPagamentoService.findById(id)
        return ResponseEntity.ok(formaPagamento) as ResponseEntity<FormaPagamento>
    }

    @PostMapping
    ResponseEntity<FormaPagamento> create(@RequestBody FormaPagamento formaPagamento){
        FormaPagamento formaPagamentoAdded = formaPagamentoService.create(formaPagamento)
        return ResponseEntity.status(HttpStatus.CREATED).body(formaPagamentoAdded)
    }

    @PutMapping("/{id}")
    ResponseEntity<FormaPagamento> update(@RequestBody FormaPagamento formaPagamento, @PathVariable("id") Long id){
        FormaPagamento formaPagamentoUpdated = formaPagamentoService.update(formaPagamento, id)
        return ResponseEntity.ok(formaPagamentoUpdated)
    }

    @DeleteMapping("/{id}")
    ResponseEntity<FormaPagamento> remove(@PathVariable("id") Long id){
        FormaPagamento formaPagamentoRemoved = formaPagamentoService.delete(id)
        return ResponseEntity.ok(formaPagamentoRemoved)
    }
}
