package com.elkhami.repoviewer.presentation.repodetails


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.elkhami.core.presentation.components.LabeledText
import com.elkhami.core.presentation.components.TopBarComposable
import com.elkhami.core.presentation.components.UrlImage
import com.elkhami.core.presentation.components.WebButton
import com.elkhami.core.presentation.designsystem.LocalDimensions
import com.elkhami.core.presentation.designsystem.LocalPadding
import com.elkhami.core.presentation.designsystem.Padding
import com.elkhami.core.presentation.designsystem.RepoviewerTheme
import com.elkhami.repoviewer.presentation.R
import com.elkhami.repoviewer.presentation.model.GitRepoUiModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch

@Destination
@Composable
fun RepoDetailsScreenRoot(
    gitRepo: GitRepoUiModel,
    navigator: DestinationsNavigator
) {
    RepoDetailsScreen(
        repoModel = gitRepo,
        onBackClick = {
            navigator.popBackStack()
        }
    )
}

@Composable
fun RepoDetailsScreen(
    modifier: Modifier = Modifier,
    repoModel: GitRepoUiModel,
    onBackClick: () -> Unit
) {
    val padding = LocalPadding.current
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        topBar = {
            TopBarComposable(
                padding = padding,
                name = repoModel.name.orEmpty(),
                onBackClick = { onBackClick() }
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            DetailsComposable(
                padding = padding,
                imageUrl = repoModel.ownerAvatarUrl.orEmpty(),
                fullName = repoModel.fullName.orEmpty(),
                description = repoModel.description.orEmpty()
            )
            Spacer(modifier = Modifier.weight(1f))
            BottomSectionComposable(
                padding = padding,
                isPrivate = repoModel.isPrivate.toString(),
                visibility = repoModel.visibility.orEmpty(),
                htmlUrl = repoModel.htmlUrl,
                snackbarHostState = snackbarHostState
            )
        }
    }
}

@Composable
fun DetailsComposable(
    modifier: Modifier = Modifier,
    padding: Padding,
    fullName: String,
    description: String,
    imageUrl: String
) {
    val dimensions = LocalDimensions.current
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = padding.mediumPadding),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        UrlImage(
            imageUrl = imageUrl,
            imageSize = dimensions.imageSizeLarge
        )
        Spacer(modifier = Modifier.height(padding.mediumPadding))

        Text(
            text = fullName,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary, // visually distinct
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(padding.smallPadding))

        Text(
            text = description,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = padding.smallPadding),
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun BottomSectionComposable(
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState,
    padding: Padding,
    isPrivate: String,
    visibility: String,
    htmlUrl: String?
) {
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(padding.mediumPadding)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = padding.smallPadding),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = visibility.uppercase(),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.labelLarge
            )

            LabeledText(
                label = stringResource(R.string.item_private),
                value = isPrivate
            )
        }

        val noBrowserString = stringResource(R.string.no_browser)

        htmlUrl?.let {
            Spacer(modifier = Modifier.height(padding.mediumPadding))
            WebButton(
                url = htmlUrl,
                onActivityNotFound = {
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar(noBrowserString)
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun RepoDetailsScreenPreview() {
    RepoviewerTheme {
        RepoDetailsScreen(
            repoModel = GitRepoUiModel(
                name = "A very very very very long repository name for testing purposes",
                fullName = "example-user/example-repo",
                description = "This is a sample description to test how the UI handles longer content in the preview.",
                visibility = "public",
                isPrivate = false
            ),
            onBackClick = {}
        )
    }
}