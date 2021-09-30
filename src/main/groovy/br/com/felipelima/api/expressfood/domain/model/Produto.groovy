package br.com.felipelima.api.expressfood.domain.model

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@Entity
@ToString(includePackage = false, includeNames = true)
@EqualsAndHashCode
class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id

    @Column(nullable = false)
    String nome

    String descricao

    @Column(nullable = false)
    BigDecimal preco

    @Column(nullable = false)
    Boolean ativo

    @ManyToOne
    @JoinColumn(name = "restaurante_id", nullable = false)
    Restaurante restaurante

}
