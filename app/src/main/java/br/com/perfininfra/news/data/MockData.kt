package br.com.perfininfra.news.data

/**
 * DADOS SIMULADOS.
 * Manchetes, números, fontes e nomes são fictícios e existem apenas para
 * prototipagem, conforme a nota do briefing de design. Nenhuma informação
 * aqui deve ser usada como insumo de decisão de investimento.
 */
object MockData {

    const val USUARIO_DEMO = "rsarti"
    const val SENHA_DEMO = "rsarti"

    val noticias: List<Noticia> = listOf(
        Noticia(
            id = "n01",
            manchete = "Aneel aprova revisão tarifária extraordinária para distribuidoras do Sudeste",
            chapeu = "Energia · Regulatório",
            fonte = "Agência Setorial",
            horario = "06h12",
            impacto = Impacto.ALTO,
            direcao = Direcao.POSITIVO,
            tags = listOf("Energia", "Aneel", "Tarifa"),
            resumo = "A diretoria colegiada aprovou reajuste extraordinário médio de 7,4% para três " +
                "distribuidoras da região Sudeste, com vigência imediata. A decisão reconhece custos " +
                "não gerenciáveis acumulados desde o ciclo anterior e antecipa parte do reposicionamento " +
                "que era esperado apenas para a revisão ordinária.",
            leituraPerfin = "Reduz o descasamento de caixa das distribuidoras reguladas e melhora a " +
                "previsibilidade de EBITDA no curto prazo. O efeito de segunda ordem sobre geradoras " +
                "contratadas no ambiente regulado é limitado.",
            ativosRelacionados = listOf("Transmissão Sudeste", "Fundo Energia III"),
            variacao = "+7,4% tarifa média"
        ),
        Noticia(
            id = "n02",
            manchete = "Leilão de saneamento no Nordeste atrai seis consórcios e ágio de 32%",
            chapeu = "Saneamento · Concessões",
            fonte = "Diário Econômico",
            horario = "06h30",
            impacto = Impacto.ALTO,
            direcao = Direcao.POSITIVO,
            tags = listOf("Saneamento", "Leilão", "Concessão"),
            resumo = "O bloco regional foi arrematado com ágio de 32% sobre o valor mínimo, com " +
                "investimento previsto de R$ 4,1 bilhões em doze anos. Foi a maior disputa do setor " +
                "no ano em número de proponentes.",
            leituraPerfin = "O ágio elevado comprime a TIR de entrada em ativos comparáveis e sugere " +
                "reprecificação para cima dos ativos maduros já em carteira. Reforça a tese de que a " +
                "janela de compra a múltiplos deprimidos está se fechando.",
            ativosRelacionados = listOf("Saneamento NE", "Pipeline 2026"),
            variacao = "ágio de 32%"
        ),
        Noticia(
            id = "n03",
            manchete = "Copom sinaliza manutenção da Selic e retira trecho sobre cortes graduais",
            chapeu = "Macro · Política monetária",
            fonte = "Boletim Macro",
            horario = "06h45",
            impacto = Impacto.ALTO,
            direcao = Direcao.NEGATIVO,
            tags = listOf("Macro", "Selic", "Juros"),
            resumo = "O comunicado retirou a referência a cortes graduais e reforçou a dependência de " +
                "dados. O mercado passou a precificar o primeiro corte apenas no segundo semestre.",
            leituraPerfin = "Custo de capital segue pressionado nas estruturas alavancadas em CDI. " +
                "Revisar a sensibilidade de covenants nas SPEs com dívida indexada e antecipar conversas " +
                "de waiver onde a folga for inferior a 0,3x.",
            ativosRelacionados = listOf("SPE Rodovias", "Carteira alavancada"),
            variacao = "Selic estável"
        ),
        Noticia(
            id = "n04",
            manchete = "Transmissora conclui emissão de debêntures incentivadas de R$ 1,2 bilhão",
            chapeu = "Mercado de capitais",
            fonte = "Radar de Crédito",
            horario = "07h05",
            impacto = Impacto.MEDIO,
            direcao = Direcao.POSITIVO,
            tags = listOf("Debêntures", "Energia", "Funding"),
            resumo = "A emissão saiu com spread de IPCA+ 6,15%, abaixo da faixa indicativa inicial, e " +
                "demanda de três vezes o lote base.",
            leituraPerfin = "Confirma a reabertura da janela de funding longo para transmissão. " +
                "Referência útil para o repricing da dívida do ativo de transmissão em carteira.",
            ativosRelacionados = listOf("Transmissão Sudeste"),
            variacao = "IPCA+ 6,15%"
        ),
        Noticia(
            id = "n05",
            manchete = "Governo publica decreto que acelera licenciamento de projetos eólicos onshore",
            chapeu = "Energia · Renováveis",
            fonte = "Diário Oficial",
            horario = "07h20",
            impacto = Impacto.MEDIO,
            direcao = Direcao.POSITIVO,
            tags = listOf("Eólica", "Licenciamento", "Regulatório"),
            resumo = "O decreto cria rito simplificado para projetos abaixo de 300 MW em áreas já " +
                "antropizadas, com prazo máximo de análise de 180 dias.",
            leituraPerfin = "Encurta o ciclo de desenvolvimento e melhora o valor de opção do pipeline " +
                "eólico. Efeito relevante sobre projetos greenfield e neutro sobre os operacionais.",
            ativosRelacionados = listOf("Pipeline Eólico"),
            variacao = "prazo de 180 dias"
        ),
        Noticia(
            id = "n06",
            manchete = "Tráfego em rodovias pedagiadas cresce 4,8% em julho, acima da projeção",
            chapeu = "Transportes",
            fonte = "Painel de Infraestrutura",
            horario = "07h34",
            impacto = Impacto.MEDIO,
            direcao = Direcao.POSITIVO,
            tags = listOf("Rodovias", "Tráfego", "Operacional"),
            resumo = "O volume de veículos equivalentes subiu 4,8% na comparação anual, puxado por " +
                "veículos pesados no corredor agrícola.",
            leituraPerfin = "Acima do budget de 3,2% assumido no modelo. Se sustentado por mais dois " +
                "meses, sugere revisão para cima da receita da SPE Rodovias no ano.",
            ativosRelacionados = listOf("SPE Rodovias"),
            variacao = "+4,8% a/a"
        ),
        Noticia(
            id = "n07",
            manchete = "CVM abre consulta pública sobre divulgação de fundos de infraestrutura",
            chapeu = "Regulatório · CVM",
            fonte = "CVM",
            horario = "08h02",
            impacto = Impacto.MEDIO,
            direcao = Direcao.NEUTRO,
            tags = listOf("CVM", "Compliance", "Fundos"),
            resumo = "A minuta amplia as exigências de transparência sobre a metodologia de marcação " +
                "de ativos ilíquidos. Contribuições podem ser enviadas por 45 dias.",
            leituraPerfin = "Sem efeito econômico imediato, mas exige revisão do manual de marcação e " +
                "do material de reporte aos cotistas. Entra no radar regulatório com prazo.",
            ativosRelacionados = listOf("Fundo Energia III", "Compliance"),
            variacao = "consulta por 45 dias"
        ),
        Noticia(
            id = "n08",
            manchete = "Companhia de gás anuncia capex adicional de R$ 800 milhões em rede",
            chapeu = "Gás natural",
            fonte = "Agência Setorial",
            horario = "08h15",
            impacto = Impacto.MEDIO,
            direcao = Direcao.NEUTRO,
            tags = listOf("Gás", "Capex", "Expansão"),
            resumo = "O plano prevê 1.100 km de novas redes até 2029, financiados por caixa próprio e " +
                "por linha multilateral já contratada.",
            leituraPerfin = "Aumenta a base de remuneração regulatória, mas posterga a distribuição de " +
                "dividendos. Neutro no valor presente e negativo no fluxo de caixa de curto prazo.",
            ativosRelacionados = listOf("Distribuidora de Gás"),
            variacao = "R$ 800 mi"
        ),
        Noticia(
            id = "n09",
            manchete = "Inflação de serviços desacelera e reforça cenário de curva mais benigna",
            chapeu = "Macro · Inflação",
            fonte = "Boletim Macro",
            horario = "08h30",
            impacto = Impacto.BAIXO,
            direcao = Direcao.POSITIVO,
            tags = listOf("Macro", "Inflação", "IPCA"),
            resumo = "O núcleo de serviços recuou para 0,31% no mês, ante 0,44% no anterior.",
            leituraPerfin = "Marginalmente positivo para os ativos indexados ao IPCA no longo prazo. " +
                "Não altera a tese de nenhuma posição atual.",
            ativosRelacionados = listOf("Carteira indexada"),
            variacao = "0,31% no mês"
        ),
        Noticia(
            id = "n10",
            manchete = "Porto do Sul registra recorde mensal de movimentação de contêineres",
            chapeu = "Logística",
            fonte = "Painel de Infraestrutura",
            horario = "08h48",
            impacto = Impacto.BAIXO,
            direcao = Direcao.POSITIVO,
            tags = listOf("Portos", "Logística", "Operacional"),
            resumo = "Foram 412 mil TEUs no mês, alta de 9% sobre o recorde anterior.",
            leituraPerfin = "Sem exposição direta em carteira. Serve como leitura de atividade para o " +
                "corredor logístico onde a SPE Rodovias opera.",
            ativosRelacionados = listOf("Leitura setorial"),
            variacao = "412 mil TEUs"
        ),
        Noticia(
            id = "n11",
            manchete = "Fundo estrangeiro anuncia veículo de US$ 2 bilhões para infraestrutura na região",
            chapeu = "Mercado · Captação",
            fonte = "Diário Econômico",
            horario = "09h05",
            impacto = Impacto.BAIXO,
            direcao = Direcao.NEUTRO,
            tags = listOf("Captação", "Competição", "M&A"),
            resumo = "O veículo terá foco em energia e saneamento, com ticket médio entre US$ 100 e " +
                "US$ 300 milhões.",
            leituraPerfin = "Aumenta a competição por ativos na faixa de ticket em que atuamos. " +
                "Monitorar o efeito sobre os múltiplos de entrada do pipeline 2026.",
            ativosRelacionados = listOf("Pipeline 2026"),
            variacao = "US$ 2 bi"
        ),
        Noticia(
            id = "n12",
            manchete = "Tribunal de contas suspende cautelarmente edital de PPP de iluminação pública",
            chapeu = "Concessões · Judicial",
            fonte = "Radar Jurídico",
            horario = "09h22",
            impacto = Impacto.BAIXO,
            direcao = Direcao.NEGATIVO,
            tags = listOf("PPP", "Judicial", "Municipal"),
            resumo = "A suspensão decorre de questionamento sobre a matriz de risco cambial do edital.",
            leituraPerfin = "Não há exposição direta. É precedente relevante para a estruturação da " +
                "matriz de risco nas PPPs municipais em avaliação.",
            ativosRelacionados = listOf("Leitura setorial"),
            variacao = "edital suspenso"
        )
    ).sortedByDescending { it.impacto.peso }

    val alertas: List<Alerta> = listOf(
        Alerta(
            id = "a01",
            titulo = "Selic mantida: revisar covenants das SPEs indexadas ao CDI",
            fonte = "Boletim Macro",
            horario = "06h47",
            impacto = Impacto.ALTO,
            detalhe = "Três SPEs com folga de covenant inferior a 0,3x na última apuração."
        ),
        Alerta(
            id = "a02",
            titulo = "Ágio de 32% em leilão comparável ao ativo de saneamento em carteira",
            fonte = "Diário Econômico",
            horario = "06h33",
            impacto = Impacto.ALTO,
            detalhe = "Sugere reprecificação para cima do ativo maduro."
        ),
        Alerta(
            id = "a03",
            titulo = "Consulta pública da CVM encerra em 45 dias",
            fonte = "CVM",
            horario = "08h04",
            impacto = Impacto.MEDIO,
            detalhe = "Necessário posicionamento do time de compliance."
        ),
        Alerta(
            id = "a04",
            titulo = "Tráfego de julho acima do budget do modelo",
            fonte = "Painel de Infraestrutura",
            horario = "07h36",
            impacto = Impacto.MEDIO,
            detalhe = "Realizado de 4,8% contra 3,2% projetado."
        ),
        Alerta(
            id = "a05",
            titulo = "Janela de funding longo reaberta para transmissão",
            fonte = "Radar de Crédito",
            horario = "07h08",
            impacto = Impacto.BAIXO,
            detalhe = "Spread de referência em IPCA+ 6,15%."
        )
    )

    val fontes: List<String> = listOf(
        "Agência Setorial",
        "Boletim Macro",
        "CVM",
        "Diário Econômico",
        "Diário Oficial",
        "Painel de Infraestrutura",
        "Radar de Crédito",
        "Radar Jurídico"
    )

    val contatos: List<Contato> = listOf(
        Contato("c1", "Comitê de Investimentos", "Lista · 7 membros"),
        Contato("c2", "Time de Energia", "Lista · 4 membros"),
        Contato("c3", "Time de Saneamento", "Lista · 3 membros"),
        Contato("c4", "Ana Beatriz Moraes", "Sócia"),
        Contato("c5", "Carlos Eduardo Lima", "Analista sênior"),
        Contato("c6", "Marina Duarte", "Analista de crédito"),
        Contato("c7", "Compliance", "Lista · 2 membros")
    )
}
