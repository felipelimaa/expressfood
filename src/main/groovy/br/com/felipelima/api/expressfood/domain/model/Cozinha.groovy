package br.com.felipelima.api.expressfood.domain.model

import br.com.felipelima.api.expressfood.core.validation.Groups
import com.fasterxml.jackson.annotation.JsonIgnore
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.OneToMany
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Entity
@ToString(includePackage = false, includeNames = true)
@EqualsAndHashCode
class Cozinha {

    @NotNull(groups = Groups.CozinhaId.class)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id

    @NotBlank
    @Column(nullable = false)
    String nome

    @JsonIgnore
    @OneToMany(mappedBy = "cozinha")
    List<Restaurante> restaurantes = new ArrayList<>()
}
