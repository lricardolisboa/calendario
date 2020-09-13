package br.com.r7.calendario.usecases.gateway

import br.com.r7.calendario.usecases.agenda.NovaAgendaUseCase
import br.com.r7.calendario.usecases.usuario.NovoUsuarioUseCase

interface AgendaRepository : NovaAgendaUseCase.AgendaRepository, NovoUsuarioUseCase.AgendaRepository{
}