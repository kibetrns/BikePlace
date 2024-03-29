package me.ipsum_amet.bikeplace.view.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.ipsum_amet.bikeplace.R
import me.ipsum_amet.bikeplace.ui.theme.BikePlaceTheme

@Composable
fun EmptyContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(id = R.drawable.dissatisfied),
            contentDescription = stringResource(
                id = R.string.empty_content
            ),
            modifier = Modifier
                .size(120.dp)
        )
        Text(
            text = stringResource(id = R.string.empty_content),
            color = Color.LightGray,
            fontWeight = FontWeight.Bold,
            fontSize = MaterialTheme.typography.h5.fontSize
        )
    }
}
@Preview("Empty Content", showBackground = true)
@Composable
fun PEmptyContent() {
    BikePlaceTheme() {
        EmptyContent()
    }
}