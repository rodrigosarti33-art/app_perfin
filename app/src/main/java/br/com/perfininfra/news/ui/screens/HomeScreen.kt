package br.com.perfininfra.news.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.outlined.Article
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Source
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.perfininfra.news.PerfinNewsViewModel
import br.com.perfininfra.news.data.EstiloCard
import br.com.perfininfra.news.data.Noticia
import br.com.perfininfra.news.ui.components.AssinaturaPerfin
import br.com.perfininfra.news.ui.components.CardCompacto
import br.com.perfininfra.news.ui.components.CardEditorial
import br.com.perfininfra.news.ui.components.GradienteInstitucional
import br.com.perfininfra.news.ui.theme.GreenBright
import br.com.perfininfra.news.ui.theme.NavyInstitutional

private enum class Aba(val rotulo: String, val icone: ImageVector) {
    BRIEFING("Briefing", Icons.Outlined.Article),
    FONTES("Fontes", Icons.Outlined.Source),
    PERFIL("Perfil", Icons.Outlined.Person)
}

@Composable
fun HomeScreen(
    vm: PerfinNewsViewModel,
    onAbrirNoticia: (String) -> Unit,
    onAbrirAlertas: () -> Unit,
    onSair: () -> Unit,
    onMensagem: (String) -> Unit
) {
    // rememberSaveable para que a aba escolhida sobreviva a mudanças de
    // configuração (rotação, alternância entre tema claro e escuro).
    var abaIndice by rememberSaveable { mutableIntStateOf(0) }
    val aba = Aba.entries[abaIndice]

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = { BarraSuperior(vm.alertasNaoLidos, onAbrirAlertas) },
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface,
                tonalElevation = 0.dp
            ) {
                Aba.entries.forEachIndexed { indice, destino ->
                    NavigationBarItem(
                        selected = aba == destino,
                        onClick = { abaIndice = indice },
                        icon = { Icon(destino.icone, contentDescription = destino.rotulo) },
                        label = { Text(destino.rotulo, fontSize = 12.sp) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = NavyInstitutional,
                            selectedTextColor = NavyInstitutional,
                            indicatorColor = GreenBright.copy(alpha = 0.14f),
                            unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )
                }
            }
        }
    ) { padding ->
        Box(Modifier.padding(padding)) {
            when (aba) {
                Aba.BRIEFING -> AbaBriefing(vm, onAbrirNoticia)
                Aba.FONTES -> AbaFontes(vm, onAbrirNoticia)
                Aba.PERFIL -> AbaPerfil(vm, onSair, onMensagem)
            }
        }
    }
}

@Composable
private fun BarraSuperior(naoLidos: Int, onAbrirAlertas: () -> Unit) {
    Column {
        Row(
            Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface)
                .padding(start = 16.dp, end = 8.dp, top = 14.dp, bottom = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AssinaturaPerfin(tamanhoTexto = 13.sp, tamanhoSimbolo = 22.dp)
            Spacer(Modifier.weight(1f))
            IconButton(onClick = onAbrirAlertas) {
                BadgedBox(
                    badge = {
                        if (naoLidos > 0) {
                            Badge(containerColor = GreenBright, contentColor = Color.White) {
                                Text("$naoLidos")
                            }
                        }
                    }
                ) {
                    Icon(
                        Icons.Filled.Notifications,
                        contentDescription = "Alertas",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
        Box(
            Modifier
                .fillMaxWidth()
                .height(3.dp)
                .background(GradienteInstitucional)
        )
    }
}

@Composable
private fun AbaBriefing(vm: PerfinNewsViewModel, onAbrirNoticia: (String) -> Unit) {
    val noticias = vm.briefing
    LazyColumn(
        Modifier.fillMaxSize(),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(if (vm.estiloCard == EstiloCard.EDITORIAL) 12.dp else 0.dp)
    ) {
        item {
            Column(Modifier.padding(bottom = 14.dp)) {
                Text(
                    text = "BRIEFING DE HOJE",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(Modifier.height(6.dp))
                Text(
                    text = "O que mudou desde ontem",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(Modifier.height(6.dp))
                Text(
                    text = "${noticias.size} manchetes ordenadas por impacto sobre as teses do portfólio.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(Modifier.height(14.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Switch(
                        checked = vm.somenteAltoImpacto,
                        onCheckedChange = vm::alternarSomenteAltoImpacto
                    )
                    Spacer(Modifier.width(10.dp))
                    Text(
                        text = "Somente alto impacto",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        items(noticias, key = { it.id }) { noticia ->
            ItemNoticia(vm, noticia, onAbrirNoticia)
        }

        item {
            Spacer(Modifier.height(20.dp))
            Text(
                text = "Manchetes, números e nomes são dados simulados para prototipagem.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun ItemNoticia(
    vm: PerfinNewsViewModel,
    noticia: Noticia,
    onAbrirNoticia: (String) -> Unit
) {
    if (vm.estiloCard == EstiloCard.EDITORIAL) {
        CardEditorial(
            noticia = noticia,
            enviada = vm.jaEnviada(noticia.id),
            onClick = { onAbrirNoticia(noticia.id) }
        )
    } else {
        Column {
            CardCompacto(
                noticia = noticia,
                enviada = vm.jaEnviada(noticia.id),
                onClick = { onAbrirNoticia(noticia.id) }
            )
            HorizontalDivider(color = MaterialTheme.colorScheme.outline)
        }
    }
}

@Composable
private fun AbaFontes(vm: PerfinNewsViewModel, onAbrirNoticia: (String) -> Unit) {
    val noticias = vm.noticiasDaFonte(vm.fonteSelecionada)
    LazyColumn(
        Modifier.fillMaxSize(),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 24.dp)
    ) {
        item {
            Text(
                text = "FONTES MONITORADAS",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(Modifier.height(12.dp))
            FiltroFontes(
                fontes = br.com.perfininfra.news.data.MockData.fontes,
                selecionada = vm.fonteSelecionada,
                onSelecionar = vm::selecionarFonte
            )
            Spacer(Modifier.height(18.dp))
            Text(
                text = "${noticias.size} matérias",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(Modifier.height(8.dp))
        }
        items(noticias, key = { it.id }) { noticia ->
            Column {
                CardCompacto(
                    noticia = noticia,
                    enviada = vm.jaEnviada(noticia.id),
                    onClick = { onAbrirNoticia(noticia.id) }
                )
                HorizontalDivider(color = MaterialTheme.colorScheme.outline)
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FiltroFontes(
    fontes: List<String>,
    selecionada: String?,
    onSelecionar: (String?) -> Unit
) {
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        ChipFonte("Todas", selecionada == null) { onSelecionar(null) }
        fontes.forEach { fonte ->
            ChipFonte(fonte, selecionada == fonte) { onSelecionar(fonte) }
        }
    }
}

@Composable
private fun ChipFonte(texto: String, selecionado: Boolean, onClick: () -> Unit) {
    val corBorda = if (selecionado) GreenBright else MaterialTheme.colorScheme.outline
    val corFundo = if (selecionado) GreenBright.copy(alpha = 0.12f) else MaterialTheme.colorScheme.surface
    Box(
        Modifier
            .background(corFundo, CircleShape)
            .border(1.dp, corBorda, CircleShape)
            .clickable { onClick() }
            .padding(horizontal = 14.dp, vertical = 8.dp)
    ) {
        Text(
            text = texto,
            style = MaterialTheme.typography.bodySmall,
            color = if (selecionado) MaterialTheme.colorScheme.onSurface
            else MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun AbaPerfil(
    vm: PerfinNewsViewModel,
    onSair: () -> Unit,
    onMensagem: (String) -> Unit
) {
    PerfilConteudo(vm = vm, onSair = onSair, onMensagem = onMensagem)
}
