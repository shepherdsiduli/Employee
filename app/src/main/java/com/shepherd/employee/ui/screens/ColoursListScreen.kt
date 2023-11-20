package com.shepherd.employee.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.shepherd.employee.R
import com.shepherd.employee.networking.data.response.Colour
import com.shepherd.employee.ui.navigation.Screen
import com.shepherd.employee.ui.screens.composables.ColoredCircle
import com.shepherd.employee.ui.screens.composables.employeeAppBar
import com.shepherd.employee.viewModel.EmployeeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ColoursListScreen(
    navController: NavHostController,
    viewModel: EmployeeViewModel,
) {
    val scaffoldState = rememberScrollState()

    Scaffold(
        topBar = {
            employeeAppBar(title = stringResource(id = R.string.select_preferred_colour))
        },
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(state = scaffoldState),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            val uiState by viewModel.coloursListUiState.collectAsState()

            LaunchedEffect(Unit) {
                viewModel.fetchColours()
            }

            ColoursList(
                navController = navController,
                colours = uiState.colours,
                modifier = Modifier.padding(4.dp),
                viewModel = viewModel,
            )
        }
    }
}

@Composable
fun ColoursList(
    navController: NavHostController,
    colours: List<Colour>,
    modifier: Modifier,
    viewModel: EmployeeViewModel,
) {
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.SpaceEvenly,

    ) {
        colours.forEach { colour ->
            Row(
                modifier = modifier.clickable {
                    viewModel.setColour(colour)
                    navController.navigate(Screen.AdditionalInformationScreen.route)
                },
            ) {
                ColoredCircle(
                    hexColor = colour.color,
                )

                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.SpaceEvenly,

                ) {
                    Text(text = colour.name)
                }
            }
            Divider(
                modifier = Modifier
                    .padding(vertical = 1.dp),
                color = Color.Black,
                thickness = 1.dp,
            )
        }
    }
}
