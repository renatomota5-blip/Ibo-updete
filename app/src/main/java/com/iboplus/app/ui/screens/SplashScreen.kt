package com.iboplus.app.ui.screens

import android.os.Handler
import android.os.Looper
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.iboplus.app.R
import com.iboplus.app.ui.navigation.Routes

/**
 * SplashScreen
 * - Exibe logo configurada pelo painel
 * - Mostra fundo (cor/imagem) também configurado pelo painel
 * - Após timeout ou validação do app, navega para Activation ou Home
 */
@Composable
fun SplashScreen(navController: NavController) {
    var ready by remember { mutableStateOf(false) }

    // Simula carregamento inicial (logo, fundo, configs, status de ativação)
    LaunchedEffect(Unit) {
        Handler(Looper.getMainLooper()).postDelayed({
            ready = true
        }, 2500) // 2.5s de splash
    }

    if (ready) {
        // TODO: substituir por checagem real no painel via API getappuser.php
        val isActivated = false
        if (isActivated) {
            navController.navigate(Routes.HOME) {
                popUpTo(Routes.SPLASH) { inclusive = true }
            }
        } else {
            navController.navigate(Routes.ACTIVATION) {
                popUpTo(Routes.SPLASH) { inclusive = true }
            }
        }
    }

    // Layout do splash
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            // Logo
            Image(
                painter = painterResource(id = R.mipmap.ic_launcher), // depois trocar por logo do painel
                contentDescription = "Logo",
                modifier = Modifier
                    .size(160.dp)
                    .padding(bottom = 24.dp),
                contentScale = ContentScale.Fit
            )

            // Texto opcional
            Text(
                text = "IBO Plus",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            // Indicador de progresso
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.primary,
                strokeWidth =
