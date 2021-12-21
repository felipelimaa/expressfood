package br.com.felipelima.api.expressfood.domain.model

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@Entity
@ToString(includePackage = false, includeNames = true)
@EqualsAndHashCode
class ItemPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id

    BigDecimal precoUnitario

    BigDecimal precoTotal

    Integer quantidade

    String observacao

    @ManyToOne
    @JoinColumn(nullable = false)
    Pedido pedido

    @ManyToOne
    @JoinColumn(nullable = false)
    Produto produto
}
