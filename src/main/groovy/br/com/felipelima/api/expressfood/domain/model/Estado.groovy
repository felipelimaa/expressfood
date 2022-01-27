package br.com.felipelima.api.expressfood.domain.model

import br.com.felipelima.api.expressfood.core.validation.Groups
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Entity
@ToString(includePackage = false, includeNames = true)
@EqualsAndHashCode
class Estado {

    @NotNull(groups = Groups.EstadoId.class)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id

    @NotBlank
    @Column(nullable = false, name = "nome_estado")
    String nome

    @Column(nullable = false, length = 2)
    String uf
}
