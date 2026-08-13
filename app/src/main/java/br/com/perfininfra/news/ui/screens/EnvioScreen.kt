package br.com.perfininfra.news.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import br.com.perfininfra.news.Envio
import br.com.perfininfra.news.PerfinNewsViewModel
import br.com.perfininfra.news.data.Noticia
import br.com.perfininfra.news.ui.theme.GreenBright
import br.com.perfininfra.news.ui.theme.NavyInstitutional

private val CANAIS = listOf("E-mail", "Teams", "WhatsApp")

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun EnvioScreen(
    vm: PerfinNewsViewModel,
    noticia: Noticia,
    onVoltar: () -> Unit,
    onEnviado: (String) -> Unit
) {
    val selecionados = remember { mutableListOf<String>().toMutableStateList() }
    var canal by rememberSaveable { mutableStateOf(CANAIS.first()) }
    var comentario by rememberSaveable {
        mutableStateOf("Destaque do briefing de hoje. Vale acompanhar o desdobramento.")
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = { Text("Enviar ao time", style = MaterialTheme.typography.titleMedium) },
                navigationIcon = {
                    IconButton(onClick = onVoltar) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Voltar",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        },
        bottomBar = {
            Column(
                Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(16.dp)
            ) {
                Button(
                    onClick = {
                        val nomes = vm.contatos.filter { it.id in selecionados }.map { it.nome }
                        vm.registrarEnvio(
                            Envio(
                                noticiaId = noticia.id,
                                manchete = noticia.manchete,
                                destinatarios = nomes,
                                canal = canal,
                                comentario = comentario
                            )
                        )
                        val destino = if (nomes.size == 1) nomes.first() else "${nomes.size} destinatários"
                        onEnviado("Enviado por $canal para $destino.")
                    },
                    enabled = selecionados.isNotEmpty(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(2.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = NavyInstitutional,
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        text = if (selecionados.isEmpty()) "SELECIONE OS DESTINATÁRIOS"
                        else "CONFIRMAR ENVIO (${selecionados.size})",
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }
        }
    ) { padding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .imePadding()
                .padding(20.dp)
        ) {
            Text(
                text = "MATÉRIA",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = noticia.manchete,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = "${noticia.fonte} · ${noticia.horario} · ${noticia.impacto.rotulo}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(Modifier.height(26.dp))

            Text(
                text = "DESTINATÁRIOS",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(Modifier.height(12.dp))
            vm.contatos.forEach { contato ->
                val marcado = contato.id in selecionados
                Row(
                    Modifier
                        .fillMaxWidth()
                        .clickable {
                            if (marcado) selecionados.remove(contato.id) else selecionados.add(contato.id)
                        }
                        .padding(vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        Modifier
                            .size(38.dp)
                            .background(
                                if (marcado) GreenBright.copy(alpha = 0.18f)
                                else MaterialTheme.colorScheme.surfaceVariant,
                                CircleShape
                            )
                            .border(
                                1.dp,
                                if (marcado) GreenBright else MaterialTheme.colorScheme.outline,
                                CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        if (marcado) {
                            Icon(Icons.Filled.Check, contentDescription = null, tint = GreenBright)
                        } else {
                            Text(
                                text = contato.iniciais,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                    Spacer(Modifier.width(14.dp))
                    Column {
                        Text(
                            text = contato.nome,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Text(
                            text = contato.cargo,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            Spacer(Modifier.height(20.dp))

            Text(
                text = "CANAL",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(Modifier.height(10.dp))
            FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                CANAIS.forEach { opcao ->
                    val ativo = canal == opcao
                    Box(
                        Modifier
                            .background(
                                if (ativo) GreenBright.copy(alpha = 0.12f)
                                else MaterialTheme.colorScheme.surface,
                                RoundedCornerShape(3.dp)
                            )
                            .border(
                                1.dp,
                                if (ativo) GreenBright else MaterialTheme.colorScheme.outline,
                                RoundedCornerShape(3.dp)
                            )
                            .clickable { canal = opcao }
                            .padding(horizontal = 16.dp, vertical = 10.dp)
                    ) {
                        Text(
                            text = opcao,
                            style = MaterialTheme.typography.bodyMedium,
                            color = if (ativo) MaterialTheme.colorScheme.onSurface
                            else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            Spacer(Modifier.height(24.dp))

            Text(
                text = "COMENTÁRIO",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(
                value = comentario,
                onValueChange = { comentario = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                textStyle = MaterialTheme.typography.bodyMedium,
                shape = RoundedCornerShape(3.dp)
            )

            Spacer(Modifier.height(18.dp))

            Text(
                text = "O envio é simulado: nenhuma mensagem sai do aparelho.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
