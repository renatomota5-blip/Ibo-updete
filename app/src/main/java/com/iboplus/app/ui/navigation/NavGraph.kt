package com.iboplus.app.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.iboplus.app.ui.screens.SplashScreen
import com.iboplus.app.ui.screens.ActivationScreen
import com.iboplus.app.ui.screens.HomeScreen

/**
 * Controla a navegação principal do aplicativo.
 * Fluxo:
 *  - SplashScreen → verifica configs do painel e status do app
 *  - Se não ativado → ActivationScreen
 *  - Se ativado e com lista → HomeScreen
 */
@Composable
fun NavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Routes.SPLASH,
        modifier = modifier
    ) {
        composable(Routes.SPLASH) {
            SplashScreen(navController)
        }
        composable(Routes.ACTIVATION) {
            ActivationScreen(navController)
        }
        composable(Routes.HOME) {
            HomeScreen(navController)
        }
    }
}

/**
 * Definição de rotas usadas na navegação.
 */
object Routes {
    const val SPLASH = "splash"
    const val ACTIVATION = "activation"
    const val HOME = "home"
}
