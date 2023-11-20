package com.shepherd.employee.ui.screens.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.shepherd.employee.R

@Composable
fun LoadImageFromUrl(imageUrl: String) {
    Box(modifier = Modifier.size(50.dp)) {
        Image(
            painter = rememberImagePainter(
                data = imageUrl,
                builder = {
                    transformations(CircleCropTransformation())
                    placeholder(R.drawable.baseline_person_24)
                    error(R.drawable.baseline_person_24)
                },
            ),
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            contentDescription = "Image",
        )
    }
}

@Preview
@Composable
fun PreviewLoadImageFromUrl() {
    LoadImageFromUrl(imageUrl = "https://reqres.in/img/faces/1-image.jpg")
}
