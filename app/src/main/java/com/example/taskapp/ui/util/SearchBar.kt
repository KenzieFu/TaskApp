package com.example.taskapp.ui.util

import android.graphics.drawable.shapes.Shape
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SearchBar(inputKey:String,modifier: Modifier){
    Column(modifier=modifier){
        Row(
            modifier = modifier
                .border(
                    border = BorderStroke(0.dp, Color.Black),
                    shape = RoundedCornerShape(20.dp))
                .padding(12.dp)
        ) {
            Text(text = inputKey)
        }
    }

}

@Preview(showBackground = true)
@Composable
fun SearchBarPreview(){
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(modifier =Modifier) {
            SearchBar(inputKey = "H", modifier = Modifier.fillMaxWidth())
        }

    }

}