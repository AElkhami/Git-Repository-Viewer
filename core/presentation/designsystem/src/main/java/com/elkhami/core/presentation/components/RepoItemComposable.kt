package com.elkhami.core.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.elkhami.core.presentation.designsystem.LocalDimensions
import com.elkhami.core.presentation.designsystem.LocalPadding
import com.elkhami.core.presentation.designsystem.R

@Composable
fun RepoItem(
    modifier: Modifier = Modifier,
    imageUrl: String,
    name: String,
    isPrivate: String,
    visibility: String
) {
    val dimensions = LocalDimensions.current
    val padding = LocalPadding.current

    Column(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(padding.mediumPadding),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(padding.mediumPadding)
        ) {
            UrlImage(
                imageUrl = imageUrl,
                imageSize = dimensions.imageSize
            )

            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = name,
                        color = MaterialTheme.colorScheme.primary, // 🔵 more visually appealing
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f),
                        style = MaterialTheme.typography.titleSmall // 🔠 stronger title
                    )

                    Text(
                        text = visibility.uppercase(),
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.labelLarge
                    )
                }

                Spacer(modifier = Modifier.height(padding.smallPadding))

                LabeledText(
                    label = stringResource(R.string.item_private),
                    value = isPrivate
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun RepoItemPreview() {
    com.elkhami.core.presentation.designsystem.RepoviewerTheme {
        RepoItem(
            name = "terraform-aws-fargate, that's a very very long repository name for demo purposes",
            isPrivate = "false",
            visibility = "public",
            imageUrl = ""
        )
    }
}

