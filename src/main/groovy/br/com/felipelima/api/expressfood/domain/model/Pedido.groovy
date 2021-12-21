package br.com.felipelima.api.expressfood.domain.model

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import org.hibernate.annotations.CreationTimestamp

import javax.persistence.Column
import javax.persistence.Embedded
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.OneToMany
import java.time.LocalDateTime

@Entity
@ToString(includePackage = false, includeNames = true)
@EqualsAndHashCode
class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id

    @Column(nullable = false)
    BigDecimal subtotal

    @Column(nullable = false)
    BigDecimal taxaFrete

    @Column(nullable = false)
    BigDecimal valorTotal

    @Embedded
    Endereco enderecoEntrega

    StatusPedido status

    @CreationTimestamp
    LocalDateTime dataCriacao

    LocalDateTime dataConfirmacao
    LocalDateTime dataCancelamento
    LocalDateTime dataEntrega

    @ManyToOne
    @JoinColumn(nullable = false)
    FormaPagamento formaPagamento

    @ManyToOne
    @JoinColumn(name = "usuario_cliente_id", nullable = false)
    Usuario cliente

    @OneToMany(mappedBy = "pedido")
    List<ItemPedido> itens = new ArrayList<>()

}
