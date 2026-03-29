import kotlinx.datetime.*
// Import do Model.kt contendo as Data Classes
import model.*
// Import do Components.kt
import components.*
import repository.Repositorio

fun main() {
    var usuarioLogado: User? = null // Começa vazio
    var sistemaAtivo = true
    val data = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())

    do {
        println(" - - - Dendê Eventos - - -")
        println("Bem-vindo à plataforma Dendê Eventos! Escolha a opção para prosseguir: ")
        val opcaoIni = readInt(
            message = "1 -> Usuário - CADASTRAR:\n2 -> Organizador - CADASTRAR: ",
            errorMessage = "Erro! Digite apenas 1 ou 2.",
            range = 1..2
        )

        when (opcaoIni) {
            1 -> {
                val novo = cadastrarUsuarioComum()
                Repositorio.salvarUsuario(novo)
                usuarioLogado = novo
            }
            2 -> {
                val novo = cadastrarUsuarioOrganizador()
                Repositorio.salvarUsuario(novo)
                usuarioLogado = novo
            } else -> println("Opção inválida.")
        }

    } while (usuarioLogado == null)
    do {
        val today = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
        val idadeExata = usuarioLogado.birthday.periodUntil(today)

        println("Olá ${usuarioLogado.username}, Bem vindo!")
        println("Escolha alguma opção para prosseguir: ")
        println("1 -> Ver meu perfil ")
        println("2 -> Desativar conta ")
        println("3 -> Ativar conta")
        println("4 -> Ver o Feed")
        println("5 -> Comprar Ingresso")
        println("6 -> Cancelar Ingresso")
        println("7 -> Minha Carteira")
        if(usuarioLogado.isOrganizer) {
            println("8 -> Eventos - CADASTRAR:")
            println("9 -> Eventos - ALTERAR:")
            println("10 -> Eventos - STATUS:")
            println("11 -> Meus Eventos")
        }
        println("0 -> Sair")
        val opcaoSis = readInt(
            message = "Escolha alguma opção para prosseguir: ",
            errorMessage = "Opção inválida. Digite de 0 a 11.",
            range = 0..11
        )

        when(opcaoSis) {
            1 -> {
                visualizarPerfil(usuarioLogado!!)
                println("\n")
                val escolha = readInt(
                    message = "1 - Alterar informações \n2 - Voltar",
                    errorMessage = "Erro! Digite apenas 1 ou 2.",
                    range = 1..2
                )
                if (escolha == 1) {
                    alterarPerfil(usuarioLogado!!)
                } else {
                    // voltar
                }
            }
            2 -> {
                inativarUsuario(usuarioLogado!!)
            }
            3 -> {
                reativarUsuario(usuarioLogado!!)
            }
            4 -> {
                println("\n--- FEED DE EVENTOS ---")
                val agora = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())

                // Chama a lógica pronta do Repositorio
                val feed = Repositorio.listarEventosAtivosParaFeed(agora)

                printTable(
                    header = "ID  | STATUS |   NOME DO EVENTO   |   DATA E HORÁRIO  | VAGAS      | PREÇO",
                    items = feed
                )
            }
            5 -> {
                println("\n--- COMPRA DE INGRESSO ---")
                val agora = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
                val disponiveis = Repositorio.listarEventosAtivosParaFeed(agora)

                printTable("EVENTOS DISPONÍVEIS", disponiveis)
                val termo = readString(
                    message = "Digite o nome exato do evento que deseja comprar: ",
                    errorMessage = "O nome não pode estar em branco.",
                    minLength = 1
                )
                val evento = Repositorio.buscarEventoPorNome(termo)

                if (evento != null && evento.isEventActive && evento.ingressosVendidos < evento.capacidadeMaxima) {
                    val novoIngresso = Ingresso(
                        id = Repositorio.gerarNovoIdIngresso(),
                        nomeDoEvento = evento.nomeEvento,
                        emailDono = usuarioLogado!!.email,
                        dataDoEvento = evento.dataInicio,
                        status = "ATIVO",
                        valorPago = evento.precoUnitario
                    )
                    Repositorio.salvarIngresso(novoIngresso)
                    evento.ingressosVendidos++
                    println("Compra efetuada! ID: ${novoIngresso.id}")
                } else {
                    println("Evento não encontrado ou esgotado.")
                }
            }
            6 -> {
                println("\n--- CANCELAMENTO DE INGRESSO ---")

                // Melhoria: Mostra os ingressos para o usuário ver o ID
                val meusIngressos = Repositorio.listarIngressosPorUsuario(usuarioLogado!!.email)

                if (meusIngressos.isEmpty()) {
                    println("Você não possui ingressos para cancelar.")
                } else {
                    // Exibe a tabela formatada usando o toString que foi configurado no Model
                    printTable("ID     | EVENTO             | STATUS     | VALOR", meusIngressos)

                    val codigoFornecido = readInt(
                        message = "\nDigite o ID do ingresso que deseja cancelar (ou 0 para voltar): ",
                        errorMessage = "Erro: Digite um número de ID válido."
                    )

                    val bilhete = Repositorio.buscarIngressoPorId(codigoFornecido)

                    // Validações (Dono, Existência e Status)
                    if (bilhete != null && bilhete.emailDono == usuarioLogado!!.email && bilhete.status == "ATIVO") {

                        val eventoRelacionado = Repositorio.buscarEventoPorNome(bilhete.nomeDoEvento)

                        if (eventoRelacionado != null) {
                            if (eventoRelacionado.estornaValor) {
                                // Cálculo com conversão explícita para Float
                                val taxa = eventoRelacionado.taxaEstorno
                                val valorRestituicao = bilhete.valorPago * (1.0f - taxa)

                                println(">> Cancelamento confirmado!")
                                println(">> Estorno: R$ ${"%.2f".format(valorRestituicao)} (Taxa de ${taxa * 100}% retida).")
                            } else {
                                println(">> Ingresso cancelado. Este evento não possui política de reembolso.")
                            }

                            // Devolve a vaga para o evento
                            eventoRelacionado.ingressosVendidos--
                        }

                        // Atualiza o objeto
                        bilhete.status = "CANCELADO"
                        println("Sucesso: O ingresso #${bilhete.id} agora está INVÁLIDO.")

                    } else {
                        println("Erro: Ingresso não encontrado, já cancelado ou não pertence a você.")
                    }
                }
            }
            7 -> {
                println("\n--- MINHA CARTEIRA DE INGRESSOS ---")
                val meusIngressos = Repositorio.listarIngressosPorUsuario(usuarioLogado!!.email)

                printTable("ID     | EVENTO             | STATUS     | VALOR", meusIngressos)
            }
            8 -> {
                // Passa o usuário logado e a lista que fica dentro do Repositório
                cadastroEvento(usuarioLogado!!, Repositorio.eventos)
            }
            9 -> {
                // Passa o usuário (para validar dono) e a lista do repositório
                alterarEvento(usuarioLogado!!, Repositorio.eventos)
            }
            10 -> {
                statusEvento(usuarioLogado!!, Repositorio.eventos)
            }
            11 -> {
                println("\n--- MEUS EVENTOS CADASTRADOS ---")

                // Chama a função pronta do Repositório
                val meusEventos = Repositorio.listarEventosPorOrganizador(usuarioLogado!!.email)

                printTable("ID  | STATUS | NOME DO EVENTO     | DATA E HORA      | VAGAS      | PREÇO", meusEventos)
            }
            else -> {
                sistemaAtivo = false
            }
        }
    } while (sistemaAtivo)
}