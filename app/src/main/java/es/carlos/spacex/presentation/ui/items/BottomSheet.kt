package es.carlos.spacex.presentation.ui.items

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import es.carlos.spacex.R
import es.carlos.spacex.common.Utils
import es.carlos.spacex.common.Utils.LaunchState.*
import es.carlos.spacex.domain.model.Launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterBottomSheet(onClick: (isSuccess: Boolean?, yearSince: Int?, orderType: Utils.OrderType) -> Unit) {

    var yearInput by remember { mutableStateOf("") }
    var isSuccess by remember { mutableStateOf<Boolean?>(null) }
    var selectedIndex by remember { mutableStateOf(0) }

    val radioValues = values()
    var currentSelection = remember { mutableStateOf(NONE) }

    val maxSizeYear = 4

    Column {

        Text(
            text = "Filters",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(16.dp)
        )

        OutlinedTextField(
            value = yearInput,
            singleLine = true,
            onValueChange = {
                if (it.length <= maxSizeYear) yearInput = it
            },
            label = { Text("Year since") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = "Date"
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 0.dp)
        )

        Text(
            text = "${yearInput.length} / $maxSizeYear",
            textAlign = TextAlign.End,
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 24.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "State of launch:",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(16.dp, 0.dp, 0.dp, 0.dp)
        )

        RadioGroup(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            items = radioValues.map { state -> state.desc },
            selection = currentSelection.value.desc
        ) {
            currentSelection.value = when (it) {
                SUCCESS.desc -> {
                    isSuccess = true
                    SUCCESS
                }
                FAILURE.desc -> {
                    isSuccess = false
                    FAILURE
                }
                else -> {
                    isSuccess = null
                    NONE
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Sort by",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(16.dp, 0.dp, 0.dp, 16.dp)
        )

        val cornerRadius = 8.dp

        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {

            Utils.OrderType.values().forEachIndexed { index, item ->
                OutlinedButton(
                    modifier = if (index == 0)
                        Modifier
                            .weight(1F)
                            .padding(16.dp, 0.dp, 0.dp, 0.dp)
                    else
                        Modifier
                            .weight(1F)
                            .padding(0.dp, 0.dp, 16.dp, 0.dp),
                    onClick = { selectedIndex = index },
                    shape = when (index) {
                        // left outer button
                        0 -> RoundedCornerShape(
                            topStart = cornerRadius,
                            topEnd = 0.dp,
                            bottomStart = cornerRadius,
                            bottomEnd = 0.dp
                        )
                        else -> RoundedCornerShape(
                            topStart = 0.dp,
                            topEnd = cornerRadius,
                            bottomStart = 0.dp,
                            bottomEnd = cornerRadius
                        )
                    },
                    border = BorderStroke(
                        1.dp, if (selectedIndex == index) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            Color.DarkGray.copy(alpha = 0.75f)
                        }
                    ),
                    colors = if (selectedIndex == index) {
                        // selected colors
                        ButtonDefaults.outlinedButtonColors(
                            containerColor = MaterialTheme.colorScheme.primary.copy(
                                alpha = 0.3f
                            ), contentColor = MaterialTheme.colorScheme.primary
                        )
                    } else {
                        // not selected colors
                        ButtonDefaults.outlinedButtonColors(
                            containerColor = MaterialTheme.colorScheme.surface,
                            contentColor = MaterialTheme.colorScheme.primary
                        )
                    },
                ) {
                    Text(
                        text = item.desc,
                        color = if (selectedIndex == index) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            Color.DarkGray.copy(alpha = 0.9f)
                        },
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                }
            }

        }

        Button(
            onClick = {
                onClick(
                    isSuccess,
                    if (yearInput.isNotEmpty()) yearInput.toInt() else null,
                    if (selectedIndex == 0) Utils.OrderType.DESC else Utils.OrderType.ASC
                )
            },
            modifier = Modifier
                .align(Alignment.End)
                .padding(16.dp),
        ) {
            Text(text = "Apply", style = MaterialTheme.typography.bodyLarge)
        }

    }
}

class WebLink(val desc: String, val link: String?, val imageResource: Int)

@Composable
fun LinkBottomSheet(launch: Launch?, onClick: (link: String?) -> Unit) {

    val options: List<WebLink> = arrayListOf(
        WebLink("Go to article", launch?.article, R.drawable.ic_baseline_assignment_24),
        WebLink("Go to wikipedia", launch?.wikipedia, R.drawable.ic_baseline_auto_stories_24),
        WebLink("Watch some videos", launch?.webcast, R.drawable.ic_baseline_subscriptions_24)
    )

    Box {
        LazyColumn {
            items(options) { option ->
                WebLinkItem(option) { onClick(option.link) }
                //androidx.compose.material.Divider(color = Color.Gray.copy(0.5F))
            }
        }
    }
}

@Composable
fun WebLinkItem(item: WebLink, onClick: (link: String?) -> Unit) {
    Row(
        modifier = Modifier
            .clickable { onClick(item.link) }
            .padding(8.dp)
    ) {
        Text(
            text = item.desc,
            modifier = Modifier
                .weight(1F)
                .padding(8.dp)
        )
        Icon(
            painter = painterResource(id = item.imageResource), contentDescription = "Option Icon",
            modifier = Modifier.padding(8.dp)
        )
    }
}