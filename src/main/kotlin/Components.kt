//Ler números inteiros e checar se o que a pessoa digitou é correto
fun readInt(
    // recado que aparece na tela pedindo pra pessoa digitar o número
    message: String,
    // se o user digitar algo errado, retorna erro
    errorMessage: String,
    // o limite de números aceitos, se nao for informado, vai de zero ao maximo possivel
    range: IntRange = 0..Int.MAX_VALUE
    // devolve um int
): Int {

    // guarda a digitação, pode ser NULL se for algo invalido
    var input: Int?
    // controla se a digitação atende ou nao as regras
    var valido: Boolean

    do {
        //exibe a mensagem na mesma linha, esperando digitação
        print(message)
        //tenta converter para numero, guardando NULL em caso de erro
        input = readlnOrNull()?.toIntOrNull()

        // when pra substituir IFs, avaliando de forma mais direta se a entrada eh valida ou não
        valido = when {
            // se não tiver vazio e o número estiver dentro do limite,  a entrada é valida
            input != null && input in range -> true
            // se não, aviso de erro, repetindo
            else -> { println(errorMessage)
                false
            }
        }

        // repete ate a var deixar de ser falsa
    } while (!valido)
// retorna o valor, se for nulo por algum motivo inexplicavel, retorna zero
    return input ?: 0
}

// Le decimais e verifica se a entrada atende os limites
// usa a mesma logica da anterior com mudanças para double,
fun readDouble(
    message: String,
    errorMessage: String,
    minValue: Double = 0.0,
    maxValue: Double = Double.MAX_VALUE

): Double {

    var input: Double?
    var valido: Boolean

    do {
        print(message)
        // pega o que a pessoa escreveu e tenta transformar em double, se falhar, fica vazio
        input = readlnOrNull()?.toDoubleOrNull()

        valido = when {
            // chega se o valor digitado ta dentro dos limites
            input != null && input >= minValue && input <= maxValue -> true
            else -> {
                println(errorMessage)
                false
            }
        }
    } while (!valido)

    // devolve o número. O ?: 0.0 é pra garantir que não vai quebrar o app se tiver vazio, setando NULL como 0.00
    return input ?: 0.0
}

// vai ler textos e checar se a pessoa digitou o mínimo de letras
fun readString(
    //solicita entrada de texto
    message: String,
    //retorna erro se não seguir os padrões de tamanho
    errorMessage: String,
 //mínimo de letras necessárias
    minLength: Int = 0
    // no fim, devolve um valor STRING
): String {

    var input: String?
    var valido: Boolean

    do {
        print(message)
        // apenas lê a linha como texto, podendo ser nulo se a entrada falhar
        input = readlnOrNull()

        valido = when {
            // confere se o texto existe e se tem letras suficientes
            input != null && input.length >= minLength -> true
            else -> {
                println(errorMessage)
                false
            }
        }
    } while (!valido)

    // retorna o texto digitado e garante o retorno de uma string vazia no lugar de null
    return input ?: ""
}


// imprime os elementos de uma coleção em formato de tabela
fun printTable(
    //String representando o cabeçalho
    header: String,
    // coleção de objetos que serão usados e impressos na tabela.
    items: List<Any>)
//retorno UNIT implicito
    {

    // gera um separador visual com base co cumprimento do cabeçalho, garantindo um minimo de 30 char
    val separator = "-".repeat(header.length.coerceAtLeast(30))

    println(separator)
    //printa o cabeçalho da tabela
    println(header)
    println(separator)

    when {
        // se não tiver nada na lista, um aviso é emitido
        items.isEmpty() -> println("Nenhum registro encontrado.")
        // se tiver dados na lista, passa por cada um deles e imprime
        else -> items.forEach { println(it.toString()) }
    }

    // mostra mais um separador para fechar a tabela
    println(separator)
}