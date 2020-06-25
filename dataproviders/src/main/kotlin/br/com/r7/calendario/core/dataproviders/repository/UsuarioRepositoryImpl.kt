package br.com.r7.calendario.core.dataproviders.repository

import br.com.r7.calendario.core.Usuario
import br.com.r7.calendario.core.dataproviders.entities.UsuarioEntity
import br.com.r7.calendario.core.dataproviders.entities.toEntity
import br.com.r7.calendario.core.dataproviders.entities.toUsuario
import br.com.r7.calendario.core.dataproviders.helpers.unwrap
import br.com.r7.calendario.usecases.gateway.UsuarioRepository
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import kotlin.math.log

@Repository
class UsuarioRepositoryImpl(private val dbUsuarioRepository: DBUsuarioRepository) : UsuarioRepository {

    override fun salvar(usuario: Usuario) : Usuario {
        val usuarioEntity = usuario.toEntity()
        return this.dbUsuarioRepository.save(usuarioEntity).unwrap(UsuarioEntity::toUsuario)
    }

    override fun isUsuarioCadastrado(login : String) : Boolean {
        return this.dbUsuarioRepository.existsByLogin(login)
    }

}