package br.com.r7.calendario.dto

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

data class LoginRequestDTO(@field:NotBlank(message = "{validation.notBlank}") @field:Email(message = "{validation.emailInvalid}") val email: String? = "",
                           @field:NotBlank(message = "{validation.notBlank}") val password: String? = "") {

    fun build(): UsernamePasswordAuthenticationToken {
        return UsernamePasswordAuthenticationToken(email, password)
    }

}