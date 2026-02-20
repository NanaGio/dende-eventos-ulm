package org.oat1
import kotlinx.datetime.*

sealed class User {
    var username: String = ""
    var email: String = ""
    var password: String = ""
    var birthday: LocalDate = LocalDate.parse("2000-01-01")
    var gender: String = ""
    var isActive: Boolean = true
}

class UsuarioComum : User()

class Organizador : User()
class OrganizadorEmpresa : User() {
    var cnpj: String = ""
    var nomeFantasia: String = ""
    var razaoSocial: String = ""
}

fun main() {
    // TESTE 1: Comum
    val user: User = UsuarioComum().apply {
        username = "joao";
        email = "joao@hotmail.com"
        birthday = LocalDate.parse("2000-01-01")
        gender = "Male"
        isActive = true
    }

    // TESTE 2: Organizador Empresa
    """val user: User = OrganizadorEmpresa().apply {
        username = "admin_tech"
        email = "contato@techevents.com"
        birthday = LocalDate.parse("1995-05-10")
        gender = "Não Informado"
        cnpj = "12.345.678/0001-00"     // Campo do OrganizadorEmpresa
        nomeFantasia = "Tech Events"    // Campo do OrganizadorEmpresa
    }"""

    var stayInMenu = true

    do {
        // Cálculo da idade
        val today = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
        val idadeExata = user.birthday.periodUntil(today)

        println("""
            ======= MENU PRINCIPAL =======
            1 - Ver meu perfil 
            2 - Desativar conta 
            0 - Sair
            Selecione uma opção: 
        """.trimIndent())

        val userChoice = readln().toIntOrNull()
        if (userChoice !in 0..2) {
            print("Opção inválida. Insira um número de 0 a 2.\n")
        } else {
            when (userChoice) {
                1 -> {
                    println("""
                    ======= MEU PERFIL =======
                    Nome: ${user.username}
                    E-mail: ${user.email}
                    Gênero: ${user.gender}
                """.trimIndent())
                    if (user is OrganizadorEmpresa) {
                        println("CNPJ: ${user.cnpj}")
                        println("Nome Fantasia: ${user.nomeFantasia}")
                    } else {
                        println("Idade detalhada: ${idadeExata.years} anos, ${idadeExata.months} meses e " +
                                "${idadeExata.days} dias")
                    }
                    println("Status: ${if (user.isActive) "Ativo" else "Inativo"}")
                    println("\n1 - Alterar informações \n2 - Voltar")
                    if (readln().toIntOrNull() !in 0..2) {
                        if (user is OrganizadorEmpresa) {
                            println("O que deseja alterar? (1 - Nome de Usuário, 2 - Senha, 3 - CPNJ, 4 - Nome Fantasia")
                            when (readln()) {
                                "1" -> {
                                    print("Insira o novo nome: ")
                                    user.username = readln()
                                }
                                "2" -> {
                                    print("Insira a nova senha: ")
                                    user.username = readln()
                                }
                                "3" -> {
                                    print("Insira o novo CPNJ: ")
                                    user.cnpj = readln()
                                }
                                "4" -> {
                                    print("Insira o novo Nome Fantasia: ")
                                    user.nomeFantasia = readln()
                                }
                            }
                        } else {
                            println("O que deseja alterar? (1 - Nome, 2 - Senha, 3 - Gênero, 4 - Data)")
                            when (readln()) {
                                "1" -> {
                                    print("Insira o novo nome: ")
                                    user.username = readln() // ATRIBUIÇÃO
                                }
                                "2" -> {
                                    print("Insira a nova senha: ")
                                    user.password = readln()
                                }
                                "3" -> {
                                    print("Insira o novo gênero: ")
                                    user.gender = readln()
                                }
                                "4" -> {
                                    print("Insira a nova data (AAAA-MM-DD): ")
                                    user.birthday = LocalDate.parse(readln())
                                }
                            }
                            println("Alteração realizada com sucesso!")
                        }

                    }
                }
                2 -> {
                    println("Confirmar desativação? (1 - Sim, 2 - Não)")
                    if (readln() == "1") {
                        user.isActive = false // Atualiza o status do usuário
                        println("Conta desativada. Para reativá-la, realize login na plataforma novamente.")
                    }
                }
                0 -> stayInMenu = false
            }
        }
    } while (stayInMenu)
}