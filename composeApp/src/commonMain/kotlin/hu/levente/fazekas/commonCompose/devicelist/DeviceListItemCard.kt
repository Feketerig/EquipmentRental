package hu.levente.fazekas.commonCompose.devicelist

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DeviceListItemCard(
    name: String,
    desc: String,
    selected: Boolean,
    onclick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
        shape = RoundedCornerShape(10.dp),
        backgroundColor = if(selected) MaterialTheme.colors.primary else MaterialTheme.colors.surface,
        elevation = 5.dp,
        onClick = onclick
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(
                Modifier
                    .fillMaxHeight()
                    .padding(start = 5.dp)
                    .weight(0.7f)
            ) {
                Text(
                    text = name,
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(end = 5.dp)
                )
                Text(
                    text = desc,
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(end = 5.dp)
                )
            }
        }
    }
}