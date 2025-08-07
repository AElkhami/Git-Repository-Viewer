package com.elkhami.repoviewer.presentation.repodetails

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.elkhami.core.presentation.designsystem.RepoviewerTheme
import com.elkhami.repoviewer.presentation.model.GitRepoUiModel
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RepoDetailsScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val testRepo = GitRepoUiModel(
        id = 1,
        name = "test-repo",
        fullName = "test-user/test-repo",
        description = "This is a test repository for UI testing purposes",
        isPrivate = false,
        ownerAvatarUrl = "",
        htmlUrl = null,
        visibility = "public"
    )

    @Test
    fun repoDetailsScreen_displaysRepositoryInformation() {
        // When
        composeTestRule.setContent {
            RepoviewerTheme {
                RepoDetailsScreen(
                    repoModel = testRepo,
                    onBackClick = {}
                )
            }
        }

        // Then
        composeTestRule.onNodeWithText("test-repo").assertExists()
        composeTestRule.onNodeWithText("test-user/test-repo").assertExists()
        composeTestRule.onNodeWithText("This is a test repository for UI testing purposes").assertExists()
    }

    @Test
    fun repoDetailsScreen_backButtonClick_triggersCallback() {
        // Given
        var backButtonClicked = false

        // When
        composeTestRule.setContent {
            RepoviewerTheme {
                RepoDetailsScreen(
                    repoModel = testRepo,
                    onBackClick = { backButtonClicked = true }
                )
            }
        }

        // Then
        composeTestRule.onNodeWithContentDescription("Back").performClick()
        assert(backButtonClicked)
    }

    @Test
    fun repoDetailsScreen_webButtonDoesNotExist_whenUrlNotProvided() {
        // When
        composeTestRule.setContent {
            RepoviewerTheme {
                RepoDetailsScreen(
                    repoModel = testRepo,
                    onBackClick = {  }
                )
            }
        }

        // Then
        composeTestRule.onNodeWithText("Open Repository").assertDoesNotExist()
    }
}