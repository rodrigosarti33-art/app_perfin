# PERFIN NEWS · Android

Protótipo navegável do briefing matinal de notícias de alto impacto para o time de
investimentos da PERFIN infra, implementado em **Kotlin + Jetpack Compose**.

O app é construído a partir do briefing de design em `App_Perfin_News/app-perfin.pdf`.

---

## APK

| | |
|---|---|
| Arquivo | `PerfinNews-debug.apk` (na raiz desta pasta) |
| Package | `br.com.perfininfra.news.debug` |
| Versão | 1.0.0 |
| Assinatura | chave de debug do Android (não é build de produção) |
| Android mínimo | 8.0 (API 26) · alvo API 34 |

**Instalação no aparelho:** copie o `.apk` para o celular, abra-o pelo gerenciador de
arquivos e autorize "instalar de fontes desconhecidas". Ou, com o aparelho em modo
desenvolvedor e conectado por USB:

```bash
adb install -r PerfinNews-debug.apk
```

**Acesso de demonstração:** usuário `rsarti` · senha `rsarti`

---

## O que está implementado

**Login** — cabeçalho com o gradiente institucional (navy → verde), assinatura
`PERFIN infra`, campos de acesso corporativo, botão ENTRAR, além dos links "Entrar com
SSO Perfin" e "Esqueci a senha" (ambos informam que não estão disponíveis no protótipo).

**Briefing** — manchetes ordenadas por impacto sobre as teses do portfólio, com filtro
rápido "somente alto impacto". Cada item traz selo de impacto, chapéu, horário, fonte e
tags.

**Variação de card** — as duas alternativas do briefing de design convivem no app e podem
ser trocadas em Perfil › Apresentação do card:
- **EDITORIAL** (padrão): manchete, resumo, tags e miniatura.
- **COMPACTO**: densidade máxima, com barra de impacto à esquerda.

**Navegação** — variação **TABS**: três destinos fixos no rodapé (Briefing · Fontes ·
Perfil), conforme a alternativa destacada no briefing.

**Detalhe da notícia** — resumo automático, bloco "Impacto para o portfólio" com a leitura
Perfin, ativos e teses relacionados, tags, e ação de salvar.

**Enviar ao time** — fluxo completo de envio: seleção de destinatários (listas e pessoas),
escolha de canal (E-mail · Teams · WhatsApp), comentário editável e confirmação com
retorno visual. Os envios ficam registrados e aparecem em Perfil.

**Alertas** — o sino na barra superior abre a lista de alertas com filtro por fonte e
contador de não lidos.

**Perfil** — identificação, troca da variação de card, preferência de digest push às 6h30,
priorização de alto impacto, indicadores da sessão (manchetes, salvas, envios) e histórico
de envios.

**Tema** — claro e escuro, ambos com a paleta institucional
(`#002440 → #33665E → #4CAC87`, azul institucional `#0A4C82`, cinzas neutros e
verde/vermelho discretos para sinalização).

---

## Premissas assumidas

A exportação do PDF do briefing veio com a coluna direita cortada — todas as linhas do
texto terminam truncadas. As decisões abaixo foram tomadas para fechar as lacunas e devem
ser confirmadas:

1. **Senha de demonstração** — o PDF mostra o usuário `rsarti` e a senha começando por
   `rsar…`, mascarada. Adotamos `rsarti` como senha do protótipo.
2. **Variações escolhidas como padrão** — EDITORIAL (card) e TABS (navegação), que
   aparecem destacadas em verde no comparativo do briefing. A alternativa COMPACTO está
   implementada e acessível; a alternativa DRAWER não foi implementada, já que TABS é a
   selecionada.
3. **Três destinos do rodapé** — o briefing informa "três destinos fixos" sem nomeá-los.
   Adotamos Briefing · Fontes · Perfil, com os alertas no sino da barra superior (o
   briefing posiciona o sino ali).
4. **Canais de envio** — E-mail, Teams e WhatsApp. O briefing cita "Enviar ao time" sem
   listar canais.
5. **Autenticação e envio são simulados** — não há rede, backend nem persistência. Nada
   sai do aparelho e o estado se perde ao fechar o app.
6. **Marca** — o símbolo (dois quadrados sobrepostos) e o wordmark são reproduzidos
   vetorialmente como placeholder, e a tipografia usa a fonte de sistema no lugar da
   Montserrat. Substituir pelos arquivos oficiais quando disponíveis.

## Fora de escopo nesta versão

Itens listados como "próximos passos" no briefing e não implementados: radar regulatório
CVM com prazo ativo (há apenas o ponto de entrada em Perfil), disparo real do digest push
das 6h30 (existe a preferência, não o agendamento) e onboarding diferenciado por perfil
(analista vs. sócio).

## Dados

Todas as manchetes, números, fontes, nomes e alertas são **fictícios**, criados para
prototipagem conforme a nota do próprio briefing. Nenhuma informação do app deve ser usada
como insumo de decisão de investimento. O dataset está em
`app/src/main/java/br/com/perfininfra/news/data/MockData.kt` — é o único arquivo a trocar
quando houver fonte real de notícias.

---

## Compilar do código-fonte

Requisitos: JDK 17 e Android SDK (API 34, build-tools 34.0.0).

```bash
gradlew.bat assembleDebug
```

O APK sai em `app/build/outputs/apk/debug/app-debug.apk`.

Pelo Android Studio: abrir esta pasta como projeto e usar Run.

## Estrutura

```
app/src/main/java/br/com/perfininfra/news/
├── MainActivity.kt              Activity única + rotas de navegação
├── PerfinNewsViewModel.kt       Estado do app (login, preferências, envios)
├── data/
│   ├── Models.kt                Notícia, Alerta, Contato, Impacto, EstiloCard
│   └── MockData.kt              Dataset simulado
└── ui/
    ├── theme/                   Paleta institucional e tipografia
    ├── components/              Marca, gradiente, cards EDITORIAL e COMPACTO
    └── screens/                 Login, Home (3 abas), Detalhe, Envio, Alertas, Perfil
```
