package gradowska.katarzyna.filmsapp.presentation.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavDestination.Companion.hasRoute
import gradowska.katarzyna.filmsapp.presentation.theme.Tolopea
import gradowska.katarzyna.filmsapp.presentation.theme.WineBerry2
import org.koin.compose.KoinContext


@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    KoinContext {
        Scaffold(
            bottomBar = {
                NavigationBar {
                    NavigationBarItem(
                        selected = currentDestination?.hasRoute<MoviesHome>() == true,
                        onClick = {
                            navController.navigate(MoviesHome) {
                                popUpTo<MoviesHome> {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = { Icon(Icons.Default.Search, "Search") },
                        label = { Text("Search") },
                        colors = NavigationBarItemDefaults.colors(
                            indicatorColor = androidx.compose.ui.graphics.Color.Transparent,
                            selectedIconColor = Tolopea,
                            unselectedIconColor = WineBerry2,
                            selectedTextColor = Tolopea,
                            unselectedTextColor = WineBerry2
                        )
                    )
                    NavigationBarItem(
                        selected = currentDestination?.hasRoute<MoviesFilter>() == true,
                        onClick = {
                            navController.navigate(MoviesFilter) {
                                popUpTo<MoviesHome> {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = { Icon(Icons.Default.Favorite, "Filters") },
                        label = { Text("Filters") },
                        colors = NavigationBarItemDefaults.colors(
                            indicatorColor = androidx.compose.ui.graphics.Color.Transparent,
                            selectedIconColor = Tolopea,
                            unselectedIconColor = WineBerry2,
                            selectedTextColor = Tolopea,
                            unselectedTextColor = WineBerry2
                        )
                    )
                }
            }
        ) { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                MainNavGraph(navController = navController)
            }
        }

    }
}