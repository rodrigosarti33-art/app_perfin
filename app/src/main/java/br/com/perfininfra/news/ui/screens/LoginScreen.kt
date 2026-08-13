package br.com.perfininfra.news.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.perfininfra.news.PerfinNewsViewModel
import br.com.perfininfra.news.ui.components.AssinaturaPerfin
import br.com.perfininfra.news.ui.components.GradienteInstitucional
import br.com.perfininfra.news.ui.theme.GreenBright
import br.com.perfininfra.news.ui.theme.NavyInstitutional

@Composable
fun LoginScreen(
    vm: PerfinNewsViewModel,
    onEntrar: () -> Unit,
    onMensagem: (String) -> Unit
) {
    var senhaVisivel by rememberSaveable { mutableStateOf(false) }
    val teclado = LocalSoftwareKeyboardController.current

    fun submeter() {
        teclado?.hide()
        if (vm.entrar()) onEntrar()
    }

    Column(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .verticalScroll(rememberScrollState())
            .imePadding()
    ) {
        // Cabeçalho com o gradiente institucional
        Box(
            Modifier
                .fillMaxWidth()
                .height(340.dp)
                .background(GradienteInstitucional)
        ) {
            Column(
                Modifier
                    .fillMaxHeight()
                    .padding(horizontal = 28.dp)
                    .padding(top = 56.dp),
                verticalArrangement = Arrangement.Top
            ) {
                AssinaturaPerfin(
                    corPerfin = Color.White,
                    corInfra = Color.White.copy(alpha = 0.82f),
                    tamanhoTexto = 17.sp,
                    tamanhoSimbolo = 34.dp
                )
                Spacer(Modifier.height(36.dp))
                Text(
                    text = "PERFIN NEWS",
                    style = MaterialTheme.typography.labelMedium,
                    color = Color.White.copy(alpha = 0.75f)
                )
                Spacer(Modifier.height(10.dp))
                Text(
                    text = "Briefing de notícias de alto impacto para o time de investimentos.",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Normal,
                    color = Color.White
                )
            }
        }

        // Área de acesso
        Column(
            Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface)
                .padding(horizontal = 28.dp, vertical = 28.dp)
        ) {
            Text(
                text = "ACESSO CORPORATIVO",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(Modifier.height(22.dp))

            CampoLogin(
                rotulo = "USUÁRIO",
                valor = vm.usuario,
                onValorChange = vm::onUsuarioChange,
                imeAction = ImeAction.Next,
                onSubmit = { },
                keyboardType = KeyboardType.Text
            )

            Spacer(Modifier.height(18.dp))

            CampoLogin(
                rotulo = "SENHA",
                valor = vm.senha,
                onValorChange = vm::onSenhaChange,
                imeAction = ImeAction.Done,
                onSubmit = { submeter() },
                keyboardType = KeyboardType.Password,
                visualTransformation = if (senhaVisivel) VisualTransformation.None
                else PasswordVisualTransformation('•'),
                trailing = {
                    IconButton(onClick = { senhaVisivel = !senhaVisivel }) {
                        Icon(
                            imageVector = if (senhaVisivel) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                            contentDescription = if (senhaVisivel) "Ocultar senha" else "Mostrar senha",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            )

            vm.erroLogin?.let {
                Spacer(Modifier.height(12.dp))
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.error
                )
            }

            Spacer(Modifier.height(26.dp))

            Button(
                onClick = { submeter() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                shape = RoundedCornerShape(2.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = NavyInstitutional,
                    contentColor = Color.White
                )
            ) {
                Text("ENTRAR", style = MaterialTheme.typography.labelLarge)
            }

            Spacer(Modifier.height(14.dp))

            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(onClick = { onMensagem("SSO Perfin não disponível no protótipo.") }) {
                    Text(
                        "Entrar com SSO Perfin",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                TextButton(onClick = { onMensagem("Recuperação de senha indisponível no protótipo.") }) {
                    Text(
                        "Esqueci a senha",
                        style = MaterialTheme.typography.bodyMedium,
                        color = NavyInstitutional
                    )
                }
            }

            Spacer(Modifier.height(18.dp))

            Text(
                text = "Protótipo com dados simulados · acesso de demonstração: rsarti / rsarti",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun CampoLogin(
    rotulo: String,
    valor: String,
    onValorChange: (String) -> Unit,
    imeAction: ImeAction,
    onSubmit: () -> Unit,
    keyboardType: KeyboardType,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    trailing: (@Composable () -> Unit)? = null
) {
    Column {
        Text(
            text = rotulo,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        OutlinedTextField(
            value = valor,
            onValueChange = onValorChange,
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            visualTransformation = visualTransformation,
            trailingIcon = trailing,
            textStyle = MaterialTheme.typography.bodyLarge,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction),
            keyboardActions = KeyboardActions(
                onDone = { onSubmit() },
                onNext = { }
            ),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                cursorColor = GreenBright
            )
        )
        Box(
            Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(MaterialTheme.colorScheme.outline)
        )
    }
}
