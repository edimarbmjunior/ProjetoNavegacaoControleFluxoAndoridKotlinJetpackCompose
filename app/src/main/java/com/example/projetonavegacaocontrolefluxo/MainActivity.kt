package com.example.projetonavegacaocontrolefluxo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.projetonavegacaocontrolefluxo.data.models.UserState
import com.example.projetonavegacaocontrolefluxo.ui.screen.AdminDashboardScreen
import com.example.projetonavegacaocontrolefluxo.ui.screen.HomeScreen
import com.example.projetonavegacaocontrolefluxo.ui.screen.LoadingScreen
import com.example.projetonavegacaocontrolefluxo.ui.screen.LoginScreen
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MainApp()
        }
    }
}

@Composable
fun MainApp() {
    /*
     * Escolha: UserState
     * Justificativa: Ao meu ver garante uma estabilidade melhor e impedi a questão de variaveis em status não reais impactar
     * na situação real. Manteém uma segurança maior para compilação ao impedir valores 'soltos' assim entrando em uma situação
     * em que não existe, pois não achou nenhuma informação inicial.
     * O estado reflete exatamente a tela atual de forma exclusiva.
     */

    var appState by remember { mutableStateOf<UserState>(UserState.Loading) }

    LaunchedEffect(Unit) {
        delay(2500)
        appState = UserState.NotLogged
    }

    Surface(modifier = Modifier.fillMaxSize()) {
        when (appState) {
            is UserState.Loading -> {
                LoadingScreen()
            }
            is UserState.NotLogged -> {
                LoginScreen(
                    onLoginAsUser = { appState = UserState.User },
                    onLoginAsAdmin = { appState = UserState.Admin }
                )
            }
            is UserState.User -> {
                HomeScreen(
                    onLogout = { appState = UserState.NotLogged }
                )
            }
            is UserState.Admin -> {
                AdminDashboardScreen(
                    onLogout = { appState = UserState.NotLogged }
                )
            }
        }
    }
}