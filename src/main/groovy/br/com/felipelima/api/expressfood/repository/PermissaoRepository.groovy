package br.com.felipelima.api.expressfood.repository

import br.com.felipelima.api.expressfood.domain.model.Permissao
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PermissaoRepository extends JpaRepository<Permissao, Long> {

}