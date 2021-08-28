package br.com.felipelima.api.expressfood.domain.model

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id

@Entity
@ToString(includePackage = false,includeNames = true)
@EqualsAndHashCode
class Restaurante {
    @Id
    @Column(name="restaurante_id")
    Long id

    String nome

    @Column(name="taxa_frete")
    BigDecimal taxaFrete
}
