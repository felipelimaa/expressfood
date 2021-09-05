package br.com.felipelima.api.expressfood.domain.repository

import br.com.felipelima.api.expressfood.domain.model.Cidade
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CidadeRepository extends JpaRepository<Cidade, Long>{

}