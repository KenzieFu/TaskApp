package com.example.taskapp

import android.graphics.drawable.Icon
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon

import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.taskapp.model.TaskType
import com.example.taskapp.ui.util.SearchBar
import com.example.taskapp.ui.util.TaskCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
 fun TaskApp(modifier: Modifier=Modifier){
    Scaffold(
        topBar ={ TopTaskBar()}
    ) {
            TaskHomeScreen(modifier = Modifier.padding(it))

    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopTaskBar(){
    TopAppBar(
        modifier = Modifier.padding(10.dp,30.dp,10.dp,10.dp),
        title =  {
    Row {
        Text(
            text= stringResource(R.string.app_bar),
            fontWeight = FontWeight.Bold,
            fontSize = 36.sp
        )
    }

    },
        actions ={
            Row {
                IconButton(onClick = { /*TODO*/ }) {
                  Icon(
                      modifier=Modifier.size(36.dp),
                      tint= Color.Black,
                      painter = painterResource(R.drawable.notification) ,
                      contentDescription = stringResource(
                      id = R.string.notif_button
                  ) ,
 
                  )
                }
            }
        }
    )
}

@Composable
private fun TaskHomeScreen(modifier: Modifier){
    Column(modifier = modifier
        .padding(20.dp, 15.dp)
        .fillMaxHeight()) {
         SearchBar(inputKey = "halo", modifier = Modifier
             .fillMaxWidth()
             .padding(0.dp, 5.dp))
        Spacer(modifier = Modifier.height(20.dp))
        TaskCardSection()
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Today Tasks",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold
            )


    }
}

@Composable
fun TaskCardSection(){
    Column(
        modifier=Modifier.height(180.dp)
    ){
        Row(
            modifier= Modifier.fillMaxHeight(),
            horizontalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            TaskCard(cardType = TaskType.Regular, count =12,modifier=Modifier.weight(1f) )
            TaskCard(cardType = TaskType.Important, count =3,modifier=Modifier.weight(1f) )
        }
    }
}




@Preview(showBackground = true)
@Composable
private fun TaskAppPreview(){
    TaskApp(modifier= Modifier.fillMaxSize())
}