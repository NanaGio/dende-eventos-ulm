data class User(
    var username: String,
    var birthday: String,
    val email: String,
    var gender: Gender,
    var password: String,
    var isOrganizer: Boolean,
    var cnpj: String = "",
    var razaoSocial: String = "",
    var nomeFantasia: String = "",
    var isActive: Boolean = true,
)

enum class Gender {
    MASCULINO,
    FEMININO,
}

fun main() { // Escrever código aqui
    var cadastro = true
    var sistema = true
    val dataValida = Regex("""\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12]\d|3[01])""")
    val emailValido = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")
    lateinit var user: User
    lateinit var gender: Gender

    do {
        println(" - - - Dendê Eventos - - -")
        println("Bem vindo a plataforma Dendê Eventos! Escolha a opção para prosseguir: ")
        println("1 -> Usuário - CADASTRAR:")
        println("2 -> Organizador - CADASTRAR:")

        val opcaoIni = readln().toIntOrNull()

        if (opcaoIni == null) {
            println("Não é permitido nenhum formato além de número")
            continue
        }

        when (opcaoIni) {
            1 -> {
                println("Você escolheu a opção de cadastrar usuário.")
                println("Escreva seu nome: ")
                var username: String
                do {
                    username = readln().lowercase().replaceFirstChar { it.uppercase() }
                    if (username.isBlank()) {
                        println("É necessário digitar nome de usuário")
                        continue
                    } else if (username.any() {it.isDigit()}) {
                        println("Não pode conter números")
                        continue
                    }
                } while (username.isBlank() || username.any() {it.isDigit()})


                println("Digite sua data de nascimento YYYY-MM-DD: ")
                var birthday: String
                do {
                    birthday = readln()
                    if (birthday.isBlank()) {
                        println("É necessário digitar data de nascimento")
                        continue
                    } else if (!dataValida.matches(birthday)) {
                        println("Formato de data inválida")
                        continue
                    }
                } while (birthday.isBlank() || !dataValida.matches(birthday))


                println("Digite seu genero: [M]asculino [F]eminino")
                var entryGender: String
                do {
                    entryGender = readln().uppercase()
                    if (entryGender.isBlank()) {
                        println("É necessário digitar o genero")
                        continue
                    } else if (entryGender.any() {it.isDigit()}) {
                        println("Não pode conter números")
                        continue
                    }
                    if (entryGender == "F") {
                        gender = Gender.FEMININO
                        continue
                    } else if (entryGender == "M") {
                        gender = Gender.MASCULINO
                        continue
                    } else {
                        println("Opção inválida!")
                        continue
                    }
                } while (entryGender.isBlank() || entryGender.any() {it.isDigit()} || entryGender != "M" && entryGender != "F")

                println("Escreva seu Email: ")
                var email: String
                do {
                    email = readln().lowercase().replaceFirstChar { it.uppercase() }
                    if (email.isBlank()) {
                        println("É necessário digitar Email")
                        continue
                    } else if (!emailValido.matches(email)) {
                        println("Formato de email inválido")
                        continue
                    }
                } while (email.isBlank() || !emailValido.matches(email))


                println("Digite a senha: ")
                var password: String
                do {
                    password = readln()
                    if (password.isBlank()) {
                        println("É necessário digitar senha!")
                        continue
                    }
                } while (password.isBlank())


                user = User(username, birthday, email, gender, password, isOrganizer = false)

                println("===Usuário criado com sucesso===")
                println(user)
                cadastro = false
            }

            2 -> {
                println("Você escolheu a opção de cadastrar usuário organizador.")
                println("Você é uma empresa? [S]im [N]ão")
                var empresa: String
                do {
                    empresa = readln().uppercase()
                    if (empresa.isBlank()) {
                        println("É necessário digitar S ou N")
                        continue
                    } else if (empresa != "S" && empresa != "N") {
                        println("Opção inválida!")
                        continue
                    }
                } while (empresa.isBlank() || empresa != "S" && empresa != "N")

                println("Escreva seu nome: ")
                var username: String
                do {
                    username = readln().lowercase().replaceFirstChar { it.uppercase() }
                    if (username.isBlank()) {
                        println("É necessário digitar nome de usuário")
                        continue
                    } else if (username.any() {it.isDigit()}) {
                        println("Não pode conter números")
                        continue
                    }
                } while (username.isBlank() || username.any() {it.isDigit()})


                println("Digite sua data de nascimento YYYY-MM-DD: ")
                var birthday: String
                do {
                    birthday = readln()
                    if (birthday.isBlank()) {
                        println("É necessário digitar data de nascimento")
                        continue
                    } else if (!dataValida.matches(birthday)) {
                        println("Formato de data inválida")
                        continue
                    }
                } while (birthday.isBlank() || !dataValida.matches(birthday))


                println("Digite seu genero: [M]asculino [F]eminino")
                var entryGender: String
                do {
                    entryGender = readln().uppercase()
                    if (entryGender.isBlank()) {
                        println("É necessário digitar o genero")
                        continue
                    } else if (entryGender.any() {it.isDigit()}) {
                        println("Não pode conter números")
                        continue
                    }
                    if (entryGender == "F") {
                        gender = Gender.FEMININO
                        continue
                    } else if (entryGender == "M") {
                        gender = Gender.MASCULINO
                        continue
                    } else {
                        println("Opção inválida!")
                        continue
                    }
                } while (entryGender.isBlank() || entryGender.any() {it.isDigit()} || entryGender != "M" && entryGender != "F")

                println("Escreva seu Email: ")
                var email: String
                do {
                    email = readln().lowercase().replaceFirstChar { it.uppercase() }
                    if (email.isBlank()) {
                        println("É necessário digitar Email")
                        continue
                    } else if (!emailValido.matches(email)) {
                        println("Formato de email inválido")
                        continue
                    }
                } while (email.isBlank() || !emailValido.matches(email))


                println("Digite a senha: ")
                var password: String
                do {
                    password = readln()
                    if (password.isBlank()) {
                        println("É necessário digitar senha!")
                        continue
                    }
                } while (password.isBlank())

                if(empresa == "S") {
                    println("digite o CNPJ apenas os dígitos: ")
                    var cnpj: String
                    do {
                        cnpj = readln()
                        if (!cnpj.any {it.isDigit()}) {
                            println("CNPJ Contem número")
                            continue
                        } else if (cnpj.isBlank()) {
                            println("É necessário digitar CNPJ")
                            continue
                        } else if (cnpj.length != 14) {
                            println("Quantidade de caracteres errada")
                            continue
                        }
                    } while (cnpj.isBlank() || !cnpj.any {it.isDigit()} || cnpj.length != 14)

                    println("Digite a Razão Social: ")
                    var razaoSocial: String
                    do {
                        razaoSocial = readln().lowercase()
                        if (razaoSocial.isBlank()) {
                            println("É necessário digitar Razão Social")
                        }
                    } while (razaoSocial.isBlank())


                    println("Digite o Nome Fantasia: ")
                    var nomeFantasia: String
                    do {
                        nomeFantasia = readln().lowercase()
                        if (nomeFantasia.isBlank()) {
                            println("É necessário digitar Razão Social")
                            continue
                        }
                    } while (nomeFantasia.isBlank())

                    user = User(username, birthday, email, gender, password, isOrganizer = true, cnpj, razaoSocial, nomeFantasia)
                } else {
                    user = User(username, birthday, email, gender, password, isOrganizer = true)
                }

                println("===Organizador criado com sucesso===")
                println(user)
                cadastro = false
            }
            else -> {
                println("Opção errada, escolha apenas 1 ou 2")
            }
        }
    } while (cadastro)
    do {
        println("Olá ${user.username}, Bem vindo!")
        println("Escolha alguma opção para prosseguir: ")
        println("1 -> Ativar Usuário")
        println("0 -> Sair")
        val opcaoSis = readln().toInt()
        when(opcaoSis) {
            1 -> {
                println("Deseja confirmar a ativação do usuário: [S]im [N]ão")
                var confirmacao: String
                do {
                    confirmacao = readln().lowercase()
                    if (confirmacao.isBlank()) {
                        println("É necessário digitar S ou N")
                        continue
                    } else if(confirmacao == "s") {
                        user.isActive = true
                        println("Usuário ativado!")
                    } else if (confirmacao == "n") {
                        println("Pulando...")
                    } else {
                        println("Opção inválida!")
                        continue
                    }
                } while (confirmacao.isBlank() || confirmacao != "s" && confirmacao != "n")
            }
            else -> {
                sistema = false
            }
        }
    } while (sistema)
}