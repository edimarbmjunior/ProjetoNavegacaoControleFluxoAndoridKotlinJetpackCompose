package com.example.projetonavegacaocontrolefluxo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.projetonavegacaocontrolefluxo.data.models.UserState
import com.example.projetonavegacaocontrolefluxo.ui.screen.AdminDashboardScreen
import com.example.projetonavegacaocontrolefluxo.ui.screen.HomeScreen
import com.example.projetonavegacaocontrolefluxo.ui.screen.LoadingScreen
import com.example.projetonavegacaocontrolefluxo.ui.screen.LoginScreen
import com.example.projetonavegacaocontrolefluxo.ui.theme.ProjetoNavegacaoControleFluxoTheme
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