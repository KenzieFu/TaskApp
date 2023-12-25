package com.example.taskapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.example.taskapp.ui.util.TaskForm

@Composable
fun EditScreen(navigateUp:()->Unit,modifier:Modifier){
    Column(
        modifier= modifier.verticalScroll(rememberScrollState())
            .background(colorResource(R.color.pale_orange)).fillMaxSize()
            .padding(15.dp, 15.dp)
    ) {
        TaskForm(navigateUp=navigateUp)
    }
}