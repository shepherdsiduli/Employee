package com.shepherd.employee.ui.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.scottyab.rootbeer.RootBeer
import com.shepherd.employee.ui.navigation.navigation
import com.shepherd.employee.ui.theme.EmployeeTheme
import com.shepherd.employee.viewModel.EmployeeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlin.system.exitProcess

@AndroidEntryPoint
class EmployeeActivity : ComponentActivity() {
    private val viewModel by viewModels<EmployeeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // kill the app if the device is rooted
        if (RootBeer(this).isRooted) {
            finish()
            exitProcess(0)
        }

        setContent {
            EmployeeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    navigation(viewModel = viewModel)
                }
            }
        }
    }
}
