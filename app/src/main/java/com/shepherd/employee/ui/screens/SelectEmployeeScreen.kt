package com.shepherd.employee.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.shepherd.employee.R
import com.shepherd.employee.networking.data.Utilities.showDatePicker
import com.shepherd.employee.ui.navigation.Screen
import com.shepherd.employee.ui.screens.composables.AppBarWithAction
import com.shepherd.employee.ui.screens.composables.LoadImageFromUrl
import com.shepherd.employee.viewModel.EmployeeViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectEmployeeScreenScreen(
    navController: NavHostController,
    viewModel: EmployeeViewModel,
) {
    val placeOfBirthState = remember { mutableStateOf(TextFieldValue()) }
    val selectedDate = remember { viewModel.selectedDateBirth }

    var text by remember { mutableStateOf(TextFieldValue()) }
    val context = LocalContext.current
    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    Scaffold(
        topBar = {
            AppBarWithAction(
                onActionClick = {
                    viewModel.selectedDateBirth = selectedDate
                    viewModel.selectedPlaceOfBirth = placeOfBirthState.value.text
                    navController.navigate(Screen.AdditionalInformationScreen.route)
                },
                title = stringResource(id = R.string.select),
            )
        },
    ) { _ ->
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 120.dp),
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = stringResource(id = R.string.select_an_employee),
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
                if (viewModel.selectedEmployee == null) {
                    Image(
                        painter = painterResource(id = R.drawable.baseline_person_24),
                        contentDescription = "",
                    )
                }
                viewModel.selectedEmployee?.let {
                    LoadImageFromUrl(it.avatar)
                }

                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.SpaceEvenly,

                ) {
                    Text(text = if (viewModel.selectedEmployee != null) " ${viewModel.selectedEmployee!!.firstName} ${viewModel.selectedEmployee!!.lastName}" else "Full Name")
                    Text(text = if (viewModel.selectedEmployee != null) " ${viewModel.selectedEmployee!!.email}" else "email")
                }
                Spacer(modifier = Modifier.weight(1f))

                IconButton(
                    onClick = { navController.navigate(Screen.ListOfEmployeesScreen.route) },
                    modifier = Modifier.padding(end = 8.dp),
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_navigate_next),
                        contentDescription = "Right Icon",
                    )
                }
            }

            Divider(
                modifier = Modifier
                    .padding(vertical = 1.dp),
                color = Color.Black,
                thickness = 1.dp,
            )

            OutlinedTextField(
                value = text,
                onValueChange = {
                    text = it
                },
                label = {
                    Text(text = stringResource(id = R.string.date_of_birth))
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done,
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                    },
                ),
                trailingIcon = {
                    IconButton(
                        onClick = {
                            showDatePicker(context, selectedDate) {
                                dateFormat.format(it.time).also { formattedDate ->
                                    text = TextFieldValue(text = formattedDate)
                                }
                                viewModel.selectedDateBirth = selectedDate
                            }
                        },
                    ) {
                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = "Date Selection",
                        )
                    }
                },
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
            )

            OutlinedTextField(
                value = placeOfBirthState.value,
                onValueChange = { placeOfBirthState.value = it },
                label = { Text(text = stringResource(id = R.string.place_of_birth)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
            )
        }
    }
}
