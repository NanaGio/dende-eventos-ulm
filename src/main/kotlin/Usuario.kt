import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.periodUntil
import kotlinx.datetime.toLocalDateTime

val emailValido = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")
val dataValida = Regex("""\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12]\d|3[01])""")

lateinit var user: User
lateinit var gender: Gender

//Cadastro de usuário comum
fun cadastroUserComum (){
    println("Você escolheu a opção de cadastrar usuário comum.")

    println("Escreva seu nome: ")
    var username: String
    do {
        username = readln().lowercase().replaceFirstChar { it.uppercase() }
        when {
            username.isBlank() -> println("É necessário digitar nome de usuário.")
            username.any { it.isDigit() } -> println("O nome de usuário não pode conter números.")
        }
    } while (username.isBlank() || username.any { it.isDigit() })

    println("Digite sua data de nascimento YYYY-MM-DD: ")
    var birthday: String
    do {
        birthday = readln()
        when {
            birthday.isBlank() -> println("É necessário digitar data de nascimento.")
            !dataValida.matches(birthday) -> println("Formato de data inválida.")
        }
    } while (birthday.isBlank() || !dataValida.matches(birthday))

    println("Digite seu gênero: [M]asculino [F]eminino")
    var entryGender: String
    do {
        entryGender = readln().uppercase()
        when {
            entryGender.isBlank() -> println("É necessário digitar o gênero.")
            entryGender.any { it.isDigit() } -> println("Não pode conter números.")
            entryGender == "F" -> gender = Gender.FEMININO
            entryGender == "M" -> gender = Gender.MASCULINO
            else -> println("Opção inválida. Tente novamente.")
        }
    } while (entryGender.isBlank() || entryGender.any { it.isDigit() } || entryGender != "M" && entryGender != "F")

    println("Escreva seu Email: ")
    var email: String
    do {
        email = readln().lowercase().replaceFirstChar { it.uppercase() }
        when {
            email.isBlank() -> println("É necessário digitar o e-mail.")
            !emailValido.matches(email) -> println("Formato de e-mail inválido.")
        }
    } while (email.isBlank() || !emailValido.matches(email))

    println("Digite a senha: ")
    var password: String
    do {
        password = readln()
        if (password.isBlank()) println("É necessário digitar senha!")
    } while (password.isBlank())

    val formatedBirthday = LocalDate.parse(birthday)
    user = User(username, formatedBirthday, email, gender, password, isOrganizer = false)

    println("=== Usuário criado com sucesso ===")
    println(user)
}

//Cadastro de usuário organizador
fun cadastroUserOrganizador (){
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

    println("Escreva seu nome: ")
    var username: String
    do {
        username = readln().lowercase().replaceFirstChar { it.uppercase() }
        when {
            username.isBlank() -> println("É necessário digitar nome de usuário.")
            username.any { it.isDigit() } -> println("O nome de usuário não pode conter números.")
        }
    } while (username.isBlank() || username.any { it.isDigit() })

    println("Digite sua data de nascimento YYYY-MM-DD: ")
    var birthday: String
    do {
        birthday = readln()
        when {
            birthday.isBlank() -> println("É necessário digitar data de nascimento.")
            !dataValida.matches(birthday) -> println("Formato de data inválida. Tente novamente.")
        }
    } while (birthday.isBlank() || !dataValida.matches(birthday))

    println("Digite seu gênero: [M]asculino [F]eminino")
    var entryGender: String
    do {
        entryGender = readln().uppercase()
        when {
            entryGender.isBlank() -> println("É necessário digitar o gênero.")
            entryGender.any { it.isDigit() } -> println("Não pode conter números.")
            entryGender == "F" -> gender = Gender.FEMININO
            entryGender == "M" -> gender = Gender.MASCULINO
            else -> println("Opção inválida.")
        }
    } while (entryGender.isBlank() || entryGender.any { it.isDigit() } || entryGender != "M" && entryGender != "F")

    println("Escreva seu e-mail: ")
    var email: String
    do {
        email = readln().lowercase().replaceFirstChar { it.uppercase() }
        when {
            email.isBlank() -> println("É necessário digitar o E-mail.")
            !emailValido.matches(email) -> println("Formato de email inválido.")
        }
    } while (email.isBlank() || !emailValido.matches(email))

    println("Digite a senha: ")
    var password: String
    do {
        password = readln()
        if (password.isBlank()) println("É necessário digitar a senha.")
    } while (password.isBlank())

    if (empresa == "S") {
        println("Digite o CNPJ, apenas números: ")
        var cnpj: String
        do {
            cnpj = readln()
            when {
                cnpj.isBlank() -> println("É necessário digitar o CNPJ.")
                !cnpj.all { it.isDigit() } -> println("Digite somente números.")
                cnpj.length != 14 -> println("Insira exatamente 14 caracteres.")
            }
        } while (cnpj.isBlank() || !cnpj.all { it.isDigit() } || cnpj.length != 14)

        println("Digite a Razão Social: ")
        var razaoSocial: String
        do {
            razaoSocial = readln().lowercase()
            if (razaoSocial.isBlank()) println("É necessário digitar Razão Social.")
        } while (razaoSocial.isBlank())

        println("Digite o Nome Fantasia: ")
        var nomeFantasia: String
        do {
            nomeFantasia = readln().lowercase()
            if (nomeFantasia.isBlank()) println("É necessário digitar Nome Fantasia.")
        } while (nomeFantasia.isBlank())

        val formatedBirthday = LocalDate.parse(birthday)
        user = User(username, formatedBirthday, email, gender, password, isOrganizer = true, cnpj, razaoSocial, nomeFantasia)
    } else {
        val formatedBirthday = LocalDate.parse(birthday)
        user = User(username, formatedBirthday, email, gender, password, isOrganizer = true)
    }

    println("=== Organizador criado com sucesso ===")
    println(user)
}

//Visualizar perfil
fun visualizarPerfil (){
    val today = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
    val idadeExata = user.birthday.periodUntil(today)

    println("""
        ======= MEU PERFIL =======
        Nome: ${user.username}
        E-mail: ${user.email}
        Gênero: ${user.gender}
    """.trimIndent())

    if (user.isOrganizer) {
        println("CNPJ: ${user.cnpj}")
        println("Nome Fantasia: ${user.nomeFantasia}")
    } else {
        println("Idade detalhada: ${idadeExata.years} anos, ${idadeExata.months} meses e ${idadeExata.days} dias")
    }

    println("Status: ${if (user.isActive) "Ativo" else "Inativo"}")
}

//Alterar perfil
fun alterarPerfil (){
    if (user.isOrganizer) {
        println("O que deseja alterar? (1 - Nome de Usuário, 2 - Senha, 3 - CNPJ, 4 - Nome Fantasia)")
        when (readln()) {
            "1" -> {
                print("Insira o novo nome: ")
                var username: String
                do {
                    username = readln().lowercase().replaceFirstChar { it.uppercase() }
                    when {
                        username.isBlank() -> println("É necessário digitar nome de usuário.")
                        username.any { it.isDigit() } -> println("Não pode conter números.")
                    }
                } while (username.isBlank() || username.any { it.isDigit() })
                user.username = username
            }
            "2" -> {
                print("Insira a nova senha: ")
                var password: String
                do {
                    password = readln()
                    if (password.isBlank()) println("É necessário digitar senha!")
                } while (password.isBlank())
                user.password = password
            }
            "3" -> {
                print("Insira o novo CNPJ (apenas números): ")
                var cnpj: String
                do {
                    cnpj = readln()
                    when {
                        cnpj.isBlank() -> println("É necessário digitar CNPJ.")
                        !cnpj.all { it.isDigit() } -> println("CNPJ deve conter apenas números.")
                        cnpj.length != 14 -> println("CNPJ deve ter 14 dígitos.")
                    }
                } while (cnpj.isBlank() || !cnpj.all { it.isDigit() } || cnpj.length != 14)
                user.cnpj = cnpj
            }
            "4" -> {
                print("Insira o novo Nome Fantasia: ")
                var nomeFantasia: String
                do {
                    nomeFantasia = readln()
                    if (nomeFantasia.isBlank()) println("É necessário digitar Nome Fantasia.")
                } while (nomeFantasia.isBlank())
                user.nomeFantasia = nomeFantasia
            }
            else -> println("Opção inválida!")
        }
    } else {
        println("O que deseja alterar? (1 - Nome, 2 - Senha, 3 - Gênero, 4 - Data)")
        when (readln()) {
            "1" -> {
                print("Insira o novo nome: ")
                var username: String
                do {
                    username = readln().lowercase().replaceFirstChar { it.uppercase() }
                    when {
                        username.isBlank() -> println("É necessário digitar nome.")
                        username.any { it.isDigit() } -> println("Não pode conter números.")
                    }
                } while (username.isBlank() || username.any { it.isDigit() })
                user.username = username
            }
            "2" -> {
                print("Insira a nova senha: ")
                var password: String
                do {
                    password = readln()
                    if (password.isBlank()) println("É necessário digitar senha!")
                } while (password.isBlank())
                user.password = password
            }
            "3" -> {
                print("Insira o novo gênero: [M]asculino [F]eminino: ")
                var entryGender: String
                do {
                    entryGender = readln().uppercase()
                    when (entryGender) {
                        "M" -> user.gender = Gender.MASCULINO
                        "F" -> user.gender = Gender.FEMININO
                        else -> println("Opção inválida! Digite M ou F.")
                    }
                } while (entryGender != "M" && entryGender != "F")
            }
            "4" -> {
                print("Insira a nova data (AAAA-MM-DD): ")
                var birthday: String
                do {
                    birthday = readln()
                    when {
                        birthday.isBlank() -> println("É necessário digitar data.")
                        !dataValida.matches(birthday) -> println("Formato inválido (AAAA-MM-DD).")
                    }
                } while (birthday.isBlank() || !dataValida.matches(birthday))
                user.birthday = LocalDate.parse(birthday)
            }
            else -> println("Opção inválida!")
        }
    }
    println("Alteração realizada com sucesso!")
}

//inativar conta
fun inativarUsuario (){
    println("Confirmar desativação? (1 - Sim, 2 - Não)")
    if (readln() == "1") {
        user.isActive = false
        println("Conta desativada. Para reativá-la, realize login na plataforma novamente.")
    }
}

//ativar conta
fun reativarUsuario (){
    println("Deseja confirmar a ativação do usuário: [S]im [N]ão")
    var confirmacao: String
    do {
        confirmacao = readln().lowercase()
        when {
            confirmacao.isBlank() -> println("É necessário digitar S ou N.")
            confirmacao == "s" -> {
                user.isActive = true
                println("Usuário ativado!")
            }
            confirmacao == "n" -> println("Pulando...")
            else -> println("Opção inválida!")
        }
    } while (confirmacao.isBlank() || confirmacao != "s" && confirmacao != "n")
}


