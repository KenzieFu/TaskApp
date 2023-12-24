package com.example.taskapp.ui.util

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun TaskItemCard(){
    Card {
        Column {
            Text(text = "Attend Party")
        }
        
    }
}

@Preview
@Composable
fun TaskItemCardPreview(){

}