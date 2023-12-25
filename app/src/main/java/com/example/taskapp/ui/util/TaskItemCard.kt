package com.example.taskapp.ui.util

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import com.example.taskapp.R
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TaskItemCard(modifier: Modifier){
    Card(modifier=modifier) {
        Column(modifier= modifier
            .background(colorResource(R.color.aqua))
            .padding(15.dp),
            verticalArrangement = Arrangement.SpaceBetween
            ) {
            Text(
                text = "Attend Party",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
                )
            TimeTask(time = "12:00",Modifier)
        }

        
    }
}
@Composable
fun TimeTask(time:String,modifier: Modifier){

    Card(modifier = modifier) {
        Row(
            modifier = Modifier.padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(R.drawable.clock),
                contentDescription =null,
                modifier=Modifier.size(20.dp),
                tint = Color.Black
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                time,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Preview
@Composable
fun TaskItemCardPreview(){
    TaskItemCard(modifier= Modifier
        .fillMaxWidth()
        .height(130.dp))
}