import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun main() {
    val formatadorData = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")

    // As coleções em memória
    val listaEventos = mutableListOf<Evento>()
    val listaIngressos = mutableListOf<Ingresso>()

    // usuario para teste
    val meuEmail = "guguinha.trainee@dende.com"

    println("===  DENDÊ EVENTOS ===")

    while (true) {
        println("\nE aí, o que tu quer fazer agora?")
        println("[10] Meus Eventos | [11] Ver o Feed | [12] Comprar Ingresso | [13] Cancelar Ingresso | [14] Minha Carteira | [0] Sair")
        print("O que deseja? ")

        val entradaUsuario = readln().trim()

        if (entradaUsuario == "0") {
            println(" log out realizado")
            break
        }

        // 10: listagem
        else if (entradaUsuario == "10") {
            println("\n--- EVENTOS CADASTRADOS ---")
            var constamEventos = false
            for (eventoAtual in listaEventos) {
                if (eventoAtual.organizadorEmail == meuEmail) {
                    println("-> ${eventoAtual.nome} | Vagas sobrando: ${eventoAtual.capacidade - eventoAtual.ingressosVendidos}")
                    constamEventos = true
                }
            }
            if (!constamEventos) println("nenhum evento cadastrado")
        }

        // 11:feed
        else if (entradaUsuario == "11") {
            println("\n--- feed de eventos ---")
            val instanteCronologicoAtual = LocalDateTime.now()

            val cadeiaElegivel = listaEventos.filter {
                it.ativo && it.ingressosVendidos < it.capacidade && it.dataFim.isAfter(instanteCronologicoAtual)
            }

            if (cadeiaElegivel.isEmpty()) {
                println("Nennhum evento ocorrendo agora")
            } else {
                val listagemOrdenada = cadeiaElegivel.sortedWith(compareBy({ it.dataInicio }, { it.nome }))
                for (item in listagemOrdenada) {
                    println("-> ${item.nome} | Quando: ${item.dataInicio.format(formatadorData)} | Preço: R$ ${item.preco}")
                }
            }
        }

        // 12:comprar ingresso
        else if (entradaUsuario == "12") {
            print("\n qual o nome do evento? ")
            val termoBuscado = readln().trim()
            var eventoMapeado = false

            for (eventoAtual in listaEventos) {
                if (eventoAtual.nome.equals(termoBuscado, ignoreCase = true) && eventoAtual.ativo && eventoAtual.ingressosVendidos < eventoAtual.capacidade) {
                    eventoMapeado = true

                    if (eventoAtual.nomeEventoPrincipal != null) {
                        println("esse evento faz parte do '${eventoAtual.nomeEventoPrincipal}'.")
                    }

                    val novaAquisicao = Ingresso(
                        id = listaIngressos.size + 1,
                        nomeDoEvento = eventoAtual.nome,
                        emailDono = meuEmail,
                        dataDoEvento = eventoAtual.dataInicio,
                        status = "ATIVO",
                        valorPago = eventoAtual.preco
                    )
                    listaIngressos.add(novaAquisicao)
                    eventoAtual.ingressosVendidos++
                    println("compra efetuada, seu número de ingresso é:  ${novaAquisicao.id}.")
                }
            }
            if (!eventoMapeado) println("evento não encontrado")
        }

        // 13: estorno/cancelamento
        else if (entradaUsuario == "13") {
            print("\n número do ingresso a ser cancelado ")
            val codigoFornecido = readln().trim().toIntOrNull()

            if (codigoFornecido == null) {
                println("digite apenas numeros ")
            } else {
                var registroAtivo = false
                for (bilhete in listaIngressos) {
                    if (bilhete.id == codigoFornecido && bilhete.emailDono == meuEmail && bilhete.status == "ATIVO") {
                        registroAtivo = true

                        for (eventoRelacionado in listaEventos) {
                            if (eventoRelacionado.nome == bilhete.nomeDoEvento) {
                                if (eventoRelacionado.estornaValor) {
                                    val valorRestituicao = bilhete.valorPago * (1.0 - eventoRelacionado.taxaEstorno)
                                    println("Tudo certo pra cancelar. O estorno vai ser de R$ $valorRestituicao, seguindo a regra do evento.")
                                } else {
                                    println("igresso cancelado. esse evento não realiza estorno")
                                }
                                eventoRelacionado.ingressosVendidos--
                            }
                        }
                        bilhete.status = "CANCELADO"
                    }
                }
                if (!registroAtivo) println("nenhum ingresso ativo com esse numero")
            }
        }

        // 14: carteira
        else if (entradaUsuario == "14") {
            println("\n---  INGRESSOS (CARTEIRA) ---")
            var carteiraOcupada = false
            val ordenacaoHistorica = listaIngressos.sortedWith(compareBy({ it.status != "ATIVO" }, { it.dataDoEvento }))

            for (bilhete in ordenacaoHistorica) {
                if (bilhete.emailDono == meuEmail) {
                    carteiraOcupada = true
                    println("[${bilhete.status}] ID ${bilhete.id} | Evento: ${bilhete.nomeDoEvento} | Dia: ${bilhete.dataDoEvento.format(formatadorData)}")
                }
            }
            if (!carteiraOcupada) println("carteira vazia" )
        }

        else {
            println("Opção inválida")
        }
    }
}