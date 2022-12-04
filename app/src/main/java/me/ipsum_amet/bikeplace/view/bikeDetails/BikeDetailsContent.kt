package me.ipsum_amet.bikeplace.view.bikeDetails

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import me.ipsum_amet.bikeplace.R
import me.ipsum_amet.bikeplace.util.*
import me.ipsum_amet.bikeplace.components.ProgressBox
import me.ipsum_amet.bikeplace.data.model.Bike
import me.ipsum_amet.bikeplace.data.model.CONDITION
import me.ipsum_amet.bikeplace.data.model.TYPE
import me.ipsum_amet.bikeplace.ui.theme.BikePlaceTheme

@Composable
fun BikeDetailsContent(
    bike: Bike,
    hoursToLease: String,
    onHoursToLeaseClicked: (String) -> Unit,
    totalPrice: Double
) {
    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Card(
            shape = RectangleShape,
            elevation = BIKE_CARD_ELEVATION,
            border = BorderStroke(width = BIKE_CARD_WIDTH, color = Color.Gray),
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
                            .height(BIKE_DETAILS_IMAGE_HEIGHT)
                            .fillMaxWidth(),
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }
        bike.name?.let {
            Text(
                text = it,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.h6,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                )
        }
        BikeChipGroup(bike = bike, modifier = Modifier.wrapContentSize())
        Column(
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Text(
                text = "Description ",
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start,
            )

            bike.description?.let {
                Text(
                    text = it,
                    maxLines = 7,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Start
                )
            }
        }
        LeaseSection(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            hoursToLease = hoursToLease,
            onHoursToLeaseClicked = onHoursToLeaseClicked
        )
        BottomDetailsCheckout(totalPrice = totalPrice, modifier = Modifier.fillMaxWidth())
    }
}

@Composable
fun BikeChip(modifier: Modifier = Modifier, bikeXteristic: String) {
    Surface(
        color = MaterialTheme.colors.surface,
        contentColor = MaterialTheme.colors.onSurface,
        shape = CircleShape,
        border = BorderStroke(
            width = CHIP_WIDTH,
            color = Color.LightGray
        ),
        modifier = modifier
    ) {
        Text(
            text = bikeXteristic,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.body2,
            modifier = Modifier.padding(M_PADDING),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun BikeChipTopRow(
    bike: Bike,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = modifier
    ) {
        bike.condition?.name?.let { BikeChip(bikeXteristic = it) }
        bike.type?.name?.let { BikeChip(bikeXteristic = it) }
    }
}

@Composable
fun BikeChipGroup(
    bike: Bike,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        BikeChipTopRow(
            bike = bike,
            modifier = Modifier
                .padding(bottom = L_PADDING)
                .fillMaxWidth()
        )
        BikeChip(
            bikeXteristic = "KES ${bike.price}/hr",
        )
    }
}

@Composable
fun BottomDetailsCheckout(
    totalPrice: Double ,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Column() {
            Text(
                text = "Total: ",
                color = Color.Red,
                fontWeight = FontWeight.ExtraBold
            )
            Text(text = "KES $totalPrice", fontWeight = FontWeight.Bold, )
        }
        Button(onClick = {}) {
            Text(text = "PAY")
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun LeaseSection(
    hoursToLease: String,
    onHoursToLeaseClicked: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
    ) {
        val isClicked = false
        Text(
            text = "Lease For ",
            modifier = Modifier
                .weight(3f, true)
        )
        Surface(
            modifier = Modifier
                .padding(end = M_PADDING, start = M_PADDING)
                .weight(3f, fill = false),
            shape = RoundedCornerShape(percent = 50),
            border = BorderStroke(
                width = BIKE_CARD_WIDTH,
                color = when{
                    isClicked -> MaterialTheme.colors.primary
                    else -> MaterialTheme.colors.primaryVariant
                }),
            onClick = {}
        ) {
            TextField(
                singleLine = true,
                value = hoursToLease,
                onValueChange = onHoursToLeaseClicked,
                modifier = Modifier
                    .wrapContentSize()
            )
        }
        Text(text = "Hour(s)", modifier = Modifier.weight(4f))
    }
}

@Preview(name = "BikeDetailsContent", showSystemUi = true, showBackground = true)
@Composable
fun PBikeDetailsContent() {
    BikePlaceTheme() {
        val hoursToLease = "4"
        val bike = Bike(
            bikeId = "dkdferpep",
            name = "Beijing Classic",
            price = 70.0,
            imageUrl = "https://images.unsplash.com/photo-1532298229144-0ec0c57515c7?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=822&q=80",
            description = "skkkkkkkkk" +
                    "kkkkkkkkk" +
                    "kkkkkkaoo" +
                    "widodw" +
                    "wwwwwwwwoqwiiuyuuuyr" +
                    "ere5555555555555jkjjjyyyyy" +
                    "yyyyrwrtttttttt" +"kkkkkkkkk" +
                    "kkkkkkaoo" +
                    "widodw" +
                    "wwwwwwwwoqwiiuyuuuyr" +
                    "ere5555555555555jkjjjyyyyy" +"kkkkkkaoo" +
                    "widodw" +
                    "wwwwwwwwoqwiiuyuuuyr" +
                    "ere5555555555555jkjjjyyyyy" +
                    "yyyyrwrtttttttt" +"kkkkkkkkk" +
                    "kkkkkkaoo" +
                    "widodw" +
                    "wwwwwwwwoqwiiuyuuuyr" +
                    "ere5555555555555jkjjjyyyyy" +
                    "yyyyrwrtttttttt" +
                    "ttttttttttttte",
            isBooked = false,
            condition = CONDITION.EXCELLENT,
            type = TYPE.MOUNTAIN_BIKE,
        )
        BikeDetailsContent(
            bike = bike,
            hoursToLease = hoursToLease,
            totalPrice = 90.0,
            onHoursToLeaseClicked = {}
        )
    }
}
