package repository

import model.*
import kotlinx.datetime.*

object Repositorio {
    // Banco de Dados em Memórias
    private val usuarios = mutableListOf<User>()
    private val eventos = mutableListOf<Evento>()
    private val ingressos = mutableListOf<Ingresso>()

    // --- CRUD USUÁRIOS ---
    fun salvarUsuario(usuario: User) {
        usuarios.add(usuario)
    }

    fun buscarUsuarioPorEmail(email: String): User? {
        return usuarios.find { it.email.equals(email, ignoreCase = true) }
    }

    fun listarTodosUsuarios() = usuarios.toList()

    // --- CRUD EVENTOS ---
    fun salvarEvento(evento: Evento) {
        eventos.add(evento)
    }

    fun buscarEventoPorNome(nome: String): Evento? {
        return eventos.find { it.nomeEvento.equals(nome, ignoreCase = true) }
    }

    fun listarEventosAtivosParaFeed(dataAtual: LocalDateTime): List<Evento> {
        return eventos.filter {
            it.isEventActive &&
                    it.ingressosVendidos < it.capacidadeMaxima &&
                    it.dataFim > dataAtual
        }.sortedWith(compareBy({ it.dataInicio }, { it.nomeEvento }))
    }

    fun listarEventosPorOrganizador(email: String): List<Evento> {
        return eventos.filter { it.organizadorEmail == email }
    }

    // --- CRUD INGRESSOS ---
    fun gerarNovoIdIngresso(): Int = ingressos.size + 1

    fun salvarIngresso(ingresso: Ingresso) {
        ingressos.add(ingresso)
    }

    fun buscarIngressoPorId(id: Int): Ingresso? {
        return ingressos.find { it.id == id }
    }

    fun listarIngressosPorUsuario(email: String): List<Ingresso> {
        return ingressos.filter { it.emailDono == email }
            .sortedWith(compareBy({ it.status != "ATIVO" }, { it.dataDoEvento }))
    }

    // --- FUNÇÕES DE EXCLUSÃO/ALTERAÇÃO GENÉRICAS ---
    // Em memória, a alteração é feita via referência de objeto,
    // mas aqui você pode adicionar logs ou validações extras.
}