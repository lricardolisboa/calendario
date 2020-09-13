package br.com.r7.calendario.handler

import br.com.r7.calendario.dto.ApiErrorDTO
import br.com.r7.calendario.usecases.exceptions.UsuarioJaCadastradoException
import br.com.r7.calendario.usecases.exceptions.UsuarioNaoEncontradoException
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.security.core.AuthenticationException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionHandler {

    private val logger = LoggerFactory.getLogger(ExceptionHandler::class.java)

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun camposInvalidos(ex: MethodArgumentNotValidException) : ApiErrorDTO{
        val message = ex.bindingResult.fieldErrors.joinToString(", ") {
            val mensagemFormatada = it.defaultMessage?.replace("{field}", it.field.capitalize())
            "$mensagemFormatada"
        }
        return ApiErrorDTO(message)
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(UsuarioJaCadastradoException::class)
    fun camposInvalidos(ex: UsuarioJaCadastradoException) : ApiErrorDTO{
        return ApiErrorDTO(ex.message!!)
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UsuarioNaoEncontradoException::class)
    fun camposInvalidos(ex: UsuarioNaoEncontradoException) : ApiErrorDTO{
        return ApiErrorDTO(ex.message!!)
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AuthenticationException::class)
    fun camposInvalidos(ex: AuthenticationException) : ApiErrorDTO{
        return ApiErrorDTO("Usuário ou senha inválidos")
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception::class)
    fun camposInvalidos(ex: Exception) : ApiErrorDTO{
        logger.error("Erro Inesperado na aplicação. Mensagem: {}", ex.message)
        return ApiErrorDTO("Erro Inesperado na aplicação.")
    }

}

