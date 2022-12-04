package me.ipsum_amet.bikeplace.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import me.ipsum_amet.bikeplace.R
import me.ipsum_amet.bikeplace.util.*
import me.ipsum_amet.bikeplace.data.model.Bike
import me.ipsum_amet.bikeplace.data.model.CONDITION
import me.ipsum_amet.bikeplace.data.model.TYPE
import me.ipsum_amet.bikeplace.ui.theme.BikePlaceTheme

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TCHomeBikeCard(bike: Bike, navigateToBikeScreen:(String) -> Unit) {
    Card(
        shape = RectangleShape,
        border = BorderStroke(width = BIKE_CARD_WIDTH, color = Color.Gray),
        onClick = {
            bike.bikeId?.let { navigateToBikeScreen(it) }
        }
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(M_PADDING)
        ) {
            Card(
                shape = CircleShape,
                modifier = Modifier
                    .size(CIRCULAR_BIKE_SIZE)
                    .align(CenterHorizontally)
            ) {
                SubcomposeAsyncImage(
                    model = bike.imageUrl,
                    contentDescription = stringResource(id = R.string.bike_image),
                ) {
                    val state = painter.state
                    if (state is AsyncImagePainter.State.Loading || state is AsyncImagePainter.State.Error) {
                        ProgressBox()
                    } else {
                        SubcomposeAsyncImageContent(
                            alignment = Alignment.Center,
                            modifier = Modifier
                                .fillMaxSize(),
                            contentScale = ContentScale.FillBounds
                        )
                    }
                }
            }
            bike.name?.let { Text(text = it) }
            Text(text = "Condition: ${bike.condition}")
        }
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CHomeBikeCard(
    bike: Bike,
    onCHomeBikeClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RectangleShape,
        border = BorderStroke(width = BIKE_CARD_WIDTH, color = Color.Gray),
        modifier = modifier,
        onClick = {
            onCHomeBikeClicked()
            }
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = CenterHorizontally,
            modifier = Modifier
                .padding(M_PADDING)
        ) {
            Text(text = "${bike.type}")
            Card(
                shape = CircleShape,
                modifier = Modifier
                    .size(CIRCULAR_BIKE_SIZE)
                    .align(CenterHorizontally)
            ) {
                SubcomposeAsyncImage(
                    model = bike.imageUrl,
                    contentDescription = stringResource(id = R.string.bike_image),
                ) {
                    val state = painter.state
                    if (state is AsyncImagePainter.State.Loading || state is AsyncImagePainter.State.Error) {
                        ProgressBox()
                    } else {
                        SubcomposeAsyncImageContent(
                            alignment = Alignment.Center,
                            modifier = Modifier
                                .fillMaxSize(),
                            contentScale = ContentScale.FillBounds
                        )
                    }
                }
            }
            bike.name?.let { Text(text = it) }
        }
    }
}

@Preview(name = "TCHomeBikeCard", showBackground = true, group = "HomeContent")
@Composable
fun PTCHomeBikeCard() {
    BikePlaceTheme() {
        val bike = Bike(
            bikeId = "dkdferpep",
            name = "Beijing Classic",
            price = 70.0,
            imageUrl = "",
            description = "",
            isBooked = false,
            condition = CONDITION.EXCELLENT,
            type = TYPE.MOUNTAIN_BIKE,
        )
        TCHomeBikeCard(bike, navigateToBikeScreen = {})
    }
}


@Preview(name = "CHomeBikeCard", showBackground = true, group = "HomeContent")
@Composable
fun PCHomeBikeCard() {
    BikePlaceTheme() {
        val bike = Bike(
            bikeId = "dkdferpep",
            name = "Beijing Classic",
            price = 70.0,
            imageUrl = "",
            description = "",
            isBooked = false,
            condition = CONDITION.EXCELLENT,
            type = TYPE.MOUNTAIN_BIKE,
        )
        CHomeBikeCard(
            bike = bike,
            onCHomeBikeClicked = {}
        )
    }
}



