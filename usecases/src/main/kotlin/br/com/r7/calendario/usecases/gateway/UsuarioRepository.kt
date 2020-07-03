package br.com.r7.calendario.usecases.gateway

import br.com.r7.calendario.usecases.usuario.LoginUsuarioUseCase
import br.com.r7.calendario.usecases.usuario.NovoUsuarioUseCase

interface UsuarioRepository : NovoUsuarioUseCase.UsuarioRepository,LoginUsuarioUseCase.UsuarioRepository {
}