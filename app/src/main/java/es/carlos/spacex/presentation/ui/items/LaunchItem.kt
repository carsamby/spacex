package es.carlos.spacex.presentation.ui.items

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Help
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import es.carlos.spacex.R
import es.carlos.spacex.domain.model.Launch


@Composable
private fun LaunchText(text: String) {
    Text(
        text = text,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
fun LaunchItem(launch: Launch, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .clickable { onClick.invoke() }
            .padding(0.dp, 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(launch.imgUrl)
                .crossfade(true)
                .placeholder(R.drawable.ic_baseline_downloading_24)
                .error(R.drawable.ic_baseline_error_24)
                .build(),
            contentScale = ContentScale.Crop,
            contentDescription = "Launch Image",
            modifier = Modifier
                .clip(CircleShape)
                .padding(8.dp)
                .weight(1.5f)
        )
        Column(
            modifier = Modifier
                .padding(8.dp, 0.dp, 0.dp, 0.dp)
                .weight(2.5f),
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Mission:")
            Text(text = "Date/time:")
            Text(text = "Rocket:")
            Text(text = "Days ${if (launch.isFuture) "from" else "since"} now:")
        }

        Column(
            modifier = Modifier
                .padding(8.dp, 0.dp, 0.dp, 0.dp)
                .weight(3f)
        ) {
            LaunchText(text = launch.name.toString())
            LaunchText(text = launch.date.toString())
            LaunchText(text = launch.rocket?.name + "/" + launch.rocket?.type)
            LaunchText(text = launch.daysLeft.toString())
        }

        Spacer(Modifier.weight(0.1f))


        Icon(
            imageVector = when (launch.success) {
                true -> Icons.Default.Check
                false -> Icons.Default.Cancel
                else -> Icons.Default.Help
            },
            contentDescription = "Launch success",
            modifier = Modifier.padding(16.dp, 0.dp)
        )
    }
}