import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.periodUntil
import kotlinx.datetime.toLocalDateTime
// Import do Model.kt contendo as Data Classes
import model.*
import components.*

val emailValido = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")
val dataValida = Regex("""\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12]\d|3[01])""")

//lateinit var user: User
lateinit var gender: Gender

//Cadastro de usuário comum
fun cadastrarUsuarioComum (): User {
    println("Você escolheu a opção de cadastrar usuário comum.")

    // Substituindo 11 linhas por apenas 1 (ou 2 para formatar)
    val username = readString("Escreva seu nome: ", "Erro: O nome não pode ser vazio.", minLength = 1)
        .trim()
        .lowercase()
        .replaceFirstChar { it.uppercase() }

    var birthday = readLocalDate(
        message = "Digite sua data de nascimento (YYYY-MM-DD): ",
        errorMessage = "ERRO: Data inválida ou formato incorreto (Use: 2000-01-01)."
    )

    var entryGender = readGender(
        message = "Digite seu gênero [M]asculino [F]eminino: ",
        errorMessage = "Opção inválida! Digite apenas 'M' para Masculino ou 'F' para Feminino."
    )

    var email = readEmail(
        message = "Escreva seu Email: ",
        errorMessage = "ERRO: Formato de e-mail inválido (ex: usuario@dende.com)."
    )

    var password = readString(
        message = "Digite a sua senha: ",
        errorMessage = "A senha deve ser inserida. "
    )

    val novoUsuario = User(username, birthday, email, entryGender, password, isOrganizer = false)

    println("=== Usuário criado com sucesso ===")
    return novoUsuario
}

//Cadastro de usuário organizador
fun cadastrarUsuarioOrganizador (): User {
    var cnpj = ""
    var razaoSocial = ""
    var nomeFantasia = ""

    println("Você escolheu a opção de cadastrar usuário organizador.")

    println("Você é uma empresa? [S]im [N]ão")
    var empresa: String
    do {
        empresa = readln().uppercase()
        when {
            empresa.isBlank() -> println("É necessário digitar S ou N.")
            empresa != "S" && empresa != "N" -> println("Opção inválida. Tente novamente.")
        }
    } while (empresa.isBlank() || empresa != "S" && empresa != "N")

    val username = readString("Escreva seu nome: ", "Erro: O nome não pode ser vazio.", minLength = 1)
        .trim()
        .lowercase()
        .replaceFirstChar { it.uppercase() }

    var birthday = readLocalDate(
        message = "Digite sua data de nascimento (YYYY-MM-DD): ",
        errorMessage = "ERRO: Data inválida ou formato incorreto (Use: 2000-01-01)."
    )

    var entryGender = readGender(
        message = "Digite seu gênero [M]asculino [F]eminino: ",
        errorMessage = "Opção inválida! Digite apenas 'M' para Masculino ou 'F' para Feminino."
    )

    var email = readEmail(
        message = "Escreva seu Email: ",
        errorMessage = "ERRO: Formato de e-mail inválido (ex: usuario@dende.com)."
    )

    var password = readString(
        message = "Digite a sua senha: ",
        errorMessage = "A senha deve ser inserida. "
    )

    if (empresa == "S") {
        println("Digite o CNPJ, apenas números: ")
        do {
            cnpj = readln()
            when {
                cnpj.isBlank() -> println("É necessário digitar o CNPJ.")
                !cnpj.all { it.isDigit() } -> println("Digite somente números.")
                cnpj.length != 14 -> println("Insira exatamente 14 caracteres.")
            }
        } while (cnpj.isBlank() || !cnpj.all { it.isDigit() } || cnpj.length != 14)

        var razaoSocial = readString("Digite a Razão Social: ", "É necessário digitar Razão Social.", minLength = 1)

        var nomeFantasia = readString("Digite o Nome Fantasia: ", "É necessário digitar Nome Fantasia.", minLength = 1)

        val novoOrganizador = User(username, birthday, email, entryGender, password, isOrganizer = true, cnpj,
            razaoSocial, nomeFantasia)
    }

    val novoOrganizador = if (empresa == "S") {
        User(
            username,
            birthday,
            email,
            entryGender,
            password,
            isOrganizer = true,
            cnpj,
            razaoSocial,
            nomeFantasia
        )
    } else {
        User(
            username,
            birthday,
            email,
            entryGender,
            password,
            isOrganizer = true
        )
    }

    println("=== Organizador criado com sucesso ===")
    return novoOrganizador
}

//Visualizar perfil
fun visualizarPerfil(usuario: User) {
    val today = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date

    val idadeExata = usuario.birthday.periodUntil(today)

    println("""
        ======= MEU PERFIL =======
        Nome: ${usuario.username}
        E-mail: ${usuario.email}
        Gênero: ${usuario.gender}
    """.trimIndent())

    if (usuario.isOrganizer) {
        // Verifica se os campos não estão vazios antes de imprimir
        if (usuario.cnpj.isNotEmpty()) println("CNPJ: ${usuario.cnpj}")
        if (usuario.nomeFantasia.isNotEmpty()) println("Nome Fantasia: ${usuario.nomeFantasia}")
    } else {
        println("Idade detalhada: ${idadeExata.years} anos, ${idadeExata.months} meses e ${idadeExata.days} dias")
    }

    println("Status: ${if (usuario.isActive) "Ativo" else "Inativo"}")
}

// Alterar perfil
fun alterarPerfil(usuario: User) {
    println("\n--- ALTERAR INFORMAÇÕES DO PERFIL ---")

    if (usuario.isOrganizer) {
        println("1 - Nome de Usuário\n2 - Senha\n3 - CNPJ\n4 - Nome Fantasia\n0 - Cancelar")

        // Substituímos o readln() pelo readInt para garantir que a opção existe
        when (readInt("Escolha o que alterar: ", "Opção inválida!", 0..4)) {
            1 -> usuario.username = readString("Novo nome: ", "Não pode ser vazio.", 1)
                .lowercase().replaceFirstChar { it.uppercase() }

            2 -> usuario.password = readString("Nova senha: ", "A senha é obrigatória.")

            3 -> usuario.cnpj = readCNPJ(
                "Novo CNPJ (apenas 14 números): ",
                "ERRO: O CNPJ deve ter exatamente 14 dígitos numéricos."
            )

            4 -> usuario.nomeFantasia = readString("Novo Nome Fantasia: ", "Não pode ser vazio.", 1)

            0 -> return
        }
    } else {
        println("1 - Nome\n2 - Senha\n3 - Gênero\n4 - Data de Nascimento\n0 - Cancelar")

        when (readInt("Escolha o que alterar: ", "Opção inválida!", 0..4)) {
            1 -> usuario.username = readString("Novo nome: ", "Não pode ser vazio.", 1)
                .lowercase().replaceFirstChar { it.uppercase() }

            2 -> usuario.password = readString("Nova senha: ", "A senha é obrigatória.")

            3 -> usuario.gender = readGender(
                "Novo gênero [M/F]: ",
                "Opção inválida! Use M ou F."
            )

            4 -> usuario.birthday = readLocalDate(
                "Nova data (AAAA-MM-DD): ",
                "Data inválida! Use o formato 2000-01-01."
            )

            0 -> return
        }
    }
    println("\n[SUCESSO] Alteração realizada com sucesso!")
}

//inativar conta
fun inativarUsuario(usuario: User) {
    val confirmar = readInt(
        message = "Confirmar desativação? (1 - Sim, 2 - Não): ",
        errorMessage = "Opção inválida! Digite 1 para confirmar ou 2 para cancelar.",
        range = 1..2
    )

    if (confirmar == 1) {
        usuario.isActive = false
        println("Conta desativada. Para reativá-la, realize login na plataforma novamente.")
    } else {
        println("Operação cancelada.")
    }
}

//ativar conta
fun reativarUsuario (usuario: User){
    println("Deseja confirmar a ativação do usuário: [S]im [N]ão")
    var confirmacao: String
    do {
        confirmacao = readln().lowercase()
        when {
            confirmacao.isBlank() -> println("É necessário digitar S ou N.")
            confirmacao == "s" -> {
                usuario.isActive = true
                println("Usuário ativado!")
            }
            confirmacao == "n" -> println("Pulando...")
            else -> println("Opção inválida!")
        }
    } while (confirmacao.isBlank() || confirmacao != "s" && confirmacao != "n")
}