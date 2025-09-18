package com.iboplus.app.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
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
import kotlinx.coroutines.delay

/**
 * SplashScreen
 * - Mostra logo e fundo
 * - Após timeout navega para Activation ou Home
 */
@Composable
fun SplashScreen(navController: NavController) {
    var ready by remember { mutableStateOf(false) }

    // Simula carregamento inicial (logo, fundo, configs, status de ativação)
    LaunchedEffect(Unit) {
        delay(2500) // 2.5s
        ready = true
    }

    if (ready) {
        // TODO: substituir por checagem real via API
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
                painter = painterResource(id = R.mipmap.ic_launcher),
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
                modifier = Modifier.size(36.dp),
                strokeWidth = 4.dp
            )
        }
    }
}
