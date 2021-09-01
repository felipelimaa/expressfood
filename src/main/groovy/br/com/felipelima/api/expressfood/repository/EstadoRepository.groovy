package br.com.felipelima.api.expressfood.repository

import br.com.felipelima.api.expressfood.domain.model.Estado
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface EstadoRepository extends JpaRepository<Estado, Long>{

}