package com.shepherd.employee.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.shepherd.employee.R
import com.shepherd.employee.networking.data.Utilities.calendarToString
import com.shepherd.employee.ui.navigation.Screen
import com.shepherd.employee.ui.screens.composables.LoadImageFromUrl
import com.shepherd.employee.ui.screens.composables.SimpleAppBar
import com.shepherd.employee.viewModel.EmployeeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReviewScreen(
    navController: NavHostController,
    viewModel: EmployeeViewModel,
) {
    val uiState by viewModel.submitInformationUiState.collectAsState()
    Scaffold(
        topBar = {
            SimpleAppBar(
                title = stringResource(id = R.string.review),
            )
        },
    ) { _ ->

        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 100.dp),
        ) {
            Text(
                text = stringResource(id = R.string.personal_details),
                color = Color.Black,
                modifier = Modifier.padding(bottom = 10.dp),
                fontSize = 16.sp,
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.Bold,
            )

            Divider(
                modifier = Modifier
                    .padding(vertical = 1.dp),
                color = Color.Black,
                thickness = 1.dp,
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                viewModel.selectedEmployee?.avatar?.let {
                    LoadImageFromUrl(it)
                }

                Column(
                    modifier = Modifier.padding(8.dp),
                    verticalArrangement = Arrangement.SpaceEvenly,

                ) {
                    viewModel.selectedEmployee?.let {
                        Text(text = "${it.firstName} ${it.lastName}")
                        Text(text = it.email)
                    }
                    Text(text = calendarToString(viewModel.selectedDateBirth))
                    Text(text = " ${viewModel.selectedGender}")
                }
            }

            Divider(
                modifier = Modifier
                    .padding(vertical = 1.dp),
                color = Color.Black,
                thickness = 1.dp,
            )

            Text(
                text = stringResource(id = R.string.additional_information),
                color = Color.Black,
                modifier = Modifier.padding(bottom = 10.dp, top = 20.dp),
                fontSize = 16.sp,
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.Bold,
            )

            Text(text = if (viewModel.selectedColour != null) " ${viewModel.selectedColour!!.name}" else "Colour")
            Text(text = " ${viewModel.selectedPlaceOfBirth}")
            Text(text = if (viewModel.selectedResidential != null) " ${viewModel.selectedResidential}" else "Residential Address")

            Button(
                onClick = {
                    viewModel.selectedEmployee?.let { employee ->
                        viewModel.selectedColour?.let { colour ->
                            viewModel.addEmployee(employee, colour)
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 20.dp),
            ) {
                Text(text = stringResource(id = R.string.submit))
            }

            if (uiState.isError) {
                Text(
                    text = stringResource(id = R.string.error_occurred),
                    color = Color.Red,
                    modifier = Modifier.padding(vertical = 8.dp),
                )
            }
        }

        if (!uiState.isLoading && uiState.isSuccess) {
            navController.navigate(Screen.SuccessfulScreen.route)
        }
    }
}
