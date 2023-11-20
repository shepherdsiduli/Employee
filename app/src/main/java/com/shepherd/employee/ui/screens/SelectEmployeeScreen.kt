package com.shepherd.employee.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.shepherd.employee.R
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
    val dateOfBirthState = remember { mutableStateOf(TextFieldValue()) }
    val placeOfBirth = remember { mutableStateOf(TextFieldValue()) }
    Scaffold(
        topBar = {
            AppBarWithAction(
                onActionClick = {
                    viewModel.selectedDateBirth = dateOfBirthState.value.text
                    viewModel.selectedPlaceOfBirth = dateOfBirthState.value.text
                    navController.navigate(Screen.AdditionalInformationScreen.route)
                },
                title = stringResource(id = R.string.select),
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
            Divider(
                modifier = Modifier
                    .padding(vertical = 1.dp),
                color = Color.Black,
                thickness = 1.dp,
            )

            Text(
                text = stringResource(id = R.string.select_an_employee),
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
                    LoadImageFromUrl(viewModel.selectedEmployee!!.avatar)
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
                value = dateOfBirthState.value,
                onValueChange = { dateOfBirthState.value = it },
                label = { Text(text = stringResource(id = R.string.date_of_birth)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
            )

            OutlinedTextField(
                value = placeOfBirth.value,
                onValueChange = { placeOfBirth.value = it },
                label = { Text(text = stringResource(id = R.string.place_of_birth)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerComponent(onDateSelected: (String) -> Unit) {
    var selectedDate by remember { mutableStateOf(Calendar.getInstance()) }
    var datePickerShown by remember { mutableStateOf(false) }
    val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
    ) {
        Text(
            text = "Select Date of Birth",
            //   style = MaterialTheme.typography.h6,
            modifier = Modifier.padding(bottom = 16.dp),
        )

        OutlinedTextField(
            value = dateFormat.format(selectedDate.time),
            onValueChange = { /* Disable manual text input */ },
            readOnly = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .fillMaxWidth()
                .clickable { datePickerShown = true },
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                onDateSelected(dateFormat.format(selectedDate.time))
            },
            modifier = Modifier.align(Alignment.CenterHorizontally),
        ) {
            Text("Confirm Date")
        }

        if (datePickerShown) {
            DatePicker(
                selectedDate = selectedDate,
                onDateChange = { newDate ->
                    selectedDate = newDate
                    datePickerShown = false
                },
            )
        }
    }
}

@Composable
fun DatePicker(
    selectedDate: Calendar,
    onDateChange: (Calendar) -> Unit,
) {
    DatePickerDialog(
        onDateChange = { newDate ->
            onDateChange(newDate)
        },
        selectedDate = selectedDate,
    )
}

@Composable
fun DatePickerDialog(
    onDateChange: (Calendar) -> Unit,
    selectedDate: Calendar,
) {
    var date by remember { mutableStateOf(selectedDate) }

    DatePicker(
        selectedDate = selectedDate,
        onDateChange = { newDate ->
//            selectedDate = newDate
//            datePickerShown = false
        },
//
//        onDateChange = { year, month, dayOfMonth ->
//            date = Calendar.getInstance().apply {
//                set(year, month, dayOfMonth)
//            }
//        },
//        year = date.get(Calendar.YEAR),
//        month = date.get(Calendar.MONTH),
//        day = date.get(Calendar.DAY_OF_MONTH)
    )

    DisposableEffect(Unit) {
        onDispose {
            onDateChange(date)
        }
    }
}

@Preview
@Composable
fun PreviewDatePickerComponent() {
    DatePickerComponent { }
}
