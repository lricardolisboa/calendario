package br.com.r7.calendario.controller

import br.com.r7.calendario.dto.LoginRequestDTO
import br.com.r7.calendario.dto.LoginResponseDTO
import br.com.r7.calendario.security.TokenManager
import br.com.r7.calendario.security.UsuarioLogado
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth")
class LoginController(private val authManager: AuthenticationManager,
                      private val tokenManager: TokenManager) {

    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun authenticate(@RequestBody loginInfo: LoginRequestDTO): ResponseEntity<LoginResponseDTO> {
        val authenticationToken: UsernamePasswordAuthenticationToken = loginInfo.build()
        val authentication = this.authManager.authenticate(authenticationToken)

        val jwt: String = this.tokenManager.generateToken(authentication)

        val tokenResponse = LoginResponseDTO("Bearer", jwt)

        return ResponseEntity.ok(tokenResponse)
    }
}