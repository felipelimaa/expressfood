package br.com.felipelima.api.expressfood.domain.model

import br.com.felipelima.api.expressfood.core.validation.Groups
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.validation.Valid
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.groups.ConvertGroup

@Entity
@ToString(includePackage = false, includeNames = true)
@EqualsAndHashCode
class Cidade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id

    @NotBlank
    @Column(nullable = false)
    String nome

    @Valid
    @ConvertGroup(to = Groups.EstadoId.class)
    @NotNull
    @ManyToOne
    @JoinColumn(name = "estado_id", nullable = false)
    Estado estado
}
