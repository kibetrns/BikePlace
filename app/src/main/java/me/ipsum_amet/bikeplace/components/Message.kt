package me.ipsum_amet.bikeplace.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import me.ipsum_amet.bikeplace.R

@Composable
fun Message(
    message: String
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(id = R.drawable.error),
            contentDescription = stringResource(
                id = R.string.empty_content
            ),
            modifier = Modifier
                .size(120.dp)
        )
        Text(
            text = message,
            color = Color.LightGray,
            fontWeight = FontWeight.Bold,
            fontSize = MaterialTheme.typography.h5.fontSize,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun MessageRow(
    message: String,
    painter: Painter
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        Image(
            painter = painter,
            contentDescription = stringResource(id = R.string.error_row_message_icon)
        )
        Text(text = message)
    }
}