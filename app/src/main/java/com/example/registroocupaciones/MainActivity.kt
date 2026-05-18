package com.example.registroocupaciones

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.navigation.compose.rememberNavController
import com.example.registroocupaciones.presentation.navegation.DrawMenu
import com.example.registroocupaciones.presentation.navigation.RegistroNavHost
import com.example.registroocupaciones.ui.theme.RegistroOcupacionesTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RegistroOcupacionesTheme {
                val navHostController = rememberNavController()
                val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

                DrawMenu(
                    drawerState = drawerState,
                    navHostController = navHostController
                ) {
                    RegistroNavHost(
                        navHostController = navHostController,
                        drawerState = drawerState
                    )
                }
            }
        }
    }
}