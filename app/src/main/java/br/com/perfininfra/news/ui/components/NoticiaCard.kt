package br.com.perfininfra.news.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import br.com.perfininfra.news.data.Direcao
import br.com.perfininfra.news.data.Impacto
import br.com.perfininfra.news.data.Noticia
import br.com.perfininfra.news.ui.theme.GreenBright
import br.com.perfininfra.news.ui.theme.ImpactHigh
import br.com.perfininfra.news.ui.theme.ImpactLow
import br.com.perfininfra.news.ui.theme.ImpactMedium
import br.com.perfininfra.news.ui.theme.NavyDeep
import br.com.perfininfra.news.ui.theme.NegativeRed
import br.com.perfininfra.news.ui.theme.PositiveGreen

fun corDoImpacto(impacto: Impacto): Color = when (impacto) {
    Impacto.ALTO -> ImpactHigh
    Impacto.MEDIO -> ImpactMedium
    Impacto.BAIXO -> ImpactLow
}

fun corDaDirecao(direcao: Direcao): Color = when (direcao) {
    Direcao.POSITIVO -> PositiveGreen
    Direcao.NEGATIVO -> NegativeRed
    Direcao.NEUTRO -> ImpactLow
}

@Composable
fun SeloImpacto(impacto: Impacto, modifier: Modifier = Modifier) {
    val cor = corDoImpacto(impacto)
    Box(
        modifier = modifier
            .background(cor.copy(alpha = 0.10f), RoundedCornerShape(3.dp))
            .border(1.dp, cor.copy(alpha = 0.35f), RoundedCornerShape(3.dp))
            .padding(horizontal = 7.dp, vertical = 3.dp)
    ) {
        Text(
            text = impacto.rotulo.uppercase(),
            style = MaterialTheme.typography.labelSmall,
            color = cor
        )
    }
}

@Composable
fun Etiqueta(texto: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(3.dp))
            .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(3.dp))
            .padding(horizontal = 7.dp, vertical = 3.dp)
    ) {
        Text(
            text = texto,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

/**
 * Miniatura da notícia. O protótipo não carrega imagens de rede, então usamos
 * um bloco gráfico com o gradiente institucional e a inicial do chapéu.
 */
@Composable
private fun Miniatura(noticia: Noticia, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .background(
                Brush.linearGradient(listOf(NavyDeep, GreenBright.copy(alpha = 0.85f))),
                RoundedCornerShape(4.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = noticia.chapeu.take(1).uppercase(),
            style = MaterialTheme.typography.headlineSmall,
            color = Color.White
        )
    }
}

/** Variação EDITORIAL: manchete, resumo, tags e miniatura. */
@Composable
fun CardEditorial(
    noticia: Noticia,
    enviada: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(6.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
    ) {
        Column(Modifier.padding(14.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                SeloImpacto(noticia.impacto)
                Spacer(Modifier.width(8.dp))
                Text(
                    text = noticia.chapeu,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = noticia.horario,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(Modifier.height(10.dp))

            Row {
                Column(Modifier.weight(1f)) {
                    Text(
                        text = noticia.manchete,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(Modifier.height(6.dp))
                    Text(
                        text = noticia.resumo,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Spacer(Modifier.width(12.dp))
                Miniatura(noticia, Modifier.size(64.dp))
            }

            Spacer(Modifier.height(12.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                noticia.tags.take(2).forEach { Etiqueta(it) }
                Spacer(Modifier.weight(1f))
                if (enviada) {
                    Text(
                        text = "Enviada",
                        style = MaterialTheme.typography.bodySmall,
                        color = PositiveGreen
                    )
                    Spacer(Modifier.width(8.dp))
                }
                Text(
                    text = noticia.fonte,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

/** Variação COMPACTO: densidade máxima, com barra de impacto à esquerda. */
@Composable
fun CardCompacto(
    noticia: Noticia,
    enviada: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .clickable(onClick = onClick)
            .padding(end = 14.dp),
        verticalAlignment = Alignment.Top
    ) {
        Box(
            Modifier
                .padding(vertical = 10.dp)
                .width(3.dp)
                .height(46.dp)
                .background(corDoImpacto(noticia.impacto))
        )
        Spacer(Modifier.width(11.dp))
        Column(Modifier.padding(vertical = 10.dp)) {
            Text(
                text = noticia.manchete,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(Modifier.height(3.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = noticia.fonte,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "  ·  ${noticia.horario}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                noticia.variacao?.let {
                    Text(
                        text = "  ·  $it",
                        style = MaterialTheme.typography.bodySmall,
                        color = corDaDirecao(noticia.direcao)
                    )
                }
                if (enviada) {
                    Text(
                        text = "  ·  enviada",
                        style = MaterialTheme.typography.bodySmall,
                        color = PositiveGreen
                    )
                }
            }
        }
    }
}
