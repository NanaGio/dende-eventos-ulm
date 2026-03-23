import kotlinx.datetime.*
val listarEventos = mutableListOf<Evento>()

//Cadastrar Evento
fun cadastroEvento (): Evento {
    println("-> Cadastro de Evento:")

    // NOME
    var nomeEventoUserInput: String
    do {
        println("- Nome do Evento: ")
        nomeEventoUserInput = readln().trim()
        if (nomeEventoUserInput.isBlank()) println("ERRO no nome do Evento.")
    } while (nomeEventoUserInput.isBlank())

    // DESCRIÇÃO
    println("- Descrição: ")
    val descricaoEventoUserInput = readln()

    // DATA INÍCIO
    var conversaoDataInicio: LocalDateTime? = null
    do {
        try {
            println("DATA e HORÁRIO INICIO - YYYY-MM-DDT00:00:00 :")
            conversaoDataInicio = LocalDateTime.parse(readln().trim())
        } catch (e: Exception) {
            println("ERRO -> Formato Inválido.")
            conversaoDataInicio = null
        }
    } while (conversaoDataInicio == null)

    // DATA FIM
    var conversaoDataFim: LocalDateTime? = null
    do {
        try {
            println("DATA e HORÁRIO DE FIM - YYYY-MM-DDT00:00:00 :")
            conversaoDataFim = LocalDateTime.parse(readln().trim())

            val diferencaMinutos = (conversaoDataFim.toInstant(TimeZone.UTC) - conversaoDataInicio.toInstant(TimeZone.UTC)).inWholeMinutes
            if (diferencaMinutos < 30) {
                println("ERRO: Evento deve ter no mínimo 30 minutos de duração.")
                conversaoDataFim = null
            }
        } catch (e: Exception) {
            println("ERRO -> Formato Inválido.")
            conversaoDataFim = null
        }
    } while (conversaoDataFim == null)

    // EVENTO PRINCIPAL
    println("- Evento Principal:")
    val eventoPrincipalUserInput = readln()

    // CAPACIDADE MÁXIMA
    var capacidadeMaximaUserInput: Int? = null
    do {
        try {
            println("Capacidade máxima: ")
            capacidadeMaximaUserInput = readln().toInt()
        } catch (e: NumberFormatException) {
            println("ERRO -> Formato Inválido.")
        }
    } while (capacidadeMaximaUserInput == null)

    // LOCAL
    println("Local do evento: ")
    val localEventoUserInput = readln()

    // PREÇO UNITÁRIO
    var precoUnitarioUserInput: Float? = null
    do {
        try {
            println("Determine o preço unitário:")
            precoUnitarioUserInput = readln().toFloat()
        } catch (e: NumberFormatException) {
            println("ERRO -> Formato Inválido.")
        }
    } while (precoUnitarioUserInput == null)

    // TAXA DE ESTORNO
    var taxaEstornoUserInput: Float? = null
    do {
        try {
            println("Determine a taxa de estorno:")
            taxaEstornoUserInput = readln().toFloat()
        } catch (e: NumberFormatException) {
            println("ERRO -> Formato Inválido.")
        }
    } while (taxaEstornoUserInput == null)

    // TIPO DE EVENTO
    println(
        "Escolha o tipo de Evento:\n" +
                "SOCIAL[1], CORPORATIVO[2], ACADÊMICO[3], CULTURAL[4], RELIGIOSOS[5], ESPORTIVOS[6],\n" +
                "FEIRA[7], CONGRESSO[8], OFICINA[9], CURSO[10], TREINAMENTO[11], AULA[12],\n" +
                "SEMINÁRIO[13], PALESTRA[14], SHOW[15], FESTIVAL[16], EXPOSIÇÃO[17], RETIRO[18],\n" +
                "CULTO[19], CELEBRAÇÃO[20], CAMPEONATO[21], CORRIDA[22]"
    )
    print("Digite -> ")
    var tipoFinal: TipoEvento = TipoEvento.SOCIAL
    when (readln()) {
        "1"  -> tipoFinal = TipoEvento.SOCIAL
        "2"  -> tipoFinal = TipoEvento.CORPORATIVO
        "3"  -> tipoFinal = TipoEvento.ACADÊMICO
        "4"  -> tipoFinal = TipoEvento.CULTURAL
        "5"  -> tipoFinal = TipoEvento.RELIGIOSOS
        "6"  -> tipoFinal = TipoEvento.ESPORTIVOS
        "7"  -> tipoFinal = TipoEvento.FEIRA
        "8"  -> tipoFinal = TipoEvento.CONGRESSO
        "9"  -> tipoFinal = TipoEvento.OFICINA
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
        else -> println("Opção inválida. Usando SOCIAL como padrão.")
    }

    // MODALIDADE
    println("Escolha a modalidade do evento: Presencial[1], Híbrido[2], Remoto[3]:")
    var tipoModalidade: Modalidade = Modalidade.PRESENCIAL
    when (readln()) {
        "1" -> tipoModalidade = Modalidade.PRESENCIAL
        "2" -> tipoModalidade = Modalidade.HÍBRIDO
        "3" -> tipoModalidade = Modalidade.REMOTO
        else -> println("Opção inválida. Usando PRESENCIAL como padrão.")
    }

    // CRIANDO OBJETO E SALVANDO
    val novoEvento = Evento(
        nomeEvento = nomeEventoUserInput,
        descricao = descricaoEventoUserInput,
        dataInicio = conversaoDataInicio,
        dataFim = conversaoDataFim,
        eventoPrincipal = eventoPrincipalUserInput,
        capacidadeMaxima = capacidadeMaximaUserInput,
        localEvento = localEventoUserInput,
        precoUnitario = precoUnitarioUserInput,
        taxaEstorno = taxaEstornoUserInput,
        tipoEvento = tipoFinal,
        modalidade = tipoModalidade,
        estornaValor = false,
        isEventActive = false,
        organizadorEmail = user.email
    )

    return novoEvento.also{
        listarEventos.add(it)
        println("Evento ${it.nomeEvento} cadastrado com sucesso!")
    }
}

//Alterar Evento
fun alterarEvento () {
    println("-> Alterar Evento:")
    listarEventos.sortWith(compareBy<Evento> { it.dataInicio }.thenBy { it.nomeEvento })

    if (listarEventos.isEmpty()) {
        println("Usuário ainda não criou eventos.")
        return
    }

    for (i in listarEventos.indices) {
        println("${i + 1} - ${listarEventos[i].nomeEvento}")
    }

    val selecionarEventoLogica = readln().toIntOrNull()?.minus(1)
    if (selecionarEventoLogica == null || selecionarEventoLogica !in listarEventos.indices) {
        println("Opção inválida.")
        return
    }

    val evento = listarEventos[selecionarEventoLogica]

    println(
        "O que deseja alterar?\n" +
                "1-Nome, 2-Descrição, 3-Datas, 4-Evento Principal,\n" +
                "5-Capacidade, 6-Local, 7-Preço, 8-Taxa Estorno,\n" +
                "9-Tipo, 10-Modalidade"
    )

    when (readln()) {
        "1" -> {
            println("Digite o novo nome (Atual: ${evento.nomeEvento}):")
            val novo = readln()
            if (novo.isNotBlank()) evento.nomeEvento = novo
            println("Nome atualizado com sucesso!")
        }
        "2" -> {
            println("Digite a nova descrição:")
            evento.descricao = readln()
            println("Descrição atualizada com sucesso!")
        }
        "3" -> {
            var novaDataInicio: LocalDateTime? = null
            do {
                try {
                    println("NOVA -> DATA e HORÁRIO INICIO - YYYY-MM-DDT00:00:00 :")
                    novaDataInicio = LocalDateTime.parse(readln().trim())
                } catch (e: Exception) {
                    println("ERRO -> Formato Inválido.")
                    novaDataInicio = null
                }
            } while (novaDataInicio == null)

            var novaDataFim: LocalDateTime? = null
            do {
                try {
                    println("NOVA -> DATA e HORÁRIO FIM - YYYY-MM-DDT00:00:00 :")
                    novaDataFim = LocalDateTime.parse(readln().trim())

                    val diferencaMinutos = (novaDataFim.toInstant(TimeZone.UTC) - novaDataInicio.toInstant(TimeZone.UTC)).inWholeMinutes
                    if (diferencaMinutos < 30) {
                        println("ERRO: Evento deve ter no mínimo 30 minutos de duração.")
                        novaDataFim = null
                    }
                } catch (e: Exception) {
                    println("ERRO -> Formato Inválido.")
                    novaDataFim = null
                }
            } while (novaDataFim == null)

            evento.dataInicio = novaDataInicio
            evento.dataFim = novaDataFim
            println("Data atualizada com sucesso!")
        }
        "4" -> {
            println("Digite o novo evento principal:")
            evento.eventoPrincipal = readln()
            println("Evento principal atualizado com sucesso!")
        }
        "5" -> {
            var novaCapacidade: Int? = null
            do {
                try {
                    println("Digite a nova capacidade máxima:")
                    novaCapacidade = readln().toInt()
                } catch (e: NumberFormatException) {
                    println("ERRO: Digite um número inteiro válido.")
                }
            } while (novaCapacidade == null)
            evento.capacidadeMaxima = novaCapacidade
            println("Capacidade atualizada com sucesso!")
        }
        "6" -> {
            println("Digite o novo local:")
            evento.localEvento = readln()
            println("Local atualizado com sucesso!")
        }
        "7" -> {
            var novoPreco: Float? = null
            do {
                try {
                    println("Digite o novo preço unitário:")
                    novoPreco = readln().toFloat()
                } catch (e: NumberFormatException) {
                    println("ERRO: Digite um número válido (ex: 10.50).")
                }
            } while (novoPreco == null)
            evento.precoUnitario = novoPreco
            println("Preço atualizado com sucesso!")
        }
        "8" -> {
            var novaTaxa: Float? = null
            do {
                try {
                    println("Digite a nova taxa de estorno (0.0 a 1.0) (Atual: ${evento.taxaEstorno}):")
                    novaTaxa = readln().toFloat()
                } catch (e: NumberFormatException) {
                    println("ERRO: Digite um número válido (ex: 0.5).")
                }
            } while (novaTaxa == null)
            evento.taxaEstorno = novaTaxa
            println("Taxa de estorno atualizada com sucesso!")
        }
        "9" -> {
            println("Digite o novo tipo de evento:")
            when (readln()) {
                "1"  -> evento.tipoEvento = TipoEvento.SOCIAL
                "2"  -> evento.tipoEvento = TipoEvento.CORPORATIVO
                "3"  -> evento.tipoEvento = TipoEvento.ACADÊMICO
                "4"  -> evento.tipoEvento = TipoEvento.CULTURAL
                "5"  -> evento.tipoEvento = TipoEvento.RELIGIOSOS
                "6"  -> evento.tipoEvento = TipoEvento.ESPORTIVOS
                "7"  -> evento.tipoEvento = TipoEvento.FEIRA
                "8"  -> evento.tipoEvento = TipoEvento.CONGRESSO
                "9"  -> evento.tipoEvento = TipoEvento.OFICINA
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
            println("Tipo de evento atualizado com sucesso!")
        }
        "10" -> {
            println("Escolha a nova modalidade: Presencial[1], Híbrido[2], Remoto[3]:")
            when (readln()) {
                "1" -> evento.modalidade = Modalidade.PRESENCIAL
                "2" -> evento.modalidade = Modalidade.HÍBRIDO
                "3" -> evento.modalidade = Modalidade.REMOTO
                else -> println("Opção inválida.")
            }
            println("Modalidade atualizada com sucesso!")
        }
        else -> println("Opção inválida!")
    }
}

//Status do evento
fun statusEvento (){
    println("-> Status do Evento:")

    if (listarEventos.isEmpty()) {
        println("Usuário não tem eventos.")
        return
    }

    for (i in listarEventos.indices) {
        println("${i + 1} - ${listarEventos[i].nomeEvento}")
    }

    val selecionarEventoLogica = readln().toIntOrNull()?.minus(1)
    if (selecionarEventoLogica == null || selecionarEventoLogica !in listarEventos.indices) {
        println("Opção inválida.")
        return
    }

    val evento = listarEventos[selecionarEventoLogica]

    println("Status atual: ${if (evento.isEventActive) "Ativo" else "Inativo"}")
    println("Digite 1 para lançar evento, 2 para desativar:")
    when (readln()) {
        "1" -> evento.isEventActive = true
        "2" -> evento.isEventActive = false
        else -> println("Opção inválida.")
    }
    println("Evento atualizado com sucesso!")
}
