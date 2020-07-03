package br.com.r7.calendario.security

import br.com.r7.calendario.usecases.usuario.LoginUsuarioUseCase
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.util.StringUtils
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JwtAuthenticationFilter(private val tokenManager: TokenManager, private val loginUsuarioUseCase: LoginUsuarioUseCase) : OncePerRequestFilter() {


    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        val jwt = getTokenFromRequest(request)
        if (this.tokenManager.isValid(jwt)) {
            val userId: Long = this.tokenManager.getUserIdFromToken(jwt)
            val userDetails: UserDetails = this.loginUsuarioUseCase.findById(userId).toUsuarioLogado()
            val authentication = UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
            SecurityContextHolder.getContext().authentication = authentication
        }
        chain.doFilter(request, response)
    }

    private fun getTokenFromRequest(request: HttpServletRequest): String {
        val bearerToken = request.getHeader("Authorization")
        return if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) bearerToken.substring(7) else ""
    }

}