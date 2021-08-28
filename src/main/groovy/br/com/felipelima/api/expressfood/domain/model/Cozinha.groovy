package br.com.felipelima.api.expressfood.domain.model

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
@ToString(includePackage = false,includeNames = true)
@EqualsAndHashCode
class Cozinha {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="cozinha_id")
    Long id

    String nome
}
