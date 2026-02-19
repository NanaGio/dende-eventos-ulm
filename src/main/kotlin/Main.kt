fun main() { // Escrever código aqui
    var ativo = true

    data class User(var username: String, var birthday: String, var gender: String, val email: String, var password: String, var isActive: Boolean = true)

    data class Organizer(
        var username: String,
        var birthday: String,
        val email: String,
        var gender: String,
        var password: String,
        var cnpj: String = "",
        var razaoSocial: String = "",
        var nomeFantasia: String = "",
        var isActive: Boolean = true,
    )

    do {
        println(" - - - Dendê Eventos - - -")
        println("Bem vindo a plataforma Dendê Eventos! Escolha a opção para prosseguir: ")
        println("1 -> Usuário - CADASTRAR:")
        println("2 -> Organizador - CADASTRAR:")

        val opcaoIni = readln().toInt()

        when (opcaoIni) {
            1 -> {
                println("Você escolheu a opção de cadastrar usuário.")
                println("Escreva seu nome: ")
                val username = readln().lowercase().replaceFirstChar { it.uppercase() }

                println("Digite sua data de nascimento DD/MM/YYYY: ")
                val birthday = readln()

                println("Digite seu genero: ")
                val gender = readln().lowercase()

                println("Escreva seu Email: ")
                val email = readln().lowercase()

                println("Digite a senha: ")
                val password = readln()

                val user = User(username, birthday, gender, email, password)

                println("===Usuário criado com sucesso===")
                println(user)
            }

            2 -> {
                println("Você escolheu a opção de cadastrar usuário organizador.")
                println("Você é uma empresa? ")
                val empresa = readln().lowercase()

                println("Escreva seu nome: ")
                val username = readln().lowercase().replaceFirstChar { it.uppercase() }

                println("Digite sua data de nascimento DD/MM/YYYY: ")
                val birthday = readln()

                println("Digite seu genero: ")
                val gender = readln().lowercase()

                println("Escreva seu Email: ")
                val email = readln().lowercase()

                println("Digite a senha: ")
                val password = readln()

                if(empresa == "S") {
                    println("digite o CNPJ apenas os dígitos: ")
                    var cnpj = readln()

                    println("Digite a Razão Social: ")
                    val razaoSocial = readln().lowercase()

                    println("Digite o Nome Fantasia: ")
                    val nomeFantasia = readln().lowercase()

                    val user = Organizer(username, birthday, email, gender, password, cnpj, razaoSocial, nomeFantasia)
                } else {
                    val user = Organizer(username, birthday, email, gender, password)
                }



                println("===Usuário criado com sucesso===")
                println(user)
            }

            else -> {
                ativo = false
            }
        }

    } while (ativo)
}