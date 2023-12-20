package com.example.viewmodel

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.viewmodel.ui.theme.ViewModelTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ViewModelTheme {
                StudentApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentApp() {
    val viewModel = remember { StudentViewModel() }
    val isShowDialog = remember {
        mutableStateOf(false)
    }

    if (isShowDialog.value) {
        InputDialog(
            viewModel = viewModel,
            onAddButton = {
                isShowDialog.value = false
            },
            onCancle = {
                isShowDialog.value = false
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Student App") },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = Color.Magenta,
                    titleContentColor = Color.White
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { isShowDialog.value = true },
                containerColor = Color.Magenta,
                contentColor = Color.White
            ) {
                Icon(Icons.Filled.Add, "Add new student")
            }
        }
    ) {
        LazyColumn(
            modifier = Modifier.padding(it)
        ) {
            items(viewModel.data) { student ->
                Text(" Student ID: ${student.studentId},Name: ${student.name}")
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputDialog(
    viewModel: StudentViewModel,
    onCancle: () -> Unit,
    onAddButton: () -> Unit
) {
    Dialog(onDismissRequest = onCancle) {
        var inputName by remember {
            mutableStateOf("")
        }
        var inputId by remember {
            mutableStateOf("")
        }
        Card(modifier = Modifier.padding(10.dp)) {
            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                OutlinedTextField(
                    value = inputId,
                    onValueChange = { inputId = it },
                    label = { Text("Student id") }
                )
                OutlinedTextField(
                    value = inputName,
                    onValueChange = { inputName = it },
                    label = { Text("Student name") }
                )
                TextButton(
                    onClick = {
                        viewModel.addStudent(inputName, inputId)
                        onAddButton()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Magenta
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)
                ) {
                    Text("Add")
                }
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun AppPreview() {
    ViewModelTheme {
        val viewModel = remember { StudentViewModel() }
        val isShowDialog = remember {
            mutableStateOf(false)
        }

        if (isShowDialog.value) {
            InputDialog(
                viewModel = viewModel,
                onAddButton = { isShowDialog.value = false },
                onCancle = { isShowDialog.value = false }
            )
        }

        StudentApp()
    }
}