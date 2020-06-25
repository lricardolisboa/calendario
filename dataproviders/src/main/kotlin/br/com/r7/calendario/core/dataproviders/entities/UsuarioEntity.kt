package br.com.r7.calendario.core.dataproviders.entities

import br.com.r7.calendario.core.Usuario
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "usuarios")
data class UsuarioEntity(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long?,
        @Column val login: String,
        @Column val nome: String,
        @Column(name = "senha") val senhaCriptografada: String,
        @Column(name = "data_cadastro") val dataCadastro: LocalDateTime
)

fun UsuarioEntity.toUsuario() = Usuario(
        id = id,
        login = login,
        dataCadastro = dataCadastro,
        nome = nome,
        senha = senhaCriptografada
)

fun Usuario.toEntity() = UsuarioEntity(
        id = id,
        login = login,
        dataCadastro = dataCadastro,
        nome = nome,
        senhaCriptografada = senha
)