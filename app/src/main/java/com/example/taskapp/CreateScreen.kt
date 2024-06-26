package com.example.taskapp

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.taskapp.model.Task
import com.example.taskapp.ui.util.TaskForm

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CreateScreen(createTask:(Task)->Unit, navigateUp:()->Unit, modifier: Modifier){
    Column(
        modifier= modifier.verticalScroll(rememberScrollState())
            .background(colorResource(R.color.pale_orange)).fillMaxSize()
            .padding(15.dp, 15.dp)
    ) {
        TaskForm(onClick = createTask, task = null,navigateUp=navigateUp)
    }
}

//@Preview
//@Composable
//fun CreatePreview(){
//    Column(modifier = Modifier.fillMaxSize().background(colorResource(R.color.pale_orange))) {
//        CreateScreen(navigateUp={},modifier = Modifier)
//    }
//
//}