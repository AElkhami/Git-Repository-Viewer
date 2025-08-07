package com.elkhami.core.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T : Any> PullToRefreshPaginatedLazyColumn(
    modifier: Modifier = Modifier,
    listState: LazyListState,
    lazyPagingItems: LazyPagingItems<T>,
    listItemContent: @Composable (T, Int) -> Unit,
    key: ((index: Int) -> Any)?,
    refreshAction: () -> Unit
) {
    val pullToRefreshState = rememberPullToRefreshState()
    var isRefreshing by remember { mutableStateOf(false) }

    // Track load state to reflect proper refresh state
    LaunchedEffect(lazyPagingItems.loadState.refresh) {
        isRefreshing = lazyPagingItems.loadState.refresh is LoadState.Loading
    }

    PullToRefreshBox(
        modifier = modifier,
        isRefreshing = isRefreshing,
        onRefresh = refreshAction,
        state = pullToRefreshState
    ) {
        LazyColumn(
            modifier = Modifier,
            state = listState
        ) {
            items(
                count = lazyPagingItems.itemCount,
                key = key
            ) { index ->
                lazyPagingItems[index]?.let {
                    listItemContent(it, index)
                }
            }
            item {
                if (lazyPagingItems.loadState.append is LoadState.Loading) {
                    Box(modifier = Modifier.fillParentMaxWidth()) {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }
                }
            }
        }
    }
}