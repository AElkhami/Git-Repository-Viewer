package com.elkhami.core.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.elkhami.core.presentation.designsystem.LocalDimensions
import com.elkhami.core.presentation.designsystem.Padding
import com.elkhami.core.presentation.designsystem.R


@Composable
fun TopBarComposable(
    modifier: Modifier = Modifier,
    padding: Padding,
    name: String,
    onBackClick: (() -> Unit)? = null
) {
    val dimensions = LocalDimensions.current

    Box(
        modifier = modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .height(dimensions.topBarHeight)
            .padding(horizontal = padding.mediumPadding)
    ) {
        if (onBackClick != null) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = stringResource(R.string.button_back),
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .clickable(onClick = onBackClick)
                    .padding(padding.mediumPadding)
            )
        }

        Text(
            text = name,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth()
                .padding(horizontal = if (onBackClick != null) padding.extraLargePadding else 0.dp),
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}
