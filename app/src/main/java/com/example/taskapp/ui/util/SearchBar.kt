package com.example.taskapp.ui.util

import android.graphics.drawable.shapes.Shape
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(onClick:(()->Unit)?,onChange:(String?)->Unit,inputKey:String,modifier: Modifier){

    Column(
        modifier=if(onClick!=null)modifier.clickable { onClick() }else modifier,
    ){
        if(onClick !=null)
        Row(
            modifier = modifier
                .border(
                    border = BorderStroke(0.dp, Color.Black),
                    shape = RoundedCornerShape(20.dp)
                )
                .padding(12.dp)
        ) {
            Text(text = "Search for Tasks")
        }
        else
            OutlinedTextField(

                value = inputKey,
                onValueChange ={value->
                    onChange(value)
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                modifier = Modifier

                    .padding(all = 2.dp)
                    .fillMaxWidth(),
              maxLines = 1,



            )
    }

}

//@Preview(showBackground = true)
//@Composable
//fun SearchBarPreview(){
//    Surface(modifier = Modifier.fillMaxSize()) {
//        Column(modifier =Modifier) {
//            SearchBar(onClick ={}, inputKey = "H", modifier = Modifier.fillMaxWidth())
//        }
//
//    }
//
//}