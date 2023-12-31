package com.shepherd.employee.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.shepherd.employee.R
import com.shepherd.employee.ui.navigation.Screen
import com.shepherd.employee.ui.screens.composables.AppBarWithAction
import com.shepherd.employee.ui.screens.composables.ColoredCircle
import com.shepherd.employee.viewModel.EmployeeViewModel

enum class Gender {
    MALE, FEMALE, OTHER
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdditionalInformationScreen(
    navController: NavHostController,
    viewModel: EmployeeViewModel,
) {
    val residentialAddress = remember { mutableStateOf(TextFieldValue()) }
    Scaffold(
        topBar = {
            AppBarWithAction(
                onActionClick = {
                    viewModel.selectedResidential = residentialAddress.value.text
                    navController.navigate(Screen.ReviewScreen.route)
                },
                title = stringResource(id = R.string.additional_information),
            )
            Divider(
                modifier = Modifier
                    .padding(vertical = 1.dp),
                color = Color.Black,
                thickness = 1.dp,
            )
        },
    ) { _ ->
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 100.dp),
        ) {
            GenderSelection()

            Text(
                text = stringResource(id = R.string.select_preferred_colour),
                color = Color.Black,
                modifier = Modifier.padding(bottom = 10.dp, top = 32.dp),
                fontSize = 16.sp,
            )

            Divider(
                modifier = Modifier
                    .fillMaxWidth()
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
                if (viewModel.selectedColour == null) {
                    ColoredCircle(hexColor = "#45B5AA")
                }
                viewModel.selectedColour?.let {
                    ColoredCircle(hexColor = it.color)
                }

                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.SpaceEvenly,

                ) {
                    Text(text = if (viewModel.selectedColour != null) viewModel.selectedColour!!.name else "Colour name")
                }

                Spacer(modifier = Modifier.weight(1f))

                IconButton(
                    onClick = { navController.navigate(Screen.SelectColourScreen.route) },
                    modifier = Modifier.padding(end = 8.dp),
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_navigate_next),
                        contentDescription = "Colours Icon",
                    )
                }
            }

            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 1.dp),
                color = Color.Black,
                thickness = 1.dp,
            )

            OutlinedTextField(
                value = residentialAddress.value,
                onValueChange = { residentialAddress.value = it },
                label = { Text(text = stringResource(id = R.string.residential_address)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 32.dp),
            )
        }
    }
}

@Composable
fun GenderSelection() {
    var selectedGender by remember { mutableStateOf(Gender.MALE) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(2.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(id = R.string.choose_gender),
            color = Color.Black,
            modifier = Modifier.padding(bottom = 10.dp),
            fontSize = 16.sp,
        )

        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 1.dp),
            color = Color.Black,
            thickness = 1.dp,
        )

        TabRow(
            selectedTabIndex = when (selectedGender) {
                Gender.MALE -> 0
                Gender.FEMALE -> 1
                Gender.OTHER -> 2
            },
        ) {
            Gender.values().forEachIndexed { index, gender ->
                Tab(
                    text = { Text(gender.name) },
                    selected = selectedGender == gender,
                    onClick = {
                        selectedGender = gender
                        // onGenderSelected(gender)
                    },
                )
            }
        }
    }
}
