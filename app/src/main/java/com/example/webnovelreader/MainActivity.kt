package com.example.webnovelreader

import android.graphics.Bitmap
import android.os.Bundle
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.example.webnovelreader.ui.theme.WebNovelReaderTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WebNovelReaderTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    CustomWebView(modifier, "https://www.uukanshu.net/b/33939/109401.html#gsc.tab=0")
}

@Composable
fun CustomWebView(
    modifier: Modifier,
    url: String,
) {

    var backEnabled by remember { mutableStateOf(false) }
    var webView: WebView? = null
    var title = rememberSaveable {
        mutableStateOf("")
    }
    Column {
        Text(text = " Title = " + title.value)

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
                        }

                        override fun onPageFinished(view: WebView?, url: String?) {
                            title.value = view?.title?:""
                        }
                    }
                    settings.javaScriptEnabled = false

                    loadUrl(url)
                    webView = this
                }
            }, update = {
                webView = it
            })
    }

    BackHandler(enabled = backEnabled) {
        webView?.goBack()
    }


}
