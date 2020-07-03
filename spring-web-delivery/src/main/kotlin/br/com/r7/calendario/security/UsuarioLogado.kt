package br.com.r7.calendario.security

import br.com.r7.calendario.core.Usuario
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

data class UsuarioLogado(val id: Long,val nome: String, val senha: String) : UserDetails {

    override fun getAuthorities(): List<SimpleGrantedAuthority> =  listOf(SimpleGrantedAuthority("ROLE_USER"))

    override fun isEnabled(): Boolean = true

    override fun getUsername(): String = nome

    override fun isCredentialsNonExpired(): Boolean = true

    override fun getPassword(): String  = senha

    override fun isAccountNonExpired(): Boolean  = true

    override fun isAccountNonLocked(): Boolean = true

}

fun Usuario.toUsuarioLogado() = UsuarioLogado(id!!,nome,senha)
