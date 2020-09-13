package br.com.r7.calendario.usecases.evento

import br.com.r7.calendario.core.Evento
import br.com.r7.calendario.usecases.exceptions.UsuarioJaCadastradoException
import br.com.r7.calendario.usecases.exceptions.UsuarioNaoEncontradoException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class NovoEventoUseCase (private val eventoRepository: EventoRepository,
                        private val usuarioRepository: UsuarioRepository) {

    @Throws(UsuarioNaoEncontradoException::class)
    @Transactional
    fun salvar(evento: Evento)  : Evento{
        val todosConvidadosCadastrados = this.usuarioRepository.isListaUsuariosCadastrados(evento.convidados)
        if(todosConvidadosCadastrados.not()){
            throw UsuarioNaoEncontradoException("Existe um convidado não cadastrado na plataforma.")
        }
        return this.eventoRepository.salvar(evento)
    }

    interface EventoRepository {
        fun salvar(evento : Evento): Evento
    }

    interface UsuarioRepository {
        fun isListaUsuariosCadastrados(logins: List<String>): Boolean
    }

}