package br.com.perfininfra.news.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.perfininfra.news.ui.theme.GreenBright
import br.com.perfininfra.news.ui.theme.NavyDeep
import br.com.perfininfra.news.ui.theme.GreenMid
import br.com.perfininfra.news.ui.theme.NavyInstitutional

/**
 * Símbolo da marca: dois quadrados vazados sobrepostos.
 * Placeholder tipográfico — substituir pelo arquivo oficial da marca.
 */
@Composable
fun SimboloPerfin(
    modifier: Modifier = Modifier,
    tamanho: Dp = 32.dp,
    corPrimaria: Color = NavyInstitutional,
    corSecundaria: Color = GreenBright
) {
    Canvas(modifier = modifier.size(tamanho)) {
        val lado = size.minDimension * 0.62f
        val traco = size.minDimension * 0.075f
        drawRect(
            color = corPrimaria,
            topLeft = Offset(0f, 0f),
            size = Size(lado, lado),
            style = Stroke(width = traco)
        )
        drawRect(
            color = corSecundaria,
            topLeft = Offset(size.width - lado, size.height - lado),
            size = Size(lado, lado),
            style = Stroke(width = traco)
        )
    }
}

/** Assinatura "PERFIN infra" com o espaçamento da identidade institucional. */
@Composable
fun AssinaturaPerfin(
    modifier: Modifier = Modifier,
    corPerfin: Color = NavyInstitutional,
    corInfra: Color = GreenBright,
    tamanhoTexto: androidx.compose.ui.unit.TextUnit = 15.sp,
    mostrarSimbolo: Boolean = true,
    tamanhoSimbolo: Dp = 26.dp
) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        if (mostrarSimbolo) {
            SimboloPerfin(tamanho = tamanhoSimbolo, corPrimaria = corPerfin, corSecundaria = corInfra)
            Spacer(Modifier.width(10.dp))
        }
        Text(
            text = "PERFIN",
            color = corPerfin,
            fontSize = tamanhoTexto,
            fontWeight = FontWeight.Bold,
            letterSpacing = 3.5.sp
        )
        Spacer(Modifier.width(6.dp))
        Text(
            text = "infra",
            color = corInfra,
            fontSize = tamanhoTexto,
            fontWeight = FontWeight.Light,
            letterSpacing = 3.5.sp
        )
    }
}

/** Gradiente institucional navy → verde usado nos cabeçalhos. */
val GradienteInstitucional: Brush
    get() = Brush.linearGradient(
        colors = listOf(NavyDeep, GreenMid, GreenBright)
    )

@Composable
fun RotuloSecao(texto: String, modifier: Modifier = Modifier) {
    Text(
        text = texto.uppercase(),
        style = MaterialTheme.typography.labelSmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        modifier = modifier
    )
}

@Composable
fun EspacoLinha(altura: Dp = 12.dp) {
    Row(horizontalArrangement = Arrangement.Start) { Spacer(Modifier.size(altura)) }
}
