package br.com.perfininfra.news.data

/** Nível de impacto usado para ordenar o briefing. */
enum class Impacto(val rotulo: String, val peso: Int) {
    ALTO("Alto impacto", 3),
    MEDIO("Impacto médio", 2),
    BAIXO("Impacto baixo", 1)
}

/** Direção esperada do efeito sobre as teses do portfólio. */
enum class Direcao(val rotulo: String) {
    POSITIVO("Positivo"),
    NEGATIVO("Negativo"),
    NEUTRO("Neutro")
}

data class Noticia(
    val id: String,
    val manchete: String,
    val chapeu: String,
    val fonte: String,
    val horario: String,
    val impacto: Impacto,
    val direcao: Direcao,
    val tags: List<String>,
    val resumo: String,
    val leituraPerfin: String,
    val ativosRelacionados: List<String>,
    val variacao: String? = null
)

data class Alerta(
    val id: String,
    val titulo: String,
    val fonte: String,
    val horario: String,
    val impacto: Impacto,
    val detalhe: String
)

data class Contato(
    val id: String,
    val nome: String,
    val cargo: String
) {
    val iniciais: String
        get() = nome.trim().split(" ").let { partes ->
            if (partes.size >= 2) "${partes.first().first()}${partes.last().first()}".uppercase()
            else partes.first().take(2).uppercase()
        }
}

/** Variação de card definida no briefing de design (EDITORIAL x COMPACTO). */
enum class EstiloCard(val rotulo: String, val descricao: String) {
    EDITORIAL("Editorial", "Manchete, resumo, tags e miniatura."),
    COMPACTO("Compacto", "Densidade máxima, barra de impacto.")
}
