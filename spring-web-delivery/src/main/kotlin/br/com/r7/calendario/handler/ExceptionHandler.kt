package br.com.r7.calendario.handler

import br.com.r7.calendario.dto.ApiErrorDTO
import br.com.r7.calendario.usecases.exceptions.UsuarioExistenteException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionHandler() {

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
    @ExceptionHandler(UsuarioExistenteException::class)
    fun camposInvalidos(ex: UsuarioExistenteException) : ApiErrorDTO{
        return ApiErrorDTO(ex.message!!)
    }

}

