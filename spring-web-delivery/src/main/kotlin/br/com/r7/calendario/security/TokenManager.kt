package br.com.r7.calendario.security

import io.jsonwebtoken.Claims
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.util.*

@Component
class TokenManager(@Value("\${jwt.secret}") val secret: String,
                   @Value("\${jwt.expiration}") val expirationInMillis: Long) {

    fun generateToken(authentication: Authentication): String {
        val user: UsuarioLogado = authentication.principal as UsuarioLogado
        val now = Date()
        val expiration = Date(now.time + expirationInMillis)
        return Jwts.builder()
                .setIssuer("Calendário")
                .setSubject(user.id.toString())
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact()
    }

    fun isValid(jwt: String): Boolean {
        return try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(jwt)
            true
        } catch (e: JwtException) {
            false
        } catch (e: IllegalArgumentException) {
            false
        }
    }

    fun getUserIdFromToken(jwt: String): Long {
        val claims: Claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(jwt).body
        return claims.subject.toLong()
    }
}