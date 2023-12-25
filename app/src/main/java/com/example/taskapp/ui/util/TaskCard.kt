package com.example.taskapp.ui.util

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.taskapp.R
import com.example.taskapp.model.TaskType

@Composable
fun TaskCard(cardType:TaskType,count:Int,modifier: Modifier=Modifier){
    val color : Int = if(cardType == TaskType.Regular ) R.color.pale_orange else R.color.reddish
    val text : Int = if(cardType == TaskType.Regular ) R.string.active_tasks else R.string.urgent_tasks
    Card(modifier=modifier) {
        Column(modifier= Modifier
            .background(colorResource(color))
            .fillMaxSize()
            .padding(10.dp, 15.dp),
            verticalArrangement = Arrangement.SpaceBetween) {
            Text(
                stringResource(text),
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                lineHeight = 23.sp


            )
            CountTask(count=count,modifier = Modifier.width(140.dp))

        }


    }
}

@SuppressLint("ResourceType")
@Composable
fun CountTask(count:Int,modifier: Modifier){
    val text :String = pluralStringResource(R.plurals.count_tasks, count =count,count )
    Card(modifier = modifier) {
        Row(
            modifier = Modifier.padding(6.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
            ) {
           Icon(
               painter = painterResource(R.drawable.task),
               contentDescription =null,
               modifier=Modifier.size(20.dp),
               tint = Color.Black
           )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                text = text,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Preview
@Composable
fun TaskCardPreview(){
    TaskCard(cardType = TaskType.Important, count=123,modifier = Modifier.size(170.dp))
}