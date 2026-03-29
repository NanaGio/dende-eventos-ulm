package model
import kotlinx.datetime.*

enum class Gender {
    MASCULINO,
    FEMININO
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
    var birthday: LocalDate = LocalDate.parse("2000-01-01"),
    val email: String, // ID único para busca
    var gender: Gender,
    var password: String,
    var isOrganizer: Boolean,
    var cnpj: String = "",
    var razaoSocial: String = "",
    var nomeFantasia: String = "",
    var isActive: Boolean = true
) {
    override fun toString(): String {
        val tipo = if (isOrganizer) "ORG" else "USER"
        val status = if (isActive) "✔" else "✘"
        return "${status.padEnd(3)} | ${username.padEnd(15)} | ${email.padEnd(25)} | ${tipo.padEnd(5)} | ${gender.name.padEnd(10)}"
    }
}

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
) {
    override fun toString(): String {
        val status = if (isEventActive) "[ON]" else "[OFF]"
        val ocupacao = "$ingressosVendidos/$capacidadeMaxima"
        val dataStr = "${dataInicio}"

        return "${status.padEnd(6)} | ${nomeEvento.padEnd(18)} | ${dataStr.padEnd(10)} | ${ocupacao.padEnd(10)} | R$ ${precoUnitario.toString().padEnd(6)}"
    }
}


data class Ingresso(
    val id: Int,
    val nomeDoEvento: String,
    val emailDono: String,
    val dataDoEvento: LocalDateTime,
    var status: String = "ATIVO",
    val valorPago: Float
) {
    override fun toString(): String {
        val idStr = "#${id.toString().padStart(3, '0')}"
        return "${idStr.padEnd(6)} | ${nomeDoEvento.padEnd(18)} | ${status.padEnd(10)} | R$ ${valorPago.toString().padEnd(7)}"
    }
}