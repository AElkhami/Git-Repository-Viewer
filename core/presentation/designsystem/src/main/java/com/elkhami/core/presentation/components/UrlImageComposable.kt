package com.elkhami.core.presentation.components

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.transform.RoundedCornersTransformation
import com.elkhami.core.presentation.components.ComposableConstants.CURVE_RADIUS
import com.elkhami.core.presentation.designsystem.R
import org.koin.compose.koinInject

@Composable
fun UrlImage(
    modifier: Modifier = Modifier,
    imageUrl: String,
    imageSize: Dp,
    imageLoader: ImageLoader = koinInject()
) {
    val context = LocalContext.current

    val request = remember(imageUrl) { // only builds when URL changes
        ImageRequest.Builder(context)
            .data(imageUrl)
            .transformations(RoundedCornersTransformation(CURVE_RADIUS))
            .build()
    }

    AsyncImage(
        model = request,
        imageLoader = imageLoader,
        contentDescription = null,
        modifier = modifier.size(imageSize),
        contentScale = ContentScale.Crop,
        placeholder = painterResource(id = R.drawable.place_holder),
        error = painterResource(id = R.drawable.place_holder)
    )
}