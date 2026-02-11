package org.oat1
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

///////////------------EVENTO LÓGICA------------///////////
    public final data class Evento(
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
        var modalidade: Modalidade
    )

    public final enum class TipoEvento{//ps como colocar CULTURAL/ENTRETERIMENTO?
        SOCIAL, CORPORATIVO, ACADÊMICO,
        CULTURAL, RELIGIOSOS, ESPORTIVOS,
        FEIRA, CONGRESSO, OFICINA, CURSO,
        TREINAMENTO, AULA, SEMINÁRIO,
        PALESTRA, SHOW, FESTIVAL,
        EXPOSIÇÃO, RETIRO, CULTO,
        CELEBRAÇÃO, CAMPEONATO, CORRIDA
    }

    enum class Modalidade{
        PRESENCIAL, REMOTO, HÍBRIDO
    }



///////////------------EVENTO LÓGICA------------///////////

fun main() { // Escrever código aqui
    val listarEventos = mutableListOf<Evento>()
    var ativo = true

    while(ativo){
        println(" - - - Dendê Eventos - - -")
        println("Bem vindo a plataforma Dendê Eventos! Escolha a opção para prosseguir: ")
        /*PARTE DE USUARIOS 1 2 3->println()*/
        println("4 -> Eventos - CADASTRAR:")
        println("5 -> Eventos - ALTERAR:")
        println("6 -> Eventos - STATUS:")
        print("0 -> SAIR")
        print(" | digite ->:")
        val userChoice = readln()

        when(userChoice){
            /*"X" -> { PARTE DA LÓGICA DE USUARIO
                USER COMUM OU ORGANIZADOR
            }*/
            "4" -> {
                println("-> Cadastro de Evento:")
                val formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")
                //NOME ->
                println("- Nome do Evento: ")
                var nomeEventoUserInput = readln()
                //DESCRIÇÃO ->
                println("- Descrição: ")
                var descricaoEventoUserInput = readln()
                //DATAS ->
                println(" - Data Inicio e horário (dd/mm/aaaa 00:00), minimo de 30 mintuos: ")
                var dataInicioUserInput = readln()
                println(" - Data Fim e horário (dd/mm/aaaa 00:00), minimo de 30 mintuos:: ")
                var dataFimUserInput = readln()
                //EVENTO PRINCIPAL ->
                println("- Evento Principal:")
                var eventoPrincipalUserInput = readln()
                //CAPACIDADE MÁXIMA ->
                println("Capacidade máxima: ")
                var capacidadeMaximaUserInput = readln().toInt()
                //LOCAL ->
                println("Local do evento: ")
                var localEventoUserInput = readln()
                //PREÇO UNITÁRIO ->
                println("Determine o preço unitário:")
                var precoUnitarioUserInput = readln().toFloat()
                //TAXA EXTORNO ->
                println("Determine a taxa de extorno:")
                var taxaEstornoUserInput = readln().toFloat()
                //ESTORNAR VALOR->
                //Criar lógica de devolução

                //LÓGICA ENUMS
                print("Escolha o tipo de Evento, digite o número desejado, entre as opções:         " +
                        "SOCIAL[1], CORPORATIVO[2], ACADÊMICO[3],\n" +
                        "CULTURAL[4], RELIGIOSOS[5], ESPORTIVOS[6],\n" +
                        "FEIRA[7], CONGRESSO[8], OFICINA[9], CURSO[10],\n" +
                        "TREINAMENTO[11], AULA[12], SEMINÁRIO[13],\n" +
                        "PALESTRA[14], SHOW[15], FESTIVAL[16],\n" +
                        "EXPOSIÇÃO[17], RETIRO[18], CULTO[19],\n" +
                        "CELEBRAÇÃO[20], CAMPEONATO[21], CORRIDA[22] ")
                print(" | digite ->:")
                var tipoFinal: TipoEvento = TipoEvento.SOCIAL
                var tipoUserChoice = readln()
                when(tipoUserChoice){
                    "1" -> tipoFinal = TipoEvento.SOCIAL
                    "2" -> tipoFinal = TipoEvento.CORPORATIVO
                    "3" -> tipoFinal = TipoEvento.ACADÊMICO
                    "4" -> tipoFinal = TipoEvento.CULTURAL
                    "5" -> tipoFinal  = TipoEvento.RELIGIOSOS
                    "6" -> tipoFinal  = TipoEvento.ESPORTIVOS
                    "7" -> tipoFinal = TipoEvento.FEIRA
                    "8" -> tipoFinal = TipoEvento.CONGRESSO
                    "9" -> tipoFinal = TipoEvento.OFICINA
                    "10" -> tipoFinal = TipoEvento.CURSO
                    "11" -> tipoFinal = TipoEvento.TREINAMENTO
                    "12" -> tipoFinal = TipoEvento.AULA
                    "13" -> tipoFinal = TipoEvento.SEMINÁRIO
                    "14" -> tipoFinal = TipoEvento.PALESTRA
                    "15" -> tipoFinal = TipoEvento.SHOW
                    "16" -> tipoFinal = TipoEvento.FESTIVAL
                    "17" -> tipoFinal = TipoEvento.EXPOSIÇÃO
                    "18" -> tipoFinal = TipoEvento.RETIRO
                    "19" -> tipoFinal = TipoEvento.CULTO
                    "20" -> tipoFinal = TipoEvento.CELEBRAÇÃO
                    "21" -> tipoFinal = TipoEvento.CAMPEONATO
                    "22" -> tipoFinal = TipoEvento.CORRIDA
                }

                println("Escolha a modalidade do evento: Presencial[1], Híbrido[2], Remoto[3]:")
                var tipoModalidade : Modalidade = Modalidade.PRESENCIAL
                var tipoModalidadeUserChoice = readln()
                when(tipoModalidadeUserChoice){
                    "1" -> tipoModalidade = Modalidade.PRESENCIAL
                    "2" -> tipoModalidade = Modalidade.HÍBRIDO
                    "3" -> tipoModalidade = Modalidade.REMOTO
                }
                var statusDefault = false

                val dataInicioFormatado = LocalDateTime.parse(dataInicioUserInput, formatador)
                val dataFimFormatado = LocalDateTime.parse(dataFimUserInput,formatador)

                //CRIANDO OBJETO EVENTO
                if (dataInicioFormatado.isAfter(dataFimFormatado)){
                    println("ERRO confilto de datas, evento não pode terminar antes de começar.")
                } else {
                    val novoEvento = Evento(
                        nomeEvento = nomeEventoUserInput,
                        descricao = descricaoEventoUserInput,
                        dataInicio = dataInicioFormatado,
                        dataFim = dataFimFormatado,
                        eventoPrincipal = eventoPrincipalUserInput,
                        capacidadeMaxima = capacidadeMaximaUserInput,
                        localEvento = localEventoUserInput,
                        precoUnitario = precoUnitarioUserInput,
                        taxaEstorno = taxaEstornoUserInput,
                        tipoEvento = tipoFinal,
                        modalidade = tipoModalidade,
                        estornaValor = false,
                        isEventActive = statusDefault
                    )
                    listarEventos.add(novoEvento)
                    println("Evento ${novoEvento.nomeEvento} cadastrado com sucesso!")
                }

            }
            "5" -> {
                println("-> Alterar Evento:")
                listarEventos.sortWith(compareBy<Evento> { it.dataInicio }.thenBy { it.nomeEvento })
                if (listarEventos.isEmpty()) {
                    println("Usuário ainda não criou eventos.")
                } else {
                    for (i in listarEventos.indices) {
                        println("${i + 1} - ${listarEventos[i].nomeEvento}")
                    }
                    val selecionarEventoLogica = readln().toInt() - 1
                    val evento = listarEventos[selecionarEventoLogica]

                    println("1-Nome, 2-Descrição, 3-Data Início, 4-Capacidade...")
                    val userChoice = readln()
                    when(userChoice){
                        "1" -> {
                            println("Digite o novo nome (Atual: ${evento.nomeEvento}):")
                            evento.nomeEvento = readln()
                            println("Atualizado!")
                        }
                        "2" -> {
                            println("Digite a nova descrição:")
                            evento.descricao = readln()
                            println("Atualizado!")
                        }
                        "3" -> {
                            println("Digite a nova data de e horário (dd/mm/aaaa 00:00), minimo de 30 mintuos::")
                            val formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")
                            evento.dataInicio = LocalDateTime.parse(readln(), formatador)
                            println("Atualizado!")
                        }
                        "4" -> {
                            println("Digite a nova data de fim e horário (dd/mm/aaaa 00:00), minimo de 30 mintuos::")
                            val formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")
                            evento.dataFim = LocalDateTime.parse(readln(), formatador)
                            println("Atualizado!")
                        }
                        "5" -> {
                            println("Digite o novo evento principal:")
                            evento.eventoPrincipal = readln()
                            println("Atualizado!")
                        }
                        "6" -> {
                            println("Digite a nova capacidade máxima: ")
                            evento.capacidadeMaxima = readln().toInt()
                            println("Atualizado!")
                        }
                        "7" -> {
                            println("Digite o novo local: ")
                            evento.localEvento = readln()
                            println("Atualizado!")
                        }
                        "8" -> {
                            println("Digite o novo preco: ")
                            evento.precoUnitario = readln().toFloat()
                            println("Atualizado!")
                        }
                        "9" -> {
                            println("Digite a nova taxa de estorno: ")
                            evento.taxaEstorno = readln().toFloat()
                            println("Atualizado!")
                        }
                        "10" -> {
                            println("Digite o novo tipo de evento:")
                            val escolhaTipo = readln()
                            when(escolhaTipo){
                                "1" -> evento.tipoEvento = TipoEvento.SOCIAL
                                "2" -> evento.tipoEvento = TipoEvento.CORPORATIVO
                                "3" -> evento.tipoEvento = TipoEvento.ACADÊMICO
                                "4" -> evento.tipoEvento = TipoEvento.CULTURAL
                                "5" -> evento.tipoEvento  = TipoEvento.RELIGIOSOS
                                "6" -> evento.tipoEvento  = TipoEvento.ESPORTIVOS
                                "7" -> evento.tipoEvento = TipoEvento.FEIRA
                                "8" -> evento.tipoEvento = TipoEvento.CONGRESSO
                                "9" -> evento.tipoEvento = TipoEvento.OFICINA
                                "10" -> evento.tipoEvento = TipoEvento.CURSO
                                "11" -> evento.tipoEvento = TipoEvento.TREINAMENTO
                                "12" -> evento.tipoEvento = TipoEvento.AULA
                                "13" -> evento.tipoEvento = TipoEvento.SEMINÁRIO
                                "14" -> evento.tipoEvento = TipoEvento.PALESTRA
                                "15" -> evento.tipoEvento = TipoEvento.SHOW
                                "16" -> evento.tipoEvento = TipoEvento.FESTIVAL
                                "17" -> evento.tipoEvento = TipoEvento.EXPOSIÇÃO
                                "18" -> evento.tipoEvento = TipoEvento.RETIRO
                                "19" -> evento.tipoEvento = TipoEvento.CULTO
                                "20" -> evento.tipoEvento = TipoEvento.CELEBRAÇÃO
                                "21" -> evento.tipoEvento = TipoEvento.CAMPEONATO
                                "22" -> evento.tipoEvento = TipoEvento.CORRIDA
                                else -> println("Opção inválida. O tipo não foi alterado.")
                            }
                            println("Atualizado!")
                        }
                        "11" -> {
                            println("Digite a nova modalidade:")
                            evento.modalidade
                            val escolhaTipoModalidade = readln()
                            when(escolhaTipoModalidade){
                                "1" -> evento.modalidade = Modalidade.PRESENCIAL
                                "2" -> evento.modalidade = Modalidade.HÍBRIDO
                                "3" -> evento.modalidade = Modalidade.REMOTO
                                else -> println("Opção inválida.")
                            }
                            println("Atualizado!")
                        }
                    }
                }
            }
            "6" -> {
                println("-> Status do Evento: ")
                /*val eventosDoUsuario = listarEventos
                    .filter { it.organizador == "NomeDoUsuarioLogado" }
                    .sortedWith(compareBy<Evento> { it.dataInicio }.thenBy { it.nomeEvento }) */
                if (listarEventos.isEmpty()){
                    println("Usuario não tem eventos")
                } else {
                    for (i in listarEventos.indices) {
                        println("${i + 1} - ${listarEventos[i].nomeEvento}")
                    }
                    val selecionarEventoLogica = readln().toInt() - 1
                    val evento = listarEventos[selecionarEventoLogica]

                    println("Status atual: ${if (evento.isEventActive) "Ativo" else "Inativo"}")
                    println("Digite 1 para lançar evento, 2 para desativar:")
                    val choice = readln()
                    when(choice){
                        "1" -> evento.isEventActive = true
                        "2" -> evento.isEventActive = false
                    }
                    println("Atualizado!")
                }
            }
            "0" -> {
                println("SAIR ->")
                ativo = false
            }
            else -> {
                println("Comando inválido, favor, insira um comando da lista.")
            }
        }
    }


}

