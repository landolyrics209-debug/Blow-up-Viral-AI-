package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.MainViewModel
import com.example.ui.components.BottomNavBar
import com.example.ui.components.NavTab
import com.example.ui.screens.AdminDashboardScreen
import com.example.ui.screens.AuthScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.ProfileScreen
import com.example.ui.screens.ScriptsScreen
import com.example.ui.screens.TrendingScreen
import com.example.ui.theme.BlowUpViralTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BlowUpViralTheme {
                BlowUpViralApp()
            }
        }
    }
}

@Composable
fun BlowUpViralApp(mainViewModel: MainViewModel = viewModel()) {
    val currentUser by mainViewModel.currentUser.collectAsState()
    var selectedTab by remember { mutableStateOf(NavTab.HOME) }

    if (currentUser == null) {
        AuthScreen(
            viewModel = mainViewModel,
            onLoginSuccess = { selectedTab = NavTab.HOME }
        )
    } else {
        val isSuperAdmin = currentUser?.isSuperAdmin == true

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            bottomBar = {
                BottomNavBar(
                    selectedTab = selectedTab,
                    onTabSelected = { tab -> selectedTab = tab },
                    showAdminTab = isSuperAdmin
                )
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                when (selectedTab) {
                    NavTab.HOME -> HomeScreen(viewModel = mainViewModel)
                    NavTab.TRENDS -> TrendingScreen(
                        viewModel = mainViewModel,
                        onNavigateToHome = { selectedTab = NavTab.HOME }
                    )
                    NavTab.SCRIPTS -> ScriptsScreen(viewModel = mainViewModel)
                    NavTab.PROFILE -> ProfileScreen(viewModel = mainViewModel)
                    NavTab.ADMIN -> {
                        if (isSuperAdmin) {
                            AdminDashboardScreen(viewModel = mainViewModel)
                        } else {
                            HomeScreen(viewModel = mainViewModel)
                        }
                    }
                }
            }
        }
    }
}

