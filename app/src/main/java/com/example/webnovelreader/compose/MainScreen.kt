package com.example.webnovelreader.compose

import android.graphics.Bitmap
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.webnovelreader.R
import com.example.webnovelreader.viewmodels.MainScreenViewModel
import kotlinx.coroutines.launch

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    onNavigateToBookmark: () -> Unit
) {
    val viewModel = hiltViewModel<MainScreenViewModel>()

    val currentWebPageTitle = rememberSaveable {
        mutableStateOf("")
    }
    val currentUrl = viewModel.currentUrl.collectAsStateWithLifecycle()
    val isMenuVisible = rememberSaveable {
        mutableStateOf(false)
    }

    val snackbarHostState = remember{ SnackbarHostState() }
    val scope = rememberCoroutineScope()
    Scaffold(
        bottomBar = {
            BottomAppBar {
                OutlinedButton(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { isMenuVisible.value = !isMenuVisible.value }
                ) {
                    Icon(Icons.Outlined.Menu, "Menu" )
                }
            }
        },
        snackbarHost = {
            SnackbarHost( hostState = snackbarHostState )
        }
    ) { innerPadding ->
        Column(modifier.padding(innerPadding)) {
            Surface(Modifier.weight(2f)) {
                Column {
                    CustomWebView(
                        modifier,
                        currentUrl,
                        onUpdateTitle = {
                            currentWebPageTitle.value = it
                        },
                        onUpdateCurrentUrl = {
                            viewModel.saveCurrentUrl(it)
                        }
                    )
                }
            }
            if (isMenuVisible.value){
                Surface(Modifier.weight(1f)) {
                    MainScreenMenu(
                        currentWebPageTitle.value, 
                        currentUrl.value,
                        onBookmarkUrl = {
                            viewModel.bookmarkUrl(currentWebPageTitle.value, currentUrl.value)
                            scope.launch {
                                snackbarHostState.showSnackbar("Current Url bookmarked!")
                            }
                        },
                        onUpdateCurrentUrl = {
                            viewModel.saveCurrentUrl(it)
                        },
                        onNavigateToBookmark = onNavigateToBookmark
                    )
                }
            }
        }
    }
}

@Composable
fun CustomWebView(
    modifier: Modifier,
    pageUrl: State<String>,
    onUpdateTitle: (title: String) -> Unit,
    onUpdateCurrentUrl: (title: String) -> Unit,
) {

    var webView: WebView? = null
    var backEnabled by remember { mutableStateOf(false) }

    Column {
        AndroidView(
            modifier = modifier,
            factory = { context ->
                WebView(context).apply {
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                    webViewClient = object : WebViewClient() {
                        override fun onPageStarted(view: WebView, url: String?, favicon: Bitmap?) {
                            backEnabled = view.canGoBack()
                            onUpdateCurrentUrl(url?:"")
                        }

                        override fun onPageFinished(view: WebView?, url: String?) {
                            onUpdateTitle(view?.title?:"")

                        }
                    }
                    settings.javaScriptEnabled = false

                    loadUrl(pageUrl.value)
                    webView = this
                }
            }, update = {
                webView = it
            }
        )
    }

    BackHandler(enabled = backEnabled) {
        webView?.goBack()
    }

}

@Composable
fun MainScreenMenu(
    currentWebPageTitle: String,
    currentUrl: String,
    onBookmarkUrl: () -> Unit,
    onUpdateCurrentUrl: (url: String) -> Unit,
    onNavigateToBookmark: () -> Unit,
){
    val textValue = rememberSaveable {
        mutableStateOf("")
    }
    val basicPadding = 8.dp
    Column {
        Text(
            modifier = Modifier.padding(basicPadding),
            text = " Page Title = $currentWebPageTitle"
        )
        Row(
            modifier = Modifier.padding(basicPadding),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(onClick = onBookmarkUrl ) {
                Icon(Icons.Outlined.Star , "Bookmark this Url" )
            }
            Text(text = " Current UrL = $currentUrl")

        }

        Row(
            modifier = Modifier.padding(basicPadding),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(onClick = { onUpdateCurrentUrl(textValue.value) }) {
                Text(text = "Go here:")
            }
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = textValue.value,
                onValueChange = { textValue.value = it },
                label = { Text(text = "Url") }
            )
        }

        Row(
            modifier = Modifier.padding(basicPadding),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Sites: ")
            Button(onClick = { onUpdateCurrentUrl("https://www.uukanshu.net") }) {
                Text(text = "U看书")
            }
            Button(onClick = { onUpdateCurrentUrl("https://www.hetushu.com") }) {
                Text(text = "和图书")
            }
        }

        Button(onClick = onNavigateToBookmark ) {
            Text(text = stringResource(R.string.button_bookmark_list))
        }

    }
}