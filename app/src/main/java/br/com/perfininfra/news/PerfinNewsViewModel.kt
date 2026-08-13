package br.com.perfininfra.news

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import br.com.perfininfra.news.data.Alerta
import br.com.perfininfra.news.data.Contato
import br.com.perfininfra.news.data.EstiloCard
import br.com.perfininfra.news.data.Impacto
import br.com.perfininfra.news.data.MockData
import br.com.perfininfra.news.data.Noticia

/** Registro de um envio feito pelo fluxo "Enviar ao time". */
data class Envio(
    val noticiaId: String,
    val manchete: String,
    val destinatarios: List<String>,
    val canal: String,
    val comentario: String
)

/**
 * Estado único do protótipo. Tudo vive em memória: não há persistência,
 * rede ou autenticação real — ver README.
 */
class PerfinNewsViewModel : ViewModel() {

    // ---- Autenticação (simulada) ----
    var usuario by mutableStateOf("")
        private set
    var senha by mutableStateOf("")
        private set
    var erroLogin by mutableStateOf<String?>(null)
        private set
    var autenticado by mutableStateOf(false)
        private set

    fun onUsuarioChange(valor: String) {
        usuario = valor
        erroLogin = null
    }

    fun onSenhaChange(valor: String) {
        senha = valor
        erroLogin = null
    }

    fun entrar(): Boolean {
        val usuarioOk = usuario.trim().equals(MockData.USUARIO_DEMO, ignoreCase = true)
        val senhaOk = senha == MockData.SENHA_DEMO
        return if (usuarioOk && senhaOk) {
            autenticado = true
            erroLogin = null
            true
        } else {
            erroLogin = "Usuário ou senha inválidos."
            false
        }
    }

    fun sair() {
        autenticado = false
        usuario = ""
        senha = ""
        erroLogin = null
    }

    // ---- Preferências de apresentação ----
    var estiloCard by mutableStateOf(EstiloCard.EDITORIAL)
        private set

    fun alternarEstiloCard(estilo: EstiloCard) {
        estiloCard = estilo
    }

    var digestAtivo by mutableStateOf(true)
        private set

    fun alternarDigest(ativo: Boolean) {
        digestAtivo = ativo
    }

    var somenteAltoImpacto by mutableStateOf(false)
        private set

    fun alternarSomenteAltoImpacto(ativo: Boolean) {
        somenteAltoImpacto = ativo
    }

    // ---- Briefing ----
    val todasNoticias: List<Noticia> = MockData.noticias

    var fonteSelecionada by mutableStateOf<String?>(null)
        private set

    fun selecionarFonte(fonte: String?) {
        fonteSelecionada = fonte
    }

    val briefing: List<Noticia>
        get() = todasNoticias.filter { noticia ->
            (!somenteAltoImpacto || noticia.impacto == Impacto.ALTO)
        }

    fun noticiasDaFonte(fonte: String?): List<Noticia> =
        if (fonte == null) todasNoticias else todasNoticias.filter { it.fonte == fonte }

    fun noticiaPorId(id: String): Noticia? = todasNoticias.firstOrNull { it.id == id }

    // ---- Alertas ----
    var filtroFonteAlertas by mutableStateOf<String?>(null)
        private set

    fun filtrarAlertasPorFonte(fonte: String?) {
        filtroFonteAlertas = fonte
    }

    val alertas: List<Alerta>
        get() = MockData.alertas.filter { filtroFonteAlertas == null || it.fonte == filtroFonteAlertas }

    private val alertasLidos = mutableStateListOf<String>()

    val alertasNaoLidos: Int
        get() = MockData.alertas.count { it.id !in alertasLidos }

    fun marcarAlertasComoLidos() {
        MockData.alertas.forEach { if (it.id !in alertasLidos) alertasLidos.add(it.id) }
    }

    // ---- Envio ao time ----
    val contatos: List<Contato> = MockData.contatos

    private val _envios = mutableStateListOf<Envio>()
    val envios: List<Envio> get() = _envios

    fun registrarEnvio(envio: Envio) {
        _envios.add(0, envio)
    }

    fun jaEnviada(noticiaId: String): Boolean = _envios.any { it.noticiaId == noticiaId }

    // ---- Salvos ----
    private val _salvas = mutableStateListOf<String>()
    val salvas: List<String> get() = _salvas

    fun alternarSalvar(noticiaId: String) {
        if (_salvas.contains(noticiaId)) _salvas.remove(noticiaId) else _salvas.add(noticiaId)
    }

    fun estaSalva(noticiaId: String): Boolean = _salvas.contains(noticiaId)
}
