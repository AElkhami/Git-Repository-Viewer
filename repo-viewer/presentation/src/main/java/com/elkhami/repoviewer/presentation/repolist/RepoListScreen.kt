package com.elkhami.repoviewer.presentation.repolist

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.elkhami.core.presentation.components.PullToRefreshPaginatedLazyColumn
import com.elkhami.core.presentation.components.RepoItem
import com.elkhami.core.presentation.components.ShowLoading
import com.elkhami.core.presentation.components.TopBarComposable
import com.elkhami.core.presentation.designsystem.LocalDimensions
import com.elkhami.core.presentation.designsystem.LocalPadding
import com.elkhami.core.presentation.designsystem.Padding
import com.elkhami.core.presentation.designsystem.RepoviewerTheme
import com.elkhami.core.presentation.extentions.rememberLazyListState
import com.elkhami.core.presentation.ui.asUiText
import com.elkhami.repoviewer.presentation.NetworkMonitor
import com.elkhami.repoviewer.presentation.R
import com.elkhami.repoviewer.presentation.destinations.RepoDetailsScreenRootDestination
import com.elkhami.repoviewer.presentation.model.GitRepoUiModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import org.koin.androidx.compose.koinViewModel

@RootNavGraph(start = true)
@Destination
@Composable
fun RepoListScreenRoot(
    viewModel: RepoListViewModel = koinViewModel(),
    navigator: DestinationsNavigator
) {
    RepoListScreen(
        pagingData = viewModel.gitRepoPagingFlow,
        onAction = { actions ->
            when (actions) {
                is RepoListAction.OnRepoClick -> {
                    navigator.navigate(
                        RepoDetailsScreenRootDestination(gitRepo = actions.gitRepoModel)
                    )
                }
            }
        }
    )
}

@Composable
fun RepoListScreen(
    pagingData: Flow<PagingData<GitRepoUiModel>>,
    onAction: (RepoListAction) -> Unit
) {
    val repoItems = pagingData.collectAsLazyPagingItems()

    val padding = LocalPadding.current
    val context = LocalContext.current

    var shouldRefresh by remember { mutableStateOf(false) }

    HandleNetworkState(
        context = context,
        onReconnect = { shouldRefresh = true }
    )

    LaunchedEffect(shouldRefresh) {
        if (shouldRefresh) {
            repoItems.refresh()
            shouldRefresh = false
        }
    }
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = repoItems.loadState.mediator) {
        val refreshState = repoItems.loadState.mediator?.refresh
        if (refreshState is LoadState.Error) {
            snackbarHostState.showSnackbar(
                refreshState.error.asUiText().asString(context)
            )
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopBarComposable(
                padding = padding,
                name = stringResource(R.string.app_title)
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when (repoItems.loadState.mediator?.refresh) {
                is LoadState.Error -> {
                    Text(
                        text = stringResource(R.string.no_repos_yet),
                        modifier = Modifier
                            .padding(padding.largePadding)
                            .fillMaxWidth(),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                }

                LoadState.Loading -> {
                    ShowLoading(padding)
                }

                else -> {
                    RepoListComposable(repoItems, onAction, padding)
                }
            }
        }
    }
}

@Composable
fun RepoListComposable(
    repoItems: LazyPagingItems<GitRepoUiModel>,
    onAction: (RepoListAction) -> Unit,
    padding: Padding
) {
    val dimensions = LocalDimensions.current
    val listState = repoItems.rememberLazyListState()

    PullToRefreshPaginatedLazyColumn(
        modifier = Modifier.fillMaxSize(),
        listState = listState,
        lazyPagingItems = repoItems,
        refreshAction = {
            repoItems.refresh()
        },
        key = repoItems.itemKey { it.id ?: 0 },
        listItemContent = { repoItem, index ->
            RepoItem(
                modifier = Modifier.clickable {
                    onAction(RepoListAction.OnRepoClick(repoItem))
                },
                name = repoItem.name.orEmpty(),
                imageUrl = repoItem.ownerAvatarUrl.orEmpty(),
                isPrivate = repoItem.isPrivate.toString(),
                visibility = repoItem.visibility.orEmpty()
            )
            if (index < repoItems.itemCount - 1) {
                HorizontalDivider(
                    modifier = Modifier.padding(
                        start = padding.mediumPadding,
                        end = padding.mediumPadding
                    ),
                    color = MaterialTheme.colorScheme.outlineVariant,
                    thickness = dimensions.dividerThickness
                )

            }
        }
    )
}

@Composable
fun HandleNetworkState(
    context: Context,
    onReconnect: () -> Unit
) {
    var isConnected by remember { mutableStateOf(false) }
    var wasPreviouslyOffline by remember { mutableStateOf(false) }

    val networkMonitor = remember {
        NetworkMonitor(context) { isOnline ->
            if (isOnline && wasPreviouslyOffline) {
                onReconnect()
            }
            isConnected = isOnline
            wasPreviouslyOffline = !isOnline
        }
    }

    DisposableEffect(context) {
        networkMonitor.register()
        onDispose {
            networkMonitor.unregister()
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun RepoListScreenPreview() {
    RepoviewerTheme {
        val repos = listOf(
            GitRepoUiModel(1, "Repo 1", "Description for repo 1"),
            GitRepoUiModel(2, "Repo 2", "Description for repo 2"),
            GitRepoUiModel(3, "Repo 3", "Description for repo 3")
        )
        val pagingData = flowOf(PagingData.from(repos))

        RepoListScreen(
            pagingData = pagingData,
            onAction = {}
        )
    }
}