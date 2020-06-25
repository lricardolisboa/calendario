package br.com.r7.calendario.dto

import br.com.r7.calendario.core.Usuario
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.PastOrPresent

@JsonIgnoreProperties()
data class UsuarioDTO(val id: Long? = null,
                      @field:NotBlank(message = "{validation.notBlank}") val nome: String = "",
                      @field:NotBlank(message = "{validation.notBlank}") @field:Email(message = "{validation.emailInvalid}") val login: String = "",
                      @field:NotBlank(message = "{validation.notBlank}") var senha: String = "",
                      @field:PastOrPresent(message = "{validation.pastOrPresent}") val dataCadastro: LocalDateTime = LocalDateTime.now())

fun Usuario.toUsuarioDTO() = UsuarioDTO(
        id = id,
        nome = nome,
        login = login,
        dataCadastro = dataCadastro
)

fun UsuarioDTO.toUsuario() = Usuario(
        id = id,
        nome = nome,
        login = login,
        senha = senha,
        dataCadastro = dataCadastro
)
