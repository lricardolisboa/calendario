package br.com.r7.calendario.core.dataproviders.repository

import br.com.r7.calendario.core.dataproviders.entities.UsuarioEntity
import org.springframework.data.jpa.repository.JpaRepository

interface DBUsuarioRepository : JpaRepository<UsuarioEntity, String>{

    fun existsByLogin(login: String): Boolean

}