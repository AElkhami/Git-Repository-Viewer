package com.elkhami.repoviewer.presentation.repolist

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.paging.PagingData
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.elkhami.core.presentation.designsystem.RepoviewerTheme
import com.elkhami.repoviewer.presentation.model.GitRepoUiModel
import kotlinx.coroutines.flow.flowOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RepoListScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val testRepos = listOf(
        GitRepoUiModel(
            id = 1,
            name = "test-repo-1",
            fullName = "test-user/test-repo-1",
            description = "Test repository 1",
            isPrivate = false,
            ownerAvatarUrl = "",
            htmlUrl = null,
            visibility = "public"
        ),
        GitRepoUiModel(
            id = 2,
            name = "test-repo-2",
            fullName = "test-user/test-repo-2",
            description = "Test repository 2",
            isPrivate = true,
            ownerAvatarUrl = "",
            htmlUrl = null,
            visibility = "private"
        )
    )

    @Test
    fun repoListScreen_displaysRepositoryList() {
        // Given
        val pagingData = flowOf(PagingData.from(testRepos))

        // When
        composeTestRule.setContent {
            RepoviewerTheme {
                RepoListScreen(
                    pagingData = pagingData,
                    onAction = { action -> }
                )
            }
        }

        // Then
        composeTestRule.onNodeWithText("Repository Viewer").assertExists()
        composeTestRule.onNodeWithText("test-repo-1").assertExists()
        composeTestRule.onNodeWithText("test-repo-2").assertExists()
    }

    @Test
    fun repoListScreen_repoItemClick_triggersAction() {
        // Given
        val pagingData = flowOf(PagingData.from(testRepos))
        var capturedAction: RepoListAction? = null

        // When
        composeTestRule.setContent {
            RepoviewerTheme {
                RepoListScreen(
                    pagingData = pagingData,
                    onAction = { action -> capturedAction = action }
                )
            }
        }

        // Then
        composeTestRule.onNodeWithText("test-repo-1").performClick()
        
        assert(capturedAction is RepoListAction.OnRepoClick)
        val clickAction = capturedAction as RepoListAction.OnRepoClick
        assert(clickAction.gitRepoModel.id == 1)
        assert(clickAction.gitRepoModel.name == "test-repo-1")
    }

    @Test
    fun repoListScreen_showsTopBar() {
        // Given
        val pagingData = flowOf(PagingData.from(testRepos))

        // When
        composeTestRule.setContent {
            RepoviewerTheme {
                RepoListScreen(
                    pagingData = pagingData,
                    onAction = { action ->  }
                )
            }
        }

        // Then
        composeTestRule.onNodeWithText("Repository Viewer").assertExists()
    }
}