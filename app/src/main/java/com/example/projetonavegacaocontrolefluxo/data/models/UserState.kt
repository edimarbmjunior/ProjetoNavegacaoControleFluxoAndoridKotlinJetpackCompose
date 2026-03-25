package com.example.projetonavegacaocontrolefluxo.data.models

sealed interface UserState {
    data object Loading : UserState
    data object NotLogged : UserState
    data object User : UserState
    data object Admin : UserState
}