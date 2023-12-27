package com.example.taskapp

import android.graphics.drawable.shapes.Shape
import android.widget.Space
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.taskapp.model.Task

import com.example.taskapp.ui.util.BoxInfo

@Composable
fun TaskDetailScreen(deleteTask:()->Unit,navigateToEdit:(String)->Unit,task: Task?, modifier:Modifier){
    Column(
        modifier=modifier
            .background(colorResource(R.color.pale_orange))
            .padding(15.dp, 15.dp)

    ){
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            Text(task!!.title,
                fontWeight = FontWeight.Bold,
                fontSize = 26.sp,
                )
            IconButton(
                onClick = { navigateToEdit(task.id.toString()) },

                ) {
                Icon(
                    painterResource( R.drawable.edit_task),
                    contentDescription = null,
                    modifier = Modifier.size(30.dp)
                    )
            }
        }
        Spacer(modifier = Modifier.height(10.dp))

        Row {
            BoxInfo(content = task!!.date.substring(11,16), icon = R.drawable.clock, modifier =Modifier )
            Spacer(modifier = Modifier.width(10.dp))
            BoxInfo(content = task.date.substring(0,10), icon =R.drawable.date , modifier =Modifier )
        }
        Spacer(modifier = Modifier.height(10.dp))
      TextButton(
          onClick = { deleteTask() },
          modifier = Modifier
              .clip(RoundedCornerShape(10.dp))
              .background(Color.Red),

      ) {

          Text(
              text = "Delete",
              fontSize = 16.sp,
              color = Color.White
              )
      }
        Spacer(modifier = Modifier.height(15.dp))

        Text(
            "Task Description",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            )
        Spacer(modifier = Modifier.height(10.dp))
        Column(
            modifier= Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
                .background(Color.White)
                .padding(15.dp),
        ) {
            Text(task!!.description,
                fontSize = 16.sp
            )
        }
    }
}
//
//@Preview
//@Composable
//fun TaskDetailPreview() {
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(colorResource(R.color.pale_orange))
//    ) {
//        TaskDetailScreen(modifier=Modifier .padding(20.dp, 15.dp))
//    }
//}