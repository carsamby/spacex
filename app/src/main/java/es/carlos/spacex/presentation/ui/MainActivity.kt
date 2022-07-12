@file:OptIn(ExperimentalMaterialApi::class)

package es.carlos.spacex.presentation.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dagger.hilt.android.AndroidEntryPoint
import es.carlos.spacex.R
import es.carlos.spacex.domain.model.Launch
import es.carlos.spacex.presentation.theme.SpaceXTheme
import es.carlos.spacex.presentation.ui.items.FilterBottomSheet
import es.carlos.spacex.presentation.ui.items.LaunchItem
import es.carlos.spacex.presentation.ui.items.LinkBottomSheet
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SpaceXTheme {
                val mainViewModel = hiltViewModel<MainViewModel>()

                MainScreen(mainViewModel)
            }
        }
    }
}

@Composable
fun HeaderText(text: String) {
    Text(
        text = text,
        modifier = Modifier
            .background(color = colorResource(id = R.color.black))
            .fillMaxWidth()
            .padding(4.dp),
        color = colorResource(id = R.color.white),
        style = MaterialTheme.typography.bodyLarge
    )
}

enum class SheetType { FILTER, ITEM, NONE }

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainScreen(
    mainViewModel: MainViewModel
) {

    // Context
    val uriHandler = LocalUriHandler.current

    // Filter Bottom Sheet
    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmStateChange = { it != ModalBottomSheetValue.HalfExpanded }
    )

    var bottomSheetType by remember { mutableStateOf(SheetType.NONE) }
    var selectedLaunch by remember { mutableStateOf<Launch?>(null) }

    val scope = rememberCoroutineScope()

    BackHandler(enabled = sheetState.isVisible) {
        scope.launch {
            if (sheetState.isVisible) sheetState.hide()
        }
    }

    // Fitler params

    var isSuccess by remember { mutableStateOf<Boolean?>(null) }
    var year by remember { mutableStateOf<Int?>(null) }

    // Main container
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        ModalBottomSheetLayout(
            sheetState = sheetState,
            sheetContent = {
                if (bottomSheetType == SheetType.FILTER)
                    FilterBottomSheet { _isSuccess, _yearSince, _order ->

                        isSuccess = _isSuccess
                        year = _yearSince

                        scope.launch {
                            sheetState.hide()
                        }

                        mainViewModel.getLaunches(_order, _yearSince, _isSuccess)
                    }
                else
                    LinkBottomSheet(selectedLaunch) {
                        it?.let { url -> uriHandler.openUri(url) }
                    }
            }
        ) {

            Scaffold(
                topBar = {
                    CenterAlignedTopAppBar(
                        title = {
                            Text(
                                text = "SpaceX",
                                style = MaterialTheme.typography.headlineMedium
                            )
                        },
                        actions = {

                            IconButton(onClick = {
                                scope.launch {
                                    bottomSheetType = SheetType.FILTER
                                    sheetState.show()
                                }
                            }) {
                                BadgedBox(badge = {
                                    if (year != null || isSuccess != null)
                                        Badge { Text("") }
                                }
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_baseline_filter_alt_24),
                                        contentDescription = "Filters"
                                    )
                                }
                            }
                        }
                    )
                }
            ) {

                val infoState = mainViewModel.companyInfo.value
                val launchesState = mainViewModel.launches.value


                Column {
                    HeaderText("COMPANY")

                    if (infoState.isLoading == true)
                        LinearProgressIndicator(modifier = Modifier.fillMaxWidth())

                    Text(
                        text = if (infoState.isSuccess == true) "${infoState.data?.name} was founded" +
                                " by ${infoState.data?.founder} in " +
                                "${infoState.data?.year}. It has now ${infoState.data?.employees} employees, " +
                                "${infoState.data?.launch_sites} launch sites, and is valued at " +
                                "${infoState.data?.valuation} USD" else infoState.error.toString(),
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(4.dp)
                    )

                    HeaderText("LAUNCHES")

                    if (launchesState.isLoading == true)
                        LinearProgressIndicator(modifier = Modifier.fillMaxWidth())

                    if (launchesState.isSuccess == true)
                        Box {
                            LazyColumn(modifier = Modifier.fillMaxSize()) {
                                launchesState.data?.let { launches ->
                                    items(launches) { launch ->
                                        LaunchItem(launch) {
                                            selectedLaunch = launch
                                            scope.launch {
                                                bottomSheetType = SheetType.ITEM
                                                sheetState.show()
                                            }
                                        }
                                        Divider(color = Color.Gray.copy(0.5F))
                                    }
                                }
                            }
                        }
                    else if (launchesState.isSuccess == false) {
                        Column(modifier = Modifier.fillMaxSize()) {
                            Text(
                                modifier = Modifier.padding(16.dp),
                                text = mainViewModel.companyInfo.value.error.toString(),
                                style = MaterialTheme.typography.titleMedium
                            )

                            Button(
                                onClick = {
                                    mainViewModel.getLaunches(
                                        dateSince = year,
                                        isSuccess = isSuccess
                                    )
                                    mainViewModel.getCompanyInfo()
                                },
                                modifier = Modifier
                                    .padding(16.dp)
                                    .align(Alignment.CenterHorizontally)
                            ) {
                                Text(text = "Try it again")
                            }
                        }
                    }
                }
            }
        }
    }
}

