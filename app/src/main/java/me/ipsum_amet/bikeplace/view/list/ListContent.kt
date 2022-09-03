package me.ipsum_amet.bikeplace.view.list

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.*
import coil.request.ImageRequest
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import me.ipsum_amet.bikeplace.R
import me.ipsum_amet.bikeplace.Util.*
import me.ipsum_amet.bikeplace.components.Message
import me.ipsum_amet.bikeplace.components.ProgressBox
import me.ipsum_amet.bikeplace.data.model.Bike
import me.ipsum_amet.bikeplace.data.model.CONDITION
import me.ipsum_amet.bikeplace.data.model.TYPE
import me.ipsum_amet.bikeplace.ui.theme.BikePlaceTheme

@Composable
fun ListContent(
    allBikes: RequestState<List<Bike>>,
    searchedBikes: RequestState<List<Bike>>,
    searchAppBarState: SearchAppBarState,
    navigateToBikeScreen: (bikeId: String) -> Unit
) {


    if (searchAppBarState == SearchAppBarState.TRIGGERED) {
        if (searchedBikes is RequestState.Success) {
            HandleListContent(
                bikes = searchedBikes.data,
                navigateToBikeScreen = navigateToBikeScreen
            )
        }
    } else {
        when( allBikes ) {
            RequestState.Loading -> {
                ProgressBox()
            }
            RequestState.Idle -> {
                ProgressBox()
            }
            else -> {
                if ( allBikes is RequestState.Success ) {
                    HandleListContent(
                        bikes = allBikes.data,
                        navigateToBikeScreen = navigateToBikeScreen
                    )
                } else if ( allBikes is RequestState.Error ) {
                    Message(message = "Error while Listing Bikes.")
                }
            }
        }
        /*
        if ( allBikes is RequestState.Loading || allBikes is RequestState.Idle) {
            ProgressBox()
        } else if (allBikes is RequestState.Success) {
            HandleListContent(
                bikes = allBikes.data,
                navigateToBikeScreen = navigateToBikeScreen
            )
        } else if ( allBikes is RequestState.Error ) {

        }
        */
    }
}

@Composable
fun HandleListContent(
    bikes: List<Bike>,
    navigateToBikeScreen: (bikeId: String) -> Unit
) {
    if (bikes.isEmpty()) {
        Message(message = "No Bikes Posted with inputted query or No Bikes Posted Yet")
    } else {
        DisplayBikes(
            bikes = bikes,
            navigateToBikeScreen = navigateToBikeScreen
        )
    }


}

@Composable
fun DisplayBikes(
    bikes: List<Bike>,
    navigateToBikeScreen: (bikeId: String) -> Unit
) {

    LazyColumn(
        contentPadding = PaddingValues(3.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        items(
            items = bikes,
            key = { bike: Bike ->
                bike::bikeId
            }
        ) { bike: Bike ->
            BikeItem(
                bike = bike,
                navigateToBikeScreen = navigateToBikeScreen
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BikeItem(
    bike: Bike,
    navigateToBikeScreen: (bikeId: String) -> Unit
) {
    Card(
        shape = RectangleShape,
        elevation = BIKE_CARD_ELEVATION,
        border = BorderStroke(width = BIKE_CARD_WIDTH, color = Color.LightGray),
        onClick = {
            bike.bikeId?.let { navigateToBikeScreen(it) }
        }
    ) {
        Column() {
            Column(modifier = Modifier.fillMaxWidth()) {
                bike.name?.let {
                    Text(
                        text = it,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.h6,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Spacer(modifier = Modifier.height(M_PADDING))
                bike.type?.let {
                    Text(
                        text = it.name,
                        fontWeight = FontWeight.Thin,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
            Spacer(modifier = Modifier.height(M_PADDING))
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
                           .fillMaxWidth()
                           .height(BIkE_CARD_IMAGE_HEIGHT)
                   )
               }
           }
            Spacer(modifier = Modifier.height(M_PADDING))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(text = "Condition: ${bike.condition?.name}")
                Text(text = "KES ${bike.price} /hr", overflow = TextOverflow.Ellipsis)
            }
        }
    }

}

@Preview(name = "Bike Item", showBackground = true)
@Composable
fun PBikeItem() {
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
    BikePlaceTheme {
        BikeItem(bike = bike, navigateToBikeScreen = {})
    }

}