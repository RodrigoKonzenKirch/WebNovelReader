package com.example.webnovelreader.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.webnovelreader.data.Bookmark
import com.example.webnovelreader.viewmodels.BookmarkScreenViewModel

@Composable
fun BookmarkScreen(
    modifier: Modifier,
    onNavigateBack: () -> Unit
) {
    val viewModel = hiltViewModel<BookmarkScreenViewModel>()

    val allBookmarks = viewModel.allBookmarksList.collectAsStateWithLifecycle()
    ListOfBookmarks(allBookmarks.value)


}

@Composable
fun ListOfBookmarks(bookmarks: List<Bookmark>) {
    LazyColumn{
        items(bookmarks) { bookmark ->
            Column {
                Text(text = bookmark.name)
                Text(text = bookmark.url)
            }

        }
    }
}
