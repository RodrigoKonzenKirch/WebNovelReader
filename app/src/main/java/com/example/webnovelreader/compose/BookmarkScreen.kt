package com.example.webnovelreader.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.webnovelreader.data.Bookmark
import com.example.webnovelreader.viewmodels.BookmarkScreenViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookmarkScreen(
    modifier: Modifier,
    onNavigateBack: () -> Unit
) {
    val viewModel = hiltViewModel<BookmarkScreenViewModel>()

    val allBookmarks = viewModel.allBookmarksList.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Bookmarks") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack ) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        ListOfBookmarks(
            modifier.padding(paddingValues),
            allBookmarks.value
        )
    }
}

@Composable
fun ListOfBookmarks(
    modifier: Modifier,
    bookmarks: List<Bookmark>,
) {
    LazyColumn(modifier){
        items(bookmarks) { bookmark ->
            Column {
                Text(text = bookmark.name)
                Text(text = bookmark.url)
            }

        }
    }
}
