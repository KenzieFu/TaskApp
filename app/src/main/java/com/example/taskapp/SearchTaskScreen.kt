package com.example.taskapp

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.taskapp.ui.util.TaskItemCard

@Composable
fun SearchTaskScreen(clickItemDetail:()->Unit,modifier:Modifier){
    Column(modifier=modifier .padding(20.dp, 15.dp)) {
        Text(
            stringResource(R.string.list_of_tasks),
            fontWeight = FontWeight.Bold,
            fontSize = 26.sp,
        )
        Spacer(modifier = Modifier.height(20.dp))
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ){
            items(6){
                item-> TaskItemCard(modifier = Modifier.fillMaxWidth().height(110.dp).clickable { clickItemDetail() })
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun SearhTaskPreview(){
//    Column(modifier= Modifier.fillMaxSize()) {
//        SearchTaskScreen(modifier=Modifier )
//    }
//
//}