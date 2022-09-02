package me.ipsum_amet.bikeplace.view.bike

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import me.ipsum_amet.bikeplace.ui.theme.BikePlaceTheme
import me.ipsum_amet.bikeplace.R
import me.ipsum_amet.bikeplace.Util.*
import me.ipsum_amet.bikeplace.components.ConditionDropDown
import me.ipsum_amet.bikeplace.components.TypeDropDown
import me.ipsum_amet.bikeplace.data.model.CONDITION
import me.ipsum_amet.bikeplace.data.model.TYPE


@Composable
fun BikeContent(
    name: String,
    onTitleChange:(String) -> Unit,
    description: String,
    onDescriptionChange:(String) -> Unit,
    type: TYPE,
    onTypeSelected:(TYPE) -> Unit,
    condition: CONDITION,
    onConditionSelected: (CONDITION) -> Unit,
    price: String,
    onPriceChange: (String) -> Unit,
    imageUrl: String?,
    onImageClicked: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(MaterialTheme.colors.background)
            .padding(L_PADDING)


    ) {
        OutlinedTextField(
            value = name,
            onValueChange = { onTitleChange(it) },
            label = { Text(text = stringResource(id = R.string.enter_bike_name)) },
            placeholder = { Text(stringResource(id = R.string.bike_name)) },
            textStyle = MaterialTheme.typography.body1,
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(M_PADDING))
        TypeDropDown(type = type, onTypeSelected = { onTypeSelected(it) })
        Spacer(modifier = Modifier.height(M_PADDING))
        ConditionDropDown(condition = condition, onConditionSelected = { onConditionSelected(it) })
        Spacer(modifier = Modifier.height(M_PADDING))
        OutlinedTextField(
            value = price,
            onValueChange = { onPriceChange(it) },
            label = { Text(stringResource(id = R.string.enter_price)) },
            singleLine = true,
            placeholder = { Text(stringResource(id = R.string.Price)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(M_PADDING))
        BrowseBikeImage(imageUrl = imageUrl, modifier = Modifier, onImageClicked = onImageClicked)
        Spacer(modifier = Modifier.height(M_PADDING))
        OutlinedTextField(
            value = description,
            onValueChange = { onDescriptionChange(it) },
            label = { Text(stringResource(id = R.string.enter_description)) },
            placeholder = { Text(text = stringResource(id = R.string.description)) },
            textStyle = MaterialTheme.typography.body1,
            modifier = Modifier
                .fillMaxSize()
        )
    }
}

@Composable
fun BrowseBikeImage(imageUrl: String?, modifier: Modifier, onImageClicked: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Browse Bike Image: ",
            modifier = Modifier.weight(2f)
        )
         BikeImage(
             imageUrl = imageUrl,
             modifier = modifier.weight(8f),
             onImageClicked = onImageClicked
         )
    }

}

@Composable
fun BikeImage(
    imageUrl: String?,
    modifier: Modifier,
    onImageClicked: () -> Unit
) {
    Box(
        modifier = modifier
            .padding(top = M_PADDING)
            .clickable { onImageClicked.invoke() }
    ) {
        ImageCard(
            model = imageUrl,
            modifier = Modifier
                .padding(M_PADDING)
                .height(BIkE_CARD_IMAGE_HEIGHT)
                .fillMaxWidth()
        )
        Card(
            border = BorderStroke(width = 1.dp, color = MaterialTheme.colors.onPrimary),
            modifier = Modifier
                .size(XL_SIZE)
                .align(Alignment.TopEnd)
                .padding(top = S_PADDING, end = S_PADDING)
        ) {
            Image(
                painter = painterResource(id = R.drawable.add),
                contentDescription = stringResource(id = R.string.add_photo),
            )

            
        }


    }



}

@Composable
fun CommonImage(
    model: String?,
    modifier: Modifier = Modifier.wrapContentSize(),
    contentScale: ContentScale = ContentScale.Inside
) {
    val painter =  rememberAsyncImagePainter(model = model)
    Image(
        painter = painter,
        contentDescription = stringResource(id = R.string.bike_image),
        modifier = modifier,
        contentScale = contentScale
    )
    if (painter.state is AsyncImagePainter.State.Loading) {
        CircularProgressIndicator()
    }
}

@Composable
fun ImageCard(
    model: String?,
    modifier: Modifier
) {

    Card(modifier = modifier) {
        if (model.isNullOrBlank()) {
            Image(
                painter = painterResource(id = R.drawable.image),
                contentDescription = stringResource(
                    id = R.string.image_placeholder
                ),
                colorFilter = ColorFilter.tint(Color.LightGray),
                contentScale = ContentScale.FillBounds
            )
        } else {
            CommonImage(model = model)
        }
        
    }
}




@Preview("BikeContent")
@Composable
fun PBikeContent() {
    BikePlaceTheme() {
        BikeContent(
            name = "Beijing Classic",
            onTitleChange = {},
            description = "",
            onDescriptionChange = {},
            type = TYPE.MOUNTAIN_BIKE,
            onTypeSelected = {},
            condition = CONDITION.EXCELLENT,
            onConditionSelected = {},
            price = "70.00",
            onPriceChange = {},
            imageUrl = ""
        ) {}

    }
}