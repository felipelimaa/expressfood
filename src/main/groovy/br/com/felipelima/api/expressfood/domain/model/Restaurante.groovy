package br.com.felipelima.api.expressfood.domain.model

import br.com.felipelima.api.expressfood.core.validation.Groups
import br.com.felipelima.api.expressfood.core.validation.ValidaDescricaoQuandoValorZerado
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp

import javax.persistence.Column
import javax.persistence.Embedded
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.JoinTable
import javax.persistence.ManyToMany
import javax.persistence.ManyToOne
import javax.persistence.OneToMany
import javax.validation.Valid
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.PositiveOrZero
import javax.validation.groups.ConvertGroup
import javax.validation.groups.Default
import java.time.LocalDateTime

@ValidaDescricaoQuandoValorZerado(atributoDescricao = "nome", atributoDescricaoConteudo = "Frete Grátis", atributoValor = "taxaFrete")
@Entity
@ToString(includePackage = false, includeNames = true)
@EqualsAndHashCode
class Restaurante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id

    @NotBlank
    @Column(nullable = false)
    String nome

    @NotNull
    @PositiveOrZero
    @Column(name="taxa_frete", nullable = false)
    BigDecimal taxaFrete

    @Valid
    @NotNull
    @ConvertGroup(from = Default.class, to = Groups.CozinhaId.class)
    @JsonIgnoreProperties("hibernateLazyInitializer")
    @ManyToOne(fetch = FetchType.LAZY)
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

    @JsonIgnore
    @CreationTimestamp
    @Column(nullable = false, columnDefinition = "datetime")
    LocalDateTime dataCadastro

    @JsonIgnore
    @UpdateTimestamp
    @Column(nullable = false, columnDefinition = "datetime")
    LocalDateTime dataAtualizacao

    @JsonIgnore
    @OneToMany(mappedBy = "restaurante")
    List<Produto> produtos = new ArrayList<>()


}
