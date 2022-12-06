package me.ipsum_amet.bikeplace.view.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import me.ipsum_amet.bikeplace.R
import me.ipsum_amet.bikeplace.util.*
import me.ipsum_amet.bikeplace.components.*
import me.ipsum_amet.bikeplace.data.model.Bike
import me.ipsum_amet.bikeplace.data.model.CONDITION
import me.ipsum_amet.bikeplace.data.model.TYPE
import me.ipsum_amet.bikeplace.data.model.User
import java.util.*

@Composable
fun HomeContent(
    user: User,
    modifier: Modifier = Modifier,
    topChoiceBikes: RequestState<List<Bike>>,
    allBikeCategories: RequestState<List<Bike>>,
    onCHomeBikeClicked: (String) -> Unit,
    navigateToTopChoiceBikes: () -> Unit,
    navigateToBikeDetailsScreen: (bikeId: String) -> Unit,
) {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.SpaceEvenly,
        contentPadding = PaddingValues(bottom = XXXL_PADDING, start = S_PADDING)

    ) {
        item {
            Text(
                text = "Hello \uD83D\uDC4B\uD83C\uDFFF ${user.fullName?.take(7)}...",
                fontWeight = FontWeight.SemiBold,
                fontFamily = FontFamily.Serif
            )
        }
        item {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Top Choices",
                    fontWeight = FontWeight.Bold,
                )
                TextButton(onClick = { navigateToTopChoiceBikes() }) {
                    Text(
                        text = "show more",
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(vertical = L_PADDING)
                    )
                }
            }
        }
        item {
            when (topChoiceBikes) {
                RequestState.Loading -> {
                    ProgressBox()
                }
                RequestState.Idle -> {
                    ProgressBox()
                }
                else -> {
                    if (topChoiceBikes is RequestState.Success) {
                        HandleDisplayTopChoicesHomeContent(
                            bikes = topChoiceBikes.data,
                            navigateToBikeScreen = navigateToBikeDetailsScreen
                        )
                    }
                }
            }
        }
        item {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Categories",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(L_PADDING)
                )
            }
        }
        item {
            when (allBikeCategories) {
                RequestState.Loading -> {
                    ProgressBox()
                }
                RequestState.Idle -> {
                    ProgressBox()
                }
                else -> {
                    if (allBikeCategories is RequestState.Success) {
                        HandleDisplayCategoriesHomeContent(
                            bikes = allBikeCategories.data,
                            modifier = modifier,
                            onCHomeBikeClicked = onCHomeBikeClicked
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun HandleDisplayTopChoicesHomeContent(
    bikes: List<Bike>,
    navigateToBikeScreen: (bikeId: String) -> Unit
) {
    if ( bikes.isEmpty() )
        MessageRow(
            message = "Bikes with the characteristics are not yet uploaded",
            painter = painterResource(
                id = R.drawable.terrain
            )
        )
     else
        DisplayTopChoicesHomeContent(bikes = bikes, navigateToBikeScreen = navigateToBikeScreen)
}@Composable
fun HandleDisplayCategoriesHomeContent(
    bikes: List<Bike>,
    modifier: Modifier = Modifier,
    onCHomeBikeClicked: (String) -> Unit,
) {
    if ( bikes.isEmpty() )
        MessageRow(
            message = "Bikes with the characteristics are not yet uploaded",
            painter = painterResource(
                id = R.drawable.terrain
            )
        )
     else
        DisplayCategoriesHomeContent(
            bikes = bikes,
            modifier = modifier,
            onCHomeBikeClicked = onCHomeBikeClicked
        )
}



@Composable
fun DisplayTopChoicesHomeContent(
    bikes: List<Bike>,
    navigateToBikeScreen: (bikeId: String) -> Unit
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(L_PADDING)
    ) {
        items(
            items = bikes,
            key = { bike: Bike ->
                bike::bikeId
            }
        ) { bike: Bike ->
            TCHomeBikeCard(bike, navigateToBikeScreen = navigateToBikeScreen)
        }
    }
}

@Composable
fun DisplayCategoriesHomeContent(
    bikes: List<Bike>,
    modifier: Modifier = Modifier,
    onCHomeBikeClicked: (String) -> Unit
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(L_PADDING)
    ) {
        items(
            items = bikes,
            key = { bike: Bike ->
                bike::bikeId
            }
        ) { bike: Bike ->
            CHomeBikeCard(
                bike = bike,
                onCHomeBikeClicked = onCHomeBikeClicked,
                modifier = modifier
            )
        }
    }
}


@Preview(name = "DisplayTopChoicesHomeContent", group = "HomeContent", showBackground = true)
@Composable
fun PDisplayTopChoicesHomeContent() {
    val bikes = listOf(
        Bike(
            bikeId = UUID.randomUUID().toString(),
            name = "Beijing Classic",
            price = 70.0,
            imageUrl = "",
            description = "",
            isBooked = false,
            condition = CONDITION.EXCELLENT,
            type = TYPE.MOUNTAIN_BIKE,
        ),
        Bike(
            bikeId = UUID.randomUUID().toString(),
            name = "Beijing Classic",
            price = 70.0,
            imageUrl = "",
            description = "",
            isBooked = false,
            condition = CONDITION.EXCELLENT,
            type = TYPE.MOUNTAIN_BIKE,
        ),
        Bike(
            bikeId = UUID.randomUUID().toString(),
            name = "Beijing Classic",
            price = 70.0,
            imageUrl = "",
            description = "",
            isBooked = false,
            condition = CONDITION.EXCELLENT,
            type = TYPE.MOUNTAIN_BIKE,
        ),
        Bike(
            bikeId = UUID.randomUUID().toString(),
            name = "Beijing Classic",
            price = 70.0,
            imageUrl = "",
            description = "",
            isBooked = false,
            condition = CONDITION.EXCELLENT,
            type = TYPE.MOUNTAIN_BIKE,
        )
    )
    DisplayTopChoicesHomeContent(bikes = bikes, navigateToBikeScreen = {})
}

/*
@Preview(name = "DisplayCategoriesHomeContent", group = "HomeContent", showBackground = true)
@Composable
fun PDisplayCategoriesHomeContent() {
    val bikes = listOf(
        Bike(
            bikeId = UUID.randomUUID().toString(),
            name = "Beijing Classic",
            price = 70.0,
            imageUrl = "",
            description = "",
            isBooked = false,
            condition = CONDITION.EXCELLENT,
            type = TYPE.MOUNTAIN_BIKE,
        ),
        Bike(
            bikeId = UUID.randomUUID().toString(),
            name = "Beijing Classic",
            price = 70.0,
            imageUrl = "",
            description = "",
            isBooked = false,
            condition = CONDITION.EXCELLENT,
            type = TYPE.MOUNTAIN_BIKE,
        ),
        Bike(
            bikeId = UUID.randomUUID().toString(),
            name = "Beijing Classic",
            price = 70.0,
            imageUrl = "",
            description = "",
            isBooked = false,
            condition = CONDITION.EXCELLENT,
            type = TYPE.MOUNTAIN_BIKE,
        ),
        Bike(
            bikeId = UUID.randomUUID().toString(),
            name = "Beijing Classic",
            price = 70.0,
            imageUrl = "",
            description = "",
            isBooked = false,
            condition = CONDITION.EXCELLENT,
            type = TYPE.MOUNTAIN_BIKE,
        )
    )
    DisplayCategoriesHomeContent(bikes = bikes, navigateToListCategoryScreen = {})
}
*/

