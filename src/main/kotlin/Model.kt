package model
//
import kotlinx.datetime.*

enum class Gender {
    MASCULINO, FEMININO
}

enum class TipoEvento {
    SOCIAL, CORPORATIVO, ACADÊMICO, CULTURAL, RELIGIOSOS, ESPORTIVOS,
    FEIRA, CONGRESSO, OFICINA, CURSO, TREINAMENTO, AULA, SEMINÁRIO,
    PALESTRA, SHOW, FESTIVAL, EXPOSIÇÃO, RETIRO, CULTO,
    CELEBRAÇÃO, CAMPEONATO, CORRIDA
}

enum class Modalidade {
    PRESENCIAL, REMOTO, HÍBRIDO
}

data class User(
    var username: String,
    var birthday: LocalDate,
    val email: String, // ID único para busca
    var gender: Gender,
    var password: String,
    var isOrganizer: Boolean,
    var cnpj: String = "",
    var razaoSocial: String = "",
    var nomeFantasia: String = "",
    var isActive: Boolean = true
)

data class Evento(
    var nomeEvento: String,
    var descricao: String,
    var dataInicio: LocalDateTime,
    var dataFim: LocalDateTime,
    var eventoPrincipal: String,
    var capacidadeMaxima: Int = 0,
    var localEvento: String,
    var precoUnitario: Float = 0.0F,
    var taxaEstorno: Float = 0.0F,
    var estornaValor: Boolean = false,
    var isEventActive: Boolean = false,
    var tipoEvento: TipoEvento,
    var modalidade: Modalidade,
    var organizadorEmail: String, // FK para User
    var ingressosVendidos: Int = 0
)

data class Ingresso(
    val id: Int,
    val nomeDoEvento: String,
    val emailDono: String,
    val dataDoEvento: LocalDateTime,
    var status: String = "ATIVO",
    val valorPago: Float
)