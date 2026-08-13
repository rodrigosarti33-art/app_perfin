package br.com.perfininfra.news.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import br.com.perfininfra.news.PerfinNewsViewModel
import br.com.perfininfra.news.data.EstiloCard
import br.com.perfininfra.news.data.MockData
import br.com.perfininfra.news.ui.theme.GreenBright
import br.com.perfininfra.news.ui.theme.NavyDeep

@Composable
fun PerfilConteudo(
    vm: PerfinNewsViewModel,
    onSair: () -> Unit,
    onMensagem: (String) -> Unit
) {
    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {
        // Identificação
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                Modifier
                    .size(56.dp)
                    .background(
                        Brush.linearGradient(listOf(NavyDeep, GreenBright)),
                        CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "RS",
                    style = MaterialTheme.typography.titleMedium,
                    color = androidx.compose.ui.graphics.Color.White
                )
            }
            Spacer(Modifier.width(16.dp))
            Column {
                Text(
                    text = "Rodrigo Sarti",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = "Analista de investimentos · PERFIN infra",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        Spacer(Modifier.height(28.dp))
        HorizontalDivider(color = MaterialTheme.colorScheme.outline)
        Spacer(Modifier.height(24.dp))

        // Variação de card do briefing de design
        Text(
            text = "APRESENTAÇÃO DO CARD",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(Modifier.height(12.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            EstiloCard.entries.forEach { estilo ->
                OpcaoEstilo(
                    estilo = estilo,
                    selecionado = vm.estiloCard == estilo,
                    onClick = { vm.alternarEstiloCard(estilo) },
                    modifier = Modifier.weight(1f)
                )
            }
        }

        Spacer(Modifier.height(28.dp))

        // Preferências
        Text(
            text = "PREFERÊNCIAS",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(Modifier.height(8.dp))

        LinhaSwitch(
            titulo = "Digest push às 6h30",
            descricao = "Resumo do briefing enviado antes da abertura do mercado.",
            marcado = vm.digestAtivo,
            onChange = vm::alternarDigest
        )
        LinhaSwitch(
            titulo = "Priorizar alto impacto",
            descricao = "Mostra apenas as manchetes de alto impacto no briefing.",
            marcado = vm.somenteAltoImpacto,
            onChange = vm::alternarSomenteAltoImpacto
        )

        Spacer(Modifier.height(24.dp))

        // Resumo da sessão
        Text(
            text = "NESTA SESSÃO",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(Modifier.height(12.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Indicador("Manchetes", "${MockData.noticias.size}", Modifier.weight(1f))
            Indicador("Salvas", "${vm.salvas.size}", Modifier.weight(1f))
            Indicador("Envios", "${vm.envios.size}", Modifier.weight(1f))
        }

        if (vm.envios.isNotEmpty()) {
            Spacer(Modifier.height(20.dp))
            Text(
                text = "ÚLTIMOS ENVIOS",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(Modifier.height(10.dp))
            vm.envios.take(4).forEach { envio ->
                Column(Modifier.padding(vertical = 8.dp)) {
                    Text(
                        text = envio.manchete,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onBackground,
                        maxLines = 2
                    )
                    Text(
                        text = "${envio.canal} · ${envio.destinatarios.joinToString(", ")}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                HorizontalDivider(color = MaterialTheme.colorScheme.outline)
            }
        }

        Spacer(Modifier.height(28.dp))

        OutlinedButton(
            onClick = { onMensagem("Radar regulatório CVM entra na próxima versão.") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(2.dp)
        ) {
            Text("Radar regulatório CVM (em breve)")
        }

        Spacer(Modifier.height(12.dp))

        OutlinedButton(
            onClick = onSair,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(2.dp)
        ) {
            Text("Sair da conta")
        }

        Spacer(Modifier.height(24.dp))

        Text(
            text = "Protótipo PERFIN NEWS · versão 1.0.0 · dados simulados. " +
                "Marca e selo são placeholders tipográficos, a substituir pelos arquivos oficiais.",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun OpcaoEstilo(
    estilo: EstiloCard,
    selecionado: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier
            .background(
                if (selecionado) GreenBright.copy(alpha = 0.10f) else MaterialTheme.colorScheme.surface,
                RoundedCornerShape(3.dp)
            )
            .border(
                1.dp,
                if (selecionado) GreenBright else MaterialTheme.colorScheme.outline,
                RoundedCornerShape(3.dp)
            )
            .clickable(onClick = onClick)
            .padding(14.dp)
    ) {
        Text(
            text = estilo.rotulo.uppercase(),
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.Bold,
            color = if (selecionado) GreenBright else MaterialTheme.colorScheme.onSurface
        )
        Spacer(Modifier.height(6.dp))
        Text(
            text = estilo.descricao,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun LinhaSwitch(
    titulo: String,
    descricao: String,
    marcado: Boolean,
    onChange: (Boolean) -> Unit
) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(Modifier.weight(1f)) {
            Text(
                text = titulo,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = descricao,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Spacer(Modifier.width(12.dp))
        Switch(checked = marcado, onCheckedChange = onChange)
    }
}

@Composable
private fun Indicador(rotulo: String, valor: String, modifier: Modifier = Modifier) {
    Column(
        modifier
            .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(3.dp))
            .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(3.dp))
            .padding(14.dp)
    ) {
        Text(
            text = valor,
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(Modifier.height(2.dp))
        Text(
            text = rotulo,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
