import kotlinx.datetime.*

data class User(
    var username: String,
    var birthday: LocalDate = LocalDate.parse("2000-01-01"),
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

data class Ingresso(
    val id: Int,
    val nomeDoEvento: String,
    val emailDono: String,
    val dataDoEvento: LocalDateTime,
    var status: String,
    val valorPago: Float
)

public final data class Evento(
    var nomeEvento: String,
    var descricao: String,
    var dataInicio: LocalDateTime, //CORRIGIR
    var dataFim: LocalDateTime,//CORRIGIR
    var eventoPrincipal: String,
    var capacidadeMaxima: Int = 0,
    var localEvento: String,
    var precoUnitario: Float = 0.0F,
    var taxaEstorno: Float = 0.0F,
    var estornaValor: Boolean = false,
    var isEventActive: Boolean = false,
    var tipoEvento: TipoEvento,
    var modalidade: Modalidade,
    var organizadorEmail: String,
    var ingressosVendidos: Int = 0
)

public final enum class TipoEvento{
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

fun main() { // Escrever código aqui
    var cadastro = true
    var sistema = true
    val dataValida = Regex("""\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12]\d|3[01])""")
    val emailValido = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")
    lateinit var user: User
    lateinit var gender: Gender
    val listarEventos = mutableListOf<Evento>()/* trocar para VAR*/
    val data = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    val listaIngressos = mutableListOf<Ingresso>()


    do {
        println(" - - - Dendê Eventos - - -")
        println("Bem-vindo à plataforma Dendê Eventos! Escolha a opção para prosseguir: ")
        println("1 -> Usuário - CADASTRAR:")
        println("2 -> Organizador - CADASTRAR:")

        val opcaoIni = readln().toIntOrNull()

        if (opcaoIni == null) {
            println("Insira somente a opção 1 ou 2.")
            continue
        }

        when (opcaoIni) {
            1 -> {
                println("Você escolheu a opção de cadastrar usuário comum.")
                println("Escreva seu nome: ")
                var username: String
                do {
                    username = readln().lowercase().replaceFirstChar { it.uppercase() }
                    if (username.isBlank()) {
                        println("É necessário digitar nome de usuário.")
                        continue
                    } else if (username.any() {it.isDigit()}) {
                        println("O nome de usuário não pode conter números.")
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


                println("Digite seu gênero: [M]asculino [F]eminino")
                var entryGender: String
                do {
                    entryGender = readln().uppercase()
                    if (entryGender.isBlank()) {
                        println("É necessário digitar o gênero.")
                        continue
                    } else if (entryGender.any() {it.isDigit()}) {
                        println("Não pode conter números.")
                        continue
                    }
                    if (entryGender == "F") {
                        gender = Gender.FEMININO
                        continue
                    } else if (entryGender == "M") {
                        gender = Gender.MASCULINO
                        continue
                    } else {
                        println("Opção inválida. Tente novamente.")
                        continue
                    }
                } while (entryGender.isBlank() || entryGender.any() {it.isDigit()} || entryGender != "M" && entryGender != "F")

                println("Escreva seu Email: ")
                var email: String
                do {
                    email = readln().lowercase().replaceFirstChar { it.uppercase() }
                    if (email.isBlank()) {
                        println("É necessário digitar o e-mail.")
                        continue
                    } else if (!emailValido.matches(email)) {
                        println("Formato de e-mail inválido.")
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

                val formatedBirthday = LocalDate.parse(birthday)
                user = User(username, formatedBirthday, email, gender, password, isOrganizer = false)

                println("=== Usuário criado com sucesso ===")
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
                        println("É necessário digitar S ou N.")
                        continue
                    } else if (empresa != "S" && empresa != "N") {
                        println("Opção inválida. Tente novamente.")
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
                        println("O nome de usuário não pode conter números.")
                        continue
                    }
                } while (username.isBlank() || username.any() {it.isDigit()})


                println("Digite sua data de nascimento YYYY-MM-DD: ")
                var birthday: String
                do {
                    birthday = readln()
                    if (birthday.isBlank()) {
                        println("É necessário digitar data de nascimento.")
                        continue
                    } else if (!dataValida.matches(birthday)) {
                        println("Formato de data inválida. Tente novamente.")
                        continue
                    }
                } while (birthday.isBlank() || !dataValida.matches(birthday))


                println("Digite seu gênero: [M]asculino [F]eminino")
                var entryGender: String
                do {
                    entryGender = readln().uppercase()
                    if (entryGender.isBlank()) {
                        println("É necessário digitar o gênero.")
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
                        println("Opção inválida.")
                        continue
                    }
                } while (entryGender.isBlank() || entryGender.any() {it.isDigit()} || entryGender != "M" && entryGender != "F")

                println("Escreva seu e-mail: ")
                var email: String
                do {
                    email = readln().lowercase().replaceFirstChar { it.uppercase() }
                    if (email.isBlank()) {
                        println("É necessário digitar o E-mail.")
                        continue
                    } else if (!emailValido.matches(email)) {
                        println("Formato de email inválido.")
                        continue
                    }
                } while (email.isBlank() || !emailValido.matches(email))


                println("Digite a senha: ")
                var password: String
                do {
                    password = readln()
                    if (password.isBlank()) {
                        println("É necessário digitar a senha.")
                        continue
                    }
                } while (password.isBlank())

                if(empresa == "S") {
                    println("digite o CNPJ, apenas números: ")
                    var cnpj: String
                    do {
                        cnpj = readln()
                        if (!cnpj.any {it.isDigit()}) {
                            println("Digite somente números.")
                            continue
                        } else if (cnpj.isBlank()) {
                            println("É necessário digitar o CNPJ.")
                            continue
                        } else if (cnpj.length != 14) {
                            println("Insira pelo menos 14 caracteres.")
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
                    val formatedBirthday = LocalDate.parse(birthday)
                    user = User(username, formatedBirthday, email, gender, password, isOrganizer = true, cnpj, razaoSocial, nomeFantasia)
                } else {
                    val formatedBirthday = LocalDate.parse(birthday)
                    user = User(username, formatedBirthday, email, gender, password, isOrganizer = true)
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

        val today = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
        val idadeExata = user.birthday.periodUntil(today)


        println("Olá ${user.username}, Bem vindo!")
        println("Escolha alguma opção para prosseguir: ")
        println("1 -> Ver meu perfil ")
        println("2 -> Desativar conta ")
        println("3 -> Ativar conta")
        println("4 -> Ver o Feed")
        println("5 -> Comprar Ingresso")
        println("6 -> Cancelar Ingresso")
        println("7 -> Minha Carteira")
        if(user.isOrganizer) {
            println("8 -> Eventos - CADASTRAR:")
            println("9 -> Eventos - ALTERAR:")
            println("10 -> Eventos - STATUS:")
            println("11 -> Meus Eventos")
        }
        println("0 -> Sair")
        val opcaoSis = readln().toIntOrNull()
        if (opcaoSis == null) {
            println("Não é permitido nenhum formato além de número.")
            continue
        }
        when(opcaoSis) {
            1 -> {
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
                    println("Idade detalhada: ${idadeExata.years} anos, ${idadeExata.months} meses e " +
                            "${idadeExata.days} dias")
                }
                println("Status: ${if (user.isActive) "Ativo" else "Inativo"}")
                println("\n1 - Alterar informações \n2 - Voltar")
                val escolha = readln().toIntOrNull()

                if (escolha == 1) {

                    if (user.isOrganizer) {

                        println("O que deseja alterar? (1 - Nome de Usuário, 2 - Senha, 3 - CNPJ, 4 - Nome Fantasia)")
                        when (readln()) {

                            "1" -> {
                                print("Insira o novo nome: ")
                                var username: String
                                do {
                                    username = readln().lowercase()
                                        .replaceFirstChar { it.uppercase() }

                                    when {
                                        username.isBlank() ->
                                            println("É necessário digitar nome de usuário")

                                        username.any { it.isDigit() } ->
                                            println("Não pode conter números")
                                    }

                                } while (username.isBlank() || username.any { it.isDigit() })

                                user.username = username
                            }

                            "2" -> {
                                print("Insira a nova senha: ")
                                var password: String
                                do {
                                    password = readln()
                                    if (password.isBlank()) {
                                        println("É necessário digitar senha!")
                                    }
                                } while (password.isBlank())

                                user.password = password
                            }

                            "3" -> {
                                print("Insira o novo CNPJ (apenas números): ")
                                var cnpj: String
                                do {
                                    cnpj = readln()

                                    when {
                                        cnpj.isBlank() ->
                                            println("É necessário digitar CNPJ")

                                        !cnpj.all { it.isDigit() } ->
                                            println("CNPJ deve conter apenas números")

                                        cnpj.length != 14 ->
                                            println("CNPJ deve ter 14 dígitos")
                                    }

                                } while (cnpj.isBlank() || !cnpj.all { it.isDigit() } || cnpj.length != 14)

                                user.cnpj = cnpj
                            }

                            "4" -> {
                                print("Insira o novo Nome Fantasia: ")
                                var nomeFantasia: String
                                do {
                                    nomeFantasia = readln()
                                    if (nomeFantasia.isBlank()) {
                                        println("É necessário digitar Nome Fantasia")
                                    }
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
                                    username = readln().lowercase()
                                        .replaceFirstChar { it.uppercase() }

                                    when {
                                        username.isBlank() ->
                                            println("É necessário digitar nome")

                                        username.any { it.isDigit() } ->
                                            println("Não pode conter números")
                                    }

                                } while (username.isBlank() || username.any { it.isDigit() })

                                user.username = username
                            }

                            "2" -> {
                                print("Insira a nova senha: ")
                                var password: String
                                do {
                                    password = readln()
                                    if (password.isBlank()) {
                                        println("É necessário digitar senha!")
                                    }
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
                                        birthday.isBlank() ->
                                            println("É necessário digitar data")

                                        !dataValida.matches(birthday) ->
                                            println("Formato inválido (AAAA-MM-DD)")
                                    }

                                } while (birthday.isBlank() || !dataValida.matches(birthday))

                                user.birthday = LocalDate.parse(birthday)
                            }

                            else -> println("Opção inválida!")
                        }
                    }

                    println("Alteração realizada com sucesso!")

                } else if (escolha == 2) {
                    // voltar
                } else {
                    println("Opção inválida!")
                }
            }
            2 -> {
                println("Confirmar desativação? (1 - Sim, 2 - Não)")
                if (readln() == "1") {
                    user.isActive = false // Atualiza o status do usuário
                    println("Conta desativada. Para reativá-la, realize login na plataforma novamente.")
                    break
                }
            }
            3 -> {
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
            8 -> {
                println("-> Cadastro de Evento, clique enter:")
                //NOME ->
                var nomeEventoUserInput = readln()
                do {
                    println("- Nome do Evento: ")
                    nomeEventoUserInput = readln().trim()
                    if (nomeEventoUserInput.isBlank()){
                        println("ERRO no nome do Evento")
                    }
                } while (nomeEventoUserInput.isBlank())


                //DESCRIÇÃO ->
                println("- Descrição: ")
                var descricaoEventoUserInput = readln()

                //DATAS ->
                println(" - Data Inicio e horário, minimo de 30 minutos: ")//CORRIGIR e achar um jeito de prevenir erro

                var conversaoDataInicio: LocalDateTime? = null
                do {
                    try{
                        println("DATA e HORÁRIO INICIO - YYYY-MM-DDT00:00:00 :")
                        var dataInicioUserInput = readln().trim()
                        conversaoDataInicio = LocalDateTime.parse(dataInicioUserInput)
                    }catch (e: Exception){
                        println("ERRO -> Formato Inválido.")
                        conversaoDataInicio = null
                    }
                } while (conversaoDataInicio == null)

                var conversaoDataFim: LocalDateTime? = null
                do {
                    try{
                        println("DATA e HORÁRIO DE FIM - YYYY/MM/DDT00:00:00 :")
                        var dataFimUserInput = readln().trim()
                        conversaoDataFim = LocalDateTime.parse(dataFimUserInput)

                        val calculoHorario = conversaoDataFim.toInstant(TimeZone.UTC) - conversaoDataInicio.toInstant(TimeZone.UTC)
                        val diferencaHorario = calculoHorario.inWholeMinutes

                        if (diferencaHorario < 30){
                            println("ERRO: Evento deve ter no mínimo 30 minutos de duração.")
                            return
                        }
                    }catch (e: Exception){
                        println("ERRO -> Formato Inválido.")
                        conversaoDataFim = null
                    }
                }while (conversaoDataFim == null)



                //DATAS - Close

                //EVENTO PRINCIPAL ->
                println("- Evento Principal:")
                var eventoPrincipalUserInput = readln()

                //CAPACIDADE MÁXIMA ->
                var capacidadeMaximaUserInput : Int? = null
                do{
                    try{
                        println("Capacidade máxima: ")
                        capacidadeMaximaUserInput = readln().toInt()
                    }catch(e: NumberFormatException){
                        println("ERRO -> Formato Inválido.")
                        capacidadeMaximaUserInput = null
                    }
                }while (capacidadeMaximaUserInput == null)


                //LOCAL ->
                println("Local do evento: ")
                var localEventoUserInput = readln()

                //PREÇO UNITÁRIO ->
                var precoUnitarioUserInput: Float? = null
                do{
                    try{
                        println("Determine o preço unitário:")
                        precoUnitarioUserInput = readln().toFloat()
                    }catch(e: NumberFormatException){
                        println("ERRO -> Formato Inválido.")
                        precoUnitarioUserInput = null
                    }
                }while(precoUnitarioUserInput == null)

                //TAXA EXTORNO ->
                var taxaEstornoUserInput : Float? = null
                do{
                    try{
                        println("Determine a taxa de extorno:")
                        taxaEstornoUserInput = readln().toFloat()
                    }catch(e: NumberFormatException){
                        println("ERRO -> Formato Inválido.")
                        taxaEstornoUserInput = null
                    }
                }while(taxaEstornoUserInput == null)
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

                //CRIANDO OBJETO EVENTO
                var statusDefault = false

                //val juncaoDataHoraInicio = dataIncioUserInput + horarioInicio
                //val dataInicioFormatado = LocalDateTime.parse()//CORRIGIR
                //val dataFimFormatado = LocalDateTime.parse()//CORRIGIR

                val novoEvento = Evento(
                    nomeEvento = nomeEventoUserInput,
                    descricao = descricaoEventoUserInput,
                    dataInicio = conversaoDataInicio,//CORRIGIR
                    dataFim = conversaoDataFim,//CORRIGIR
                    eventoPrincipal = eventoPrincipalUserInput,
                    capacidadeMaxima = capacidadeMaximaUserInput,
                    localEvento = localEventoUserInput,
                    precoUnitario = precoUnitarioUserInput,
                    taxaEstorno = taxaEstornoUserInput,
                    tipoEvento = tipoFinal,
                    modalidade = tipoModalidade,
                    estornaValor = false,
                    isEventActive = statusDefault,
                    organizadorEmail = user.email
                )
                listarEventos.add(novoEvento)
                println("Evento ${novoEvento.nomeEvento} cadastrado com sucesso!")
            }
            9 -> {
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
                            println("Nome atualizado com sucesso!!")
                        }
                        "2" -> {
                            println("Digite a nova descrição:")
                            evento.descricao = readln()
                            println("Descrição atualizada com sucesso!")
                        }
                        "3" -> {
                            //DATAS ->
                            println(" - Data Inicio e horário, minimo de 30 minutos: ")//CORRIGIR e achar um jeito de
                            // prevenir erro

                            var novaConversaoDataInicio: LocalDateTime? = null
                            do {
                                try{
                                    println("NOVA -> DATA e HORÁRIO INICIO - YYYY-MM-DDT00:00:00 :")
                                    var novaDataInicioUserInput = readln().trim()
                                    novaConversaoDataInicio = LocalDateTime.parse(novaDataInicioUserInput)
                                }catch (e: Exception){
                                    println("ERRO -> Formato Inválido.")
                                    novaConversaoDataInicio = null
                                }
                            } while (novaConversaoDataInicio == null)

                            var novaConversaoDataFim: LocalDateTime? = null
                            do {
                                try{
                                    println("NOVA -> DATA e HORÁRIO FIM - YYYY/MM/DDT00:00:00 :")
                                    var novaDataFimUserInput = readln().trim()
                                    novaConversaoDataFim = LocalDateTime.parse(novaDataFimUserInput)

                                    val calculoHorario = novaConversaoDataFim.toInstant(TimeZone.UTC) - novaConversaoDataInicio.toInstant(TimeZone.UTC)
                                    val diferencaHorario = calculoHorario.inWholeMinutes

                                    if (diferencaHorario < 30){
                                        println("ERRO: Evento deve ter no mínimo 30 minutos de duração.")
                                        return
                                    }
                                }catch (e: Exception){
                                    println("ERRO -> Formato Inválido.")
                                    novaConversaoDataFim = null
                                }
                            }while (novaConversaoDataFim == null)

                            evento.dataInicio = novaConversaoDataInicio
                            evento.dataFim = novaConversaoDataFim
                            println("Data atualizada com sucesso!")
                        }
                        "4" -> {
                            println("Digite o novo evento principal:")
                            evento.eventoPrincipal = readln()
                            println("Evento principal atualizado com sucesso!")
                        }
                        "5" -> {
                            var novaCapacidade: Int? = null
                            do{
                                try{
                                    println("Digite a nova capacidade máxima")
                                    novaCapacidade = readln().toInt()
                                }catch(e: NumberFormatException){
                                    println("ERRO: Digite um número inteiro válido.")
                                    novaCapacidade = null
                                }

                            } while(novaCapacidade == null)
                            evento.capacidadeMaxima = readln().toInt()
                            println("Capacidade atualizada com sucesso!")
                        }
                        "6" -> {
                            println("Digite o novo local: ")
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
                                    novoPreco = null
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
                                    novaTaxa = null
                                }
                            } while (novaTaxa == null)
                            evento.taxaEstorno = novaTaxa
                            println("Taxa de estorno atualizada com sucesso!")
                        }
                        "9" -> {
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
                            println("Tipo de evento atualizado com sucesso!")
                        }
                        "10" -> {
                            println("Digite a nova modalidade:")
                            evento.modalidade
                            val escolhaTipoModalidade = readln()
                            when(escolhaTipoModalidade){
                                "1" -> evento.modalidade = Modalidade.PRESENCIAL
                                "2" -> evento.modalidade = Modalidade.HÍBRIDO
                                "3" -> evento.modalidade = Modalidade.REMOTO
                                else -> println("Opção inválida.")
                            }
                            println("Modalidade atualizada com sucesso!")
                        }
                    }
                }
            }
            10 -> {
                println("-> Status do Evento: ")
                /*val eventosDoUsuario = listarEventos //para quando tiver user
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
                    println("Evento atualizado com sucesso!")
                }
            }
            11 -> {
                println("\n--- EVENTOS CADASTRADOS ---")
                var constamEventos = false
                for (evento in listarEventos) {
                    if (evento.organizadorEmail == user.email) {
                        println("-> ${evento.nomeEvento} | Vagas sobrando: ${evento.capacidadeMaxima - evento.ingressosVendidos}")
                        constamEventos = true
                    }
                }
                if (!constamEventos) println("nenhum evento cadastrado")
            }
            4 -> {
                println("\n--- feed de eventos ---")
                val instanteCronologicoAtual = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
                val cadeiaElegivel = listarEventos.filter {
                    it.isEventActive && it.ingressosVendidos < it.capacidadeMaxima && it.dataFim > instanteCronologicoAtual
                }

                if (cadeiaElegivel.isEmpty()) {
                    println("Nennhum evento ocorrendo agora")
                } else {
                    val listagemOrdenada = cadeiaElegivel.sortedWith(compareBy({ it.dataInicio }, { it.nomeEvento }))
                    for (item in listagemOrdenada) {
                        println("-> ${item.nomeEvento} | Quando: ${item.dataInicio} | Preço: R$ ${item.precoUnitario}")
                    }
                }
            }
            5 -> {
                print("\n qual o nome do evento? ")
                val termoBuscado = readln().trim()
                var eventoMapeado = false

                for (evento in listarEventos) {
                    if (evento.nomeEvento.equals(termoBuscado, ignoreCase = true) && evento.isEventActive && evento.ingressosVendidos < evento.capacidadeMaxima) {
                        eventoMapeado = true

                        if (evento.nomeEvento != null) {
                            println("esse evento faz parte do '${evento.nomeEvento}'.")
                        }

                        val novaAquisicao = Ingresso(
                            id = listaIngressos.size + 1,
                            nomeDoEvento = evento.nomeEvento,
                            emailDono = user.email,
                            dataDoEvento = evento.dataInicio,
                            status = "ATIVO",
                            valorPago = evento.precoUnitario
                        )
                        listaIngressos.add(novaAquisicao)
                        evento.ingressosVendidos++
                        println("compra efetuada, seu número de ingresso é:  ${novaAquisicao.id}.")
                    }
                }
                if (!eventoMapeado) println("evento não encontrado")
            }
            6 -> {
                print("\n número do ingresso a ser cancelado ")
                val codigoFornecido = readln().trim().toIntOrNull()

                if (codigoFornecido == null) {
                    println("digite apenas numeros ")
                } else {
                    var registroAtivo = false
                    for (bilhete in listaIngressos) {
                        if (bilhete.id == codigoFornecido && bilhete.emailDono == user.email && bilhete.status == "ATIVO") {
                            registroAtivo = true

                            for (eventoRelacionado in listarEventos) {
                                if (eventoRelacionado.nomeEvento == bilhete.nomeDoEvento) {
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
            7 -> {
                println("\n---  INGRESSOS (CARTEIRA) ---")
                var carteiraOcupada = false
                val ordenacaoHistorica = listaIngressos.sortedWith(compareBy({ it.status != "ATIVO" }, { it.dataDoEvento }))

                for (bilhete in ordenacaoHistorica) {
                    if (bilhete.emailDono == user.email) {
                        carteiraOcupada = true
                        println("[${bilhete.status}] ID ${bilhete.id} | Evento: ${bilhete.nomeDoEvento} | Dia: ${bilhete.dataDoEvento}")
                    }
                }
                if (!carteiraOcupada) println("carteira vazia" )
            }
            else -> {
                sistema = false
            }
        }
    } while (sistema)
}