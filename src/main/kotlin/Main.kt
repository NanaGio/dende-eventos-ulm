data class User(
    var username: String,
    var birthday: String,
    val email: String,
    var gender: String,
    var password: String,
    var isOrganizer: Boolean,
    var cnpj: String = "",
    var razaoSocial: String = "",
    var nomeFantasia: String = "",
    var isActive: Boolean = true,
)

fun main() { // Escrever código aqui
    var cadastro = true
    var sistema = true
    val dataValida = Regex("""\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12]\d|3[01])""")
    lateinit var user: User


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


                println("Digite seu genero: ")
                var gender: String
                do {
                    gender = readln().lowercase().replaceFirstChar { it.uppercase() }
                    if (gender.isBlank()) {
                        println("É necessário digitar nome de usuário")
                        continue
                    } else if (gender.any() {it.isDigit()}) {
                        println("Não pode conter números")
                        continue
                    }
                } while (gender.isBlank() || gender.any() {it.isDigit()})

                println("Escreva seu Email: ")
                var email: String
                do {
                    email = readln().lowercase().replaceFirstChar { it.uppercase() }
                    if (email.isBlank()) {
                        println("É necessário digitar nome de usuário")
                        continue
                    } else if (email.any() {it.isDigit()}) {
                        println("Não pode conter números")
                        continue
                    }
                } while (email.isBlank() || email.any() {it.isDigit()})


                println("Digite a senha: ")
                val password = readln()

                user = User(username, birthday, email, gender, password, isOrganizer = false)

                println("===Usuário criado com sucesso===")
                println(user)
                cadastro = false
            }

            2 -> {
                println("Você escolheu a opção de cadastrar usuário organizador.")
                println("Você é uma empresa? ")
                val empresa = readln().lowercase()

                println("Escreva seu nome: ")
                val username = readln().lowercase().replaceFirstChar { it.uppercase() }

                println("Digite sua data de nascimento YYYY-MM-DD: ")
                val birthday = readln()

                println("Digite seu genero: ")
                val gender = readln().lowercase()

                println("Escreva seu Email: ")
                val email = readln().lowercase()

                println("Digite a senha: ")
                val password = readln()

                if(empresa == "S") {
                    println("digite o CNPJ apenas os dígitos: ")
                    val cnpj = readln()

                    println("Digite a Razão Social: ")
                    val razaoSocial = readln().lowercase()

                    println("Digite o Nome Fantasia: ")
                    val nomeFantasia = readln().lowercase()

                    user = User(username, birthday, email, gender, password, isOrganizer = true, cnpj, razaoSocial, nomeFantasia)
                } else {
                    user = User(username, birthday, email, gender, password, isOrganizer = true)
                }

                println("===Usuário criado com sucesso===")
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
                println("Deseja confirmar a ativação do usuário: [s]im [n]ão")
                val confirmacao = readln().lowercase()
                if(confirmacao == "s") {
                    user.isActive = true
                }
            }

            else -> {
                sistema = false
            }
        }
    } while (sistema)
}