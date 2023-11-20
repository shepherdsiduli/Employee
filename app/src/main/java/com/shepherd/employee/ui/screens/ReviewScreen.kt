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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.shepherd.employee.R
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
            Divider(
                modifier = Modifier
                    .padding(vertical = 1.dp),
                color = Color.Black,
                thickness = 1.dp,
            )

            Text(
                text = stringResource(id = R.string.personal_details),
                color = Color.Black,
                modifier = Modifier.padding(bottom = 10.dp),
                fontSize = 16.sp,
                textAlign = TextAlign.Start,
            )

            Divider(
                modifier = Modifier
                    .padding(vertical = 1.dp),
                color = Color.Black,
                thickness = 1.dp,
            )

            Row() {
                viewModel.selectedEmployee?.let {
                    LoadImageFromUrl(viewModel.selectedEmployee!!.avatar)
                }

                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.SpaceEvenly,

                ) {
                    Text(text = if (viewModel.selectedEmployee != null) " ${viewModel.selectedEmployee!!.firstName} ${viewModel.selectedEmployee!!.lastName}" else "Full Name")
                    Text(text = if (viewModel.selectedEmployee != null) " ${viewModel.selectedEmployee!!.email}" else "email")
                    Text(text = if (viewModel.selectedDateBirth != null) " ${viewModel.selectedDateBirth}" else "DOB")
                    Text(text = if (viewModel.selectedGender != null) " ${viewModel.selectedGender}" else "Gender")
                }
            }

            Divider(
                modifier = Modifier
                    .padding(vertical = 1.dp),
                color = Color.Black,
                thickness = 1.dp,
            )

            Text(text = if (viewModel.selectedColour != null) " ${viewModel.selectedColour!!.name}" else "Colour")
            Text(text = if (viewModel.selectedDateBirth != null) " ${viewModel.selectedDateBirth}" else "Place of Birth")
            Text(text = if (viewModel.selectedResidential != null) " ${viewModel.selectedResidential}" else "Residential Address")

            Button(
                onClick = {
                    // viewModel.addEmployee()
                    navController.navigate(Screen.SuccessfulScreen.route)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
            ) {
                Text(text = stringResource(id = R.string.submit))
            }
        }
    }
}
