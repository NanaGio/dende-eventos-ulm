import kotlinx.datetime.*
import repository.Repositorio
// Import do Model.kt contendo as Data Classes
import model.*
import components.*

//Cadastrar Evento
fun cadastroEvento(usuario: User, lista: MutableList<Evento>): Evento {
    println("-> Cadastro de Evento:")

    // NOME
    var nomeEventoUserInput = readString("Nome do Evento: ", "Erro: Nome não pode ser vazio.", minLength = 2)

    // DESCRIÇÃO
    println("- Descrição: ")
    val descricaoEventoUserInput = readString("Descrição: ", "Erro: Descrição muito curta.", minLength = 5)

    // DATAS
    var dataInicio: LocalDateTime
    var dataFim: LocalDateTime
    do {
        dataInicio = readLocalDateTime("Data/Hora Início (YYYY-MM-DDT00:00:00): ", "Formato inválido!")
        dataFim = readLocalDateTime("Data/Hora Fim (YYYY-MM-DDT00:00:00): ", "Formato inválido!")

        val valida = (dataFim.toInstant(TimeZone.UTC) - dataInicio.toInstant(TimeZone.UTC)).inWholeMinutes >= 30
        if (!valida) println("ERRO: O evento deve durar no mínimo 30 minutos.")
    } while (!valida)

    // EVENTO PRINCIPAL
    val eventoPrincipalUserInput = readString("Evento Principal: ", "")

    // CAPACIDADE MÁXIMA
    var capacidadeMaximaUserInput = readInt("Capacidade Máxima: ", "Erro: Digite um número inteiro.")

    // LOCAL
    val localEventoUserInput = readString("Local do Evento: ", "Erro: Local não informado.", minLength = 3)

    // PREÇO UNITÁRIO
    var precoUnitarioUserInput = readDouble(
        message = "Determine o preço unitário:",
        errorMessage = "ERRO -> Formato Inválido. Digite, somente, valores separados por ponto (Ex.: 20.00)"
    ).toFloat()

    // TAXA DE ESTORNO
    var taxaEstornoUserInput = readDouble(
        message = "Determine a taxa de estorno (0.0 a 1.0): ",
        errorMessage = "ERRO -> Digite um valor entre 0.0 (0%) e 1.0 (100%).",
        minValue = 0.0,
        maxValue = 1.0
    ).toFloat()

    // TIPO DE EVENTO
    println(
        "Escolha o tipo de Evento:\n" +
                "SOCIAL[1], CORPORATIVO[2], ACADÊMICO[3], CULTURAL[4], RELIGIOSOS[5], ESPORTIVOS[6],\n" +
                "FEIRA[7], CONGRESSO[8], OFICINA[9], CURSO[10], TREINAMENTO[11], AULA[12],\n" +
                "SEMINÁRIO[13], PALESTRA[14], SHOW[15], FESTIVAL[16], EXPOSIÇÃO[17], RETIRO[18],\n" +
                "CULTO[19], CELEBRAÇÃO[20], CAMPEONATO[21], CORRIDA[22]"
    )
    val tipoEscolha = readInt("Opção de Tipo: ", "Opção inválida.", range = 1..22)
    val tipoFinal = TipoEvento.entries[tipoEscolha - 1]

    // MODALIDADE
    println("Escolha a modalidade do evento: Presencial[1], Híbrido[2], Remoto[3]:")
    var escolhaModalidade = readInt("Opção: ", "Opção inválida.", range = 1..3)
    val tipoModalidade = Modalidade.entries[escolhaModalidade - 1]

    // CRIANDO OBJETO E SALVANDO
    val novoEvento = Evento(
        nomeEvento = nomeEventoUserInput,
        descricao = descricaoEventoUserInput,
        dataInicio = dataInicio,
        dataFim = dataFim,
        eventoPrincipal = eventoPrincipalUserInput,
        capacidadeMaxima = capacidadeMaximaUserInput,
        localEvento = localEventoUserInput,
        precoUnitario = precoUnitarioUserInput,
        taxaEstorno = taxaEstornoUserInput,
        tipoEvento = tipoFinal,
        modalidade = tipoModalidade,
        estornaValor = false,
        isEventActive = false,
        organizadorEmail = usuario.email
    )

    return novoEvento.also{
        lista.add(it)
        println("Evento ${it.nomeEvento} cadastrado com sucesso!")
    }
}

//Alterar Evento
fun alterarEvento (usuario: User, lista: MutableList<Evento>) {
    println("-> Alterar Evento:")
    val meusEventos = lista.filter { it.organizadorEmail == usuario.email }

    if (meusEventos.isEmpty()) {
        println("Você não possui eventos cadastrados.")
        return
    }

    // Mostra a tabela para o usuário escolher pelo ID visual (posição na lista filtrada)
    printTable("ID  |  STATUS |     NOME DO EVENTO    |   DATA E HORÁRIO   | VAGAS      |  PREÇO", meusEventos)

    val index = readInt("Selecione o número do evento para alterar: ", "ID Inválido.", range = 1..meusEventos.size) - 1
    val evento = meusEventos[index]

    println("\nAlterando: ${evento.nomeEvento}")
    println("1-Nome, 2-Descrição, 3-Capacidade, 4-Preço, 5-Status, 0-Sair")

    when (readInt("Opção: ", "Opção inválida.", range = 0..5)) {
        1 -> evento.nomeEvento = readString("Novo Nome: ", "Insira um nome com uma maior quantidade de caracteres.", 2)
        2 -> evento.descricao = readString("Nova Descrição: ", "Insira uma descrição com uma maior quantidade de " +
                "caracteres.", 5)
        3 -> {
            var novaDataInicio: LocalDateTime
            var novaDataFim: LocalDateTime

            do {
                novaDataInicio = readLocalDateTime(
                    message = "NOVA -> DATA e HORÁRIO INICIO (YYYY-MM-DDT00:00:00): ",
                    errorMessage = "ERRO -> Formato Inválido. Use o padrão ISO (Ex: 2026-12-31T20:00:00)."
                )

                novaDataFim = readLocalDateTime(
                    message = "NOVA -> DATA e HORÁRIO FIM (YYYY-MM-DDT00:00:00): ",
                    errorMessage = "ERRO -> Formato Inválido."
                )

                val duracaoValida = (novaDataFim.toInstant(TimeZone.UTC) - novaDataInicio.toInstant(TimeZone.UTC)).inWholeMinutes >= 30

                if (!duracaoValida) {
                    println("ERRO: O evento deve durar no mínimo 30 minutos. Tente novamente.")
                }
            } while (!duracaoValida)

            evento.dataInicio = novaDataInicio
            evento.dataFim = novaDataFim
            println("Datas atualizadas com sucesso!")
        }
        4 -> {
            println("Digite o novo evento principal:")
            evento.eventoPrincipal = readln()
            println("Evento principal atualizado com sucesso!")
        }
        5 -> evento.capacidadeMaxima = readInt("Nova Capacidade: ", "Capacidade inválida. Insira, somente, números " +
                "maiores que 0.")
        6 -> {
            println("Digite o novo local:")
            evento.localEvento = readln()
            println("Local atualizado com sucesso!")
        }
        7 -> evento.precoUnitario = readDouble("Novo Preço: ", "Preço inválido. Insira, somente, números com casas de" +
                " vírgula. Ex.: 20.00").toFloat()
        8 -> {
            var novaTaxa = readDouble(
                message = "Digite a nova taxa de estorno (0.0 a 1.0) (Atual: ${evento.taxaEstorno}):",
                errorMessage = "A taxa deve estar entre 0.0 e 1.0",
                minValue = 0.0,
                maxValue = 1.0
            ).toFloat()
            evento.taxaEstorno = novaTaxa
            println("Taxa de estorno atualizada com sucesso!")
        }
        9 -> {
            println("--- ALTERAR TIPO DE EVENTO ---")
            // Mostramos as opções (pode ser o println original ou um loop)
            println(    "SOCIAL[1], CORPORATIVO[2], ACADÊMICO[3], CULTURAL[4], RELIGIOSOS[5], ESPORTIVOS[6],\n" +
                        "FEIRA[7], CONGRESSO[8], OFICINA[9], CURSO[10], TREINAMENTO[11], AULA[12],\n" +
                        "SEMINÁRIO[13], PALESTRA[14], SHOW[15], FESTIVAL[16], EXPOSIÇÃO[17], RETIRO[18],\n" +
                        "CULTO[19], CELEBRAÇÃO[20], CAMPEONATO[21], CORRIDA[22]"
            )

            val escolha = readInt(
                message = "Digite o novo tipo (1-22): ",
                errorMessage = "Erro: Digite um número entre 1 e 22.",
                range = 1..22
            )

            evento.tipoEvento = TipoEvento.entries[escolha - 1]
            println("Sucesso! O tipo agora é: ${evento.tipoEvento}")
        }
        10 -> {
            println("Escolha a nova modalidade: [1] Presencial, [2] Híbrido, [3] Remoto")

            val escolha = readInt("Opção: ", "Opção inválida! Escolha 1, 2 ou 3.", range = 1..3)

            // Mapeamento direto para o Enum Modalidade
            evento.modalidade = Modalidade.entries[escolha - 1]

            println("Modalidade atualizada para ${evento.modalidade}!")
        }
    }
}

//Status do evento
fun statusEvento(usuario: User, lista: MutableList<Evento>){
    println("-> Status do Evento:")
    val meusEventos = lista.filter { it.organizadorEmail == usuario.email }

    if (meusEventos.isEmpty()) {
        println("Você ainda não possui eventos cadastrados para gerenciar.")
        return
    }

    printTable("ID  | STATUS | NOME DO EVENTO     | DATA E HORA      | VAGAS       | PREÇO", meusEventos)

    val index = readInt(
        message = "Selecione o número (ID) do evento para alterar: ",
        errorMessage = "ID inválido! Escolha um número entre 1 e ${meusEventos.size}.",
        range = 1..meusEventos.size
    ) - 1

    val evento = meusEventos[index]

    println("\nEvento selecionado: ${evento.nomeEvento}")
    println("Status atual: ${if (evento.isEventActive) "ATIVO (Visível no Feed)" else "INATIVO (Oculto)"}")

    println("O que deseja fazer?\n1 -> Ativar/Lançar evento\n2 -> Desativar/Recolher evento\n0 -> Voltar")

    val acao = readInt("Escolha uma opção: ", "Opção inválida.", range = 0..2)

    when (acao) {
        1 -> {
            evento.isEventActive = true
            println("Sucesso! '${evento.nomeEvento}' agora está ATIVO e aparecerá no Feed.")
        }
        2 -> {
            evento.isEventActive = false
            println("Sucesso! '${evento.nomeEvento}' agora está INATIVO.")
        }
        0 -> println("Operação cancelada.")
    }
}