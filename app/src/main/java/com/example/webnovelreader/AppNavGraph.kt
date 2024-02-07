package com.example.webnovelreader

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.webnovelreader.compose.BookmarkScreen
import com.example.webnovelreader.compose.MainScreen

@Composable
fun AppNavGraph(
    modifier: Modifier,
    navHostController: NavHostController,
    startDestination: String = AppDestinations.MAIN_ROUTE,
    navActions: NavigationActions = remember(navHostController) {
        NavigationActions(navHostController)
    }
) {

    NavHost(
        navController = navHostController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(AppDestinations.MAIN_ROUTE) {
            MainScreen(
                modifier = modifier,
                onNavigateToBookmark = { navActions.navigateToBookmarkScreen() }
            )
        }

        composable(AppDestinations.BOOKMARK_ROUTE) {
            BookmarkScreen(
                modifier = modifier,
                onNavigateBack = { navActions.navigateBack() }
            )
        }
    }
}