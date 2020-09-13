package br.com.r7.calendario.core.dataproviders.repository.usuario

import br.com.r7.calendario.core.Usuario
import br.com.r7.calendario.core.dataproviders.entities.UsuarioEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface DBUsuarioRepository : JpaRepository<UsuarioEntity, Long>{

    fun existsByLogin(login: String): Boolean
    fun countByLoginIn(login: List<String>): Long
    fun findByLogin(login: String): Optional<UsuarioEntity>

}