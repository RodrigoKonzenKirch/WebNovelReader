package com.example.webnovelreader

import androidx.navigation.NavController
import com.example.webnovelreader.AppDestinations.BOOKMARK_ROUTE

object AppDestinations{
    const val MAIN_ROUTE = "main"
    const val BOOKMARK_ROUTE = "bookmark"
}

class NavigationActions(private val navController: NavController) {

    fun navigateToBookmarkScreen() {
        navController.navigate(BOOKMARK_ROUTE)
    }

    fun navigateBack() {
        navController.popBackStack()
    }



}