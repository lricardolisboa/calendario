package br.com.r7.calendario.security.config

import br.com.r7.calendario.dto.ApiErrorDTO
import br.com.r7.calendario.security.JwtAuthenticationFilter
import br.com.r7.calendario.security.TokenManager
import br.com.r7.calendario.security.toUsuarioLogado
import br.com.r7.calendario.usecases.usuario.LoginUsuarioUseCase
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.boot.web.servlet.server.Encoding
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.BeanIds
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import java.io.IOException
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Configuration
@EnableWebSecurity
class SecurityConfiguration(val loginUsuarioUseCase: LoginUsuarioUseCase,
                            val tokenManager: TokenManager) : WebSecurityConfigurerAdapter() {


    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Throws(Exception::class)
    override fun authenticationManagerBean(): AuthenticationManager {
        return super.authenticationManagerBean()
    }

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http.antMatcher("/api/**")
                .authorizeRequests()
                .antMatchers("/api/auth/**").permitAll()
                .antMatchers(HttpMethod.POST,"/api/usuarios").permitAll()
                .anyRequest().authenticated()
                .and()
                .cors()
                .and()
                .csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(JwtAuthenticationFilter(tokenManager, loginUsuarioUseCase),
                        UsernamePasswordAuthenticationFilter::class.java)
                .exceptionHandling()
                .authenticationEntryPoint(JwtAuthenticationEntryPoint())
    }

    @Throws(Exception::class)
    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(UserDetailsService { username -> this.loginUsuarioUseCase.findByLogin(username).toUsuarioLogado() })
                .passwordEncoder(BCryptPasswordEncoder())
    }

    @Throws(Exception::class)
    override fun configure(web: WebSecurity) {
        web.ignoring().antMatchers("/**.html", "/v2/api-docs", "/webjars/**",
                "/configuration/**", "/swagger-resources/**", "/css/**", "/**.ico", "/js/**")
    }

    private class JwtAuthenticationEntryPoint : AuthenticationEntryPoint {
        private val logger = LoggerFactory.getLogger(JwtAuthenticationEntryPoint::class.java)

        @Throws(IOException::class, ServletException::class)
        override fun commence(request: HttpServletRequest, response: HttpServletResponse,
                              authException: AuthenticationException) {
            logger.error("Um acesso não autorizado foi verificado. Mensagem: {}", authException.message)
            val messageError = jacksonObjectMapper().writeValueAsString(ApiErrorDTO("Acesso Negado."))
            response.contentType = "application/json";
            response.status = HttpServletResponse.SC_UNAUTHORIZED;
            response.outputStream.println(messageError)
        }
    }
}