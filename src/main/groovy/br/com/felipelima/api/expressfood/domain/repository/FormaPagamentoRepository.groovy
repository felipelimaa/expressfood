package br.com.felipelima.api.expressfood.domain.repository

import br.com.felipelima.api.expressfood.domain.model.FormaPagamento
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface FormaPagamentoRepository extends JpaRepository<FormaPagamento, Long>{

}