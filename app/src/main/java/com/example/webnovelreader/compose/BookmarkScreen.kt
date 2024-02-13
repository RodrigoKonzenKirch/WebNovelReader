package com.example.webnovelreader.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.webnovelreader.R
import com.example.webnovelreader.data.Bookmark
import com.example.webnovelreader.viewmodels.BookmarkScreenViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookmarkScreen(
    modifier: Modifier,
    onNavigateBack: () -> Unit
) {
    val viewModel = hiltViewModel<BookmarkScreenViewModel>()

    val allBookmarks = viewModel.allBookmarksList.collectAsStateWithLifecycle()
    val snackbarHostState = remember{ SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.title_bookmarks)) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack ) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = stringResource(R.string.button_back))
                    }
                }
            )
        },
        snackbarHost = {
            SnackbarHost( hostState = snackbarHostState )
        }
    ) { paddingValues ->
        ListOfBookmarks(
            modifier.padding(paddingValues),
            allBookmarks.value,
            onDeleteItem = {
                viewModel.deleteBookmark(it)
            },
            onSelectBookmark = {
                viewModel.saveCurrentUrl(it)
                scope.launch {
                    snackbarHostState.showSnackbar("Url selected!")
                }
            }
        )
    }
}

@Composable
fun ListOfBookmarks(
    modifier: Modifier,
    bookmarks: List<Bookmark>,
    onDeleteItem: (bookmark: Bookmark) -> Unit,
    onSelectBookmark: (url: String) -> Unit,
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ){
        items(bookmarks) { bookmark ->
            Card(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.padding(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .clickable { onSelectBookmark(bookmark.url) },

                    ) {
                        Text(
                            modifier = Modifier.padding(4.dp),
                            text = bookmark.name
                        )
                        Text(
                            modifier = Modifier.padding(vertical = 4.dp, horizontal = 16.dp),
                            text = bookmark.url
                        )
                    }
                    IconButton(onClick = { onDeleteItem(bookmark) }) {
                        Icon(Icons.Filled.Delete, contentDescription = stringResource(R.string.icon_delete_bookmark))
                    }
                }
            }

        }
    }
}
