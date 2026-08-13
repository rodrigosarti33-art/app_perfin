package br.com.perfininfra.news

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import br.com.perfininfra.news.ui.screens.AlertasScreen
import br.com.perfininfra.news.ui.screens.DetalheScreen
import br.com.perfininfra.news.ui.screens.EnvioScreen
import br.com.perfininfra.news.ui.screens.HomeScreen
import br.com.perfininfra.news.ui.screens.LoginScreen
import br.com.perfininfra.news.ui.theme.PerfinNewsTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PerfinNewsTheme {
                PerfinNewsApp()
            }
        }
    }
}

private object Rotas {
    const val LOGIN = "login"
    const val HOME = "home"
    const val ALERTAS = "alertas"
    const val DETALHE = "detalhe/{noticiaId}"
    const val ENVIO = "envio/{noticiaId}"

    fun detalhe(id: String) = "detalhe/$id"
    fun envio(id: String) = "envio/$id"
}

@Composable
fun PerfinNewsApp(vm: PerfinNewsViewModel = viewModel()) {
    val nav = rememberNavController()
    val snackbar = remember { SnackbarHostState() }
    val escopo = rememberCoroutineScope()

    fun mensagem(texto: String) {
        escopo.launch { snackbar.showSnackbar(texto) }
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        snackbarHost = { SnackbarHost(snackbar) }
    ) { padding ->
        NavHost(
            navController = nav,
            startDestination = Rotas.LOGIN,
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            composable(Rotas.LOGIN) {
                LoginScreen(
                    vm = vm,
                    onEntrar = {
                        nav.navigate(Rotas.HOME) {
                            popUpTo(Rotas.LOGIN) { inclusive = true }
                        }
                    },
                    onMensagem = ::mensagem
                )
            }

            composable(Rotas.HOME) {
                HomeScreen(
                    vm = vm,
                    onAbrirNoticia = { id -> nav.navigate(Rotas.detalhe(id)) },
                    onAbrirAlertas = { nav.navigate(Rotas.ALERTAS) },
                    onSair = {
                        vm.sair()
                        nav.navigate(Rotas.LOGIN) {
                            popUpTo(Rotas.HOME) { inclusive = true }
                        }
                    },
                    onMensagem = ::mensagem
                )
            }

            composable(Rotas.ALERTAS) {
                AlertasScreen(vm = vm, onVoltar = { nav.popBackStack() })
            }

            composable(
                route = Rotas.DETALHE,
                arguments = listOf(navArgument("noticiaId") { type = NavType.StringType })
            ) { entrada ->
                val id = entrada.arguments?.getString("noticiaId").orEmpty()
                val noticia = vm.noticiaPorId(id)
                if (noticia == null) {
                    LaunchedEffect(id) { nav.popBackStack() }
                } else {
                    DetalheScreen(
                        vm = vm,
                        noticia = noticia,
                        onVoltar = { nav.popBackStack() },
                        onEnviar = { nav.navigate(Rotas.envio(id)) }
                    )
                }
            }

            composable(
                route = Rotas.ENVIO,
                arguments = listOf(navArgument("noticiaId") { type = NavType.StringType })
            ) { entrada ->
                val id = entrada.arguments?.getString("noticiaId").orEmpty()
                val noticia = vm.noticiaPorId(id)
                if (noticia == null) {
                    LaunchedEffect(id) { nav.popBackStack() }
                } else {
                    EnvioScreen(
                        vm = vm,
                        noticia = noticia,
                        onVoltar = { nav.popBackStack() },
                        onEnviado = { texto ->
                            nav.popBackStack(Rotas.HOME, inclusive = false)
                            mensagem(texto)
                        }
                    )
                }
            }
        }
    }
}
