package com.example.taskapp

import android.graphics.drawable.Icon
import android.util.Log
import androidx.annotation.StringRes
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon

import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.taskapp.model.TaskType
import com.example.taskapp.ui.util.SearchBar
import com.example.taskapp.ui.util.TaskCard
import com.example.taskapp.ui.util.TaskItemCard

enum class TaskScreen(@StringRes val title :Int?){
    TaskHomeScreen(title = R.string.app_bar),
    TaskSearchScreen(title = null),

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
 fun TaskApp(modifier: Modifier=Modifier){
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute =TaskScreen.valueOf(
        backStackEntry?.destination?.route
            ?:TaskScreen.TaskHomeScreen.name
    )
    Scaffold(
        topBar ={ TopTaskBar(
            current=currentRoute,
            navigateUp={navController.navigateUp()},
            canNavigateUp = navController.previousBackStackEntry !=null,
            modifier=if(currentRoute.name == TaskScreen.TaskHomeScreen.name)
                Modifier.padding(10.dp,30.dp,10.dp,10.dp)
            else
            Modifier.fillMaxWidth()
        )}
    ) {pad->
        NavHost(navController = navController,
            startDestination = TaskScreen.TaskHomeScreen.name ){
           composable(TaskScreen.TaskHomeScreen.name){
               TaskHomeScreen(navController=navController,modifier = Modifier.padding(pad))
           }
            composable(TaskScreen.TaskSearchScreen.name){
                SearchTaskScreen(modifier = Modifier.padding(pad))
            }
        }


    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopTaskBar(modifier: Modifier,navigateUp:()->Unit,
                       canNavigateUp:Boolean,current:TaskScreen){

    TopAppBar(
        modifier = if(current == TaskScreen.TaskHomeScreen)modifier
        else modifier.shadow(12.dp),
        navigationIcon={
            if (canNavigateUp) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        },
        title =  {
    Row {
        if (current.title !=null){
        Text(
            text= stringResource(current.title),
            fontWeight = FontWeight.Bold,
            fontSize = 36.sp
        )}
    }

    },
        actions = {
            Row {
                if(!canNavigateUp)
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        modifier = Modifier.size(36.dp),
                        tint = Color.Black,
                        painter = painterResource(R.drawable.notification),
                        contentDescription = stringResource(
                            id = R.string.notif_button
                        ),

                        )


                }
                else if(canNavigateUp && current ==TaskScreen.TaskSearchScreen)
                    SearchBar(onClick = null, inputKey = "hi", modifier = Modifier
                        .width(325.dp)
                        .padding(0.dp, 5.dp) )


            }
        })
}

@Composable
private fun TaskHomeScreen(navController: NavController,modifier: Modifier){
    Column(modifier = modifier
        .padding(20.dp, 15.dp)
        .fillMaxHeight()) {
         SearchBar(onClick={
             Log.d("Hi","Hi")
             navController.navigate(TaskScreen.TaskSearchScreen.name)
         },inputKey = "halo", modifier = Modifier
             .fillMaxWidth()
             .padding(0.dp, 5.dp))
        Spacer(modifier = Modifier.height(20.dp))
        TaskCardSection()
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Today Tasks",
            fontWeight = FontWeight.Bold,
            fontSize = 26.sp,

            )
        Spacer(modifier = Modifier.height(20.dp))
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ){
            items(3){
                TaskItemCard(modifier = Modifier
                    .fillMaxWidth()
                    .height(110.dp))
            }
        }



    }
}

@Composable
fun TaskCardSection(){
    Column(
        modifier=Modifier.height(140.dp)
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