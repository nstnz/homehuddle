package com.homehuddle.common.design.specific

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import com.homehuddle.common.design.theme.AppTheme
import com.homehuddle.common.design.theme.textDarkBorder
import dev.icerock.moko.media.Bitmap
import dev.icerock.moko.media.compose.toImageBitmap
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource

@Composable
internal fun TripPhotoComponent(
    size: Dp,
    radius: Dp = AppTheme.indents.x1_5,
    bitmap: Bitmap? = null,
    url: String? = null
) {
    Surface(
        modifier = Modifier.size(size),
        shape = RoundedCornerShape(radius),
        color = AppTheme.colors.textDarkBorder(),
    ) {
        bitmap?.let {
            Image(
                bitmap = it.toImageBitmap(),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
        url?.let {
            val painterResource = asyncPainterResource(it)
            KamelImage(
                resource = painterResource,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
    }
}