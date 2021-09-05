package br.com.felipelima.api.expressfood.domain.repository

import br.com.felipelima.api.expressfood.domain.model.Restaurante
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RestauranteRepository extends JpaRepository<Restaurante, Long>{

}