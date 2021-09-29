package br.com.felipelima.api.expressfood.domain.model

import br.com.felipelima.api.expressfood.domain.model.Cozinha
import com.fasterxml.jackson.annotation.JsonIgnore
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

import javax.persistence.Column
import javax.persistence.Embedded
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.JoinTable
import javax.persistence.ManyToMany
import javax.persistence.ManyToOne

@Entity
@ToString(includePackage = false, includeNames = true)
@EqualsAndHashCode
class Restaurante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id

    @Column(nullable = false)
    String nome

    @Column(name="taxa_frete", nullable = false)
    BigDecimal taxaFrete

    @ManyToOne
    @JoinColumn(name = "cozinha_id", nullable = false)
    Cozinha cozinha

    @JsonIgnore
    @Embedded
    Endereco endereco

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "restaurante_forma_pagamento",
            joinColumns = @JoinColumn(name = "restaurante_id"),
            inverseJoinColumns = @JoinColumn(name = "forma_pagamento_id")
    )
    List<FormaPagamento> formasPagamento = new ArrayList<>()

}
