package com.example.taskapp

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.taskapp.model.Task
import com.example.taskapp.model.TaskType
import com.example.taskapp.ui.util.ErrorScreen
import com.example.taskapp.ui.util.LoadingScreen
import com.example.taskapp.ui.util.SearchBar
import com.example.taskapp.ui.util.TaskCard
import com.example.taskapp.ui.util.TaskItemCard
import com.example.taskapp.viewModel.TasksUiState
import com.example.taskapp.viewModel.TasksViewModel

//enum class TaskScreen(@StringRes val title :Int?){
//    TaskHomeScreen(title = R.string.app_bar),
//    TaskSearchScreen(title = null),
//    TaskDetailScreen(title = R.string.detail_screen),
//    CreateTaskScreen(title=R.string.create_screen),
//    EditTaskScreen(title=R.string.edit_screen)
//
//}

sealed class  TaskScreen(val route:String, @StringRes val title: Int?){
    object TaskHomeScreen :TaskScreen(route="home",title = R.string.app_bar)
    object TaskSearchScreen:TaskScreen(route="search",title = null)
   object TaskDetailScreen:TaskScreen(route="detail",title = R.string.detail_screen)
   object CreateTaskScreen:TaskScreen(route="create",title=R.string.create_screen)
   object EditTaskScreen:TaskScreen(route="edit",title=R.string.edit_screen)


    fun withArgs(vararg args:String?):String{
        return  buildString {
            append(route)
            args.forEach {arg->
                append("/$arg")
            }
        }
    }


}
fun trackRoute(screen:String?):TaskScreen{
    Log.d("Route",screen.toString() +TaskScreen.EditTaskScreen.route)
    if(TaskScreen.TaskSearchScreen.route.contains(screen.toString()) ) {
        return TaskScreen.TaskSearchScreen
    }
    else if(screen.toString().contains(TaskScreen.EditTaskScreen.route) ) {
        return TaskScreen.EditTaskScreen
    }
    else if(screen.toString().contains(TaskScreen.TaskDetailScreen.route)) {
        return TaskScreen.TaskDetailScreen
    }
    else if(TaskScreen.CreateTaskScreen.route.contains(screen.toString())) {
        return TaskScreen.CreateTaskScreen
    }
    else
        return  TaskScreen.TaskHomeScreen
}

@SuppressLint("UnrememberedMutableState")
@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
 fun TaskApp(modifier: Modifier=Modifier){
    val  taskViewModel :TasksViewModel = viewModel()
    var search by remember {
        mutableStateOf<String>("")
    }

    val taskUiState= taskViewModel.tasksUiState.collectAsState()
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute  = trackRoute(backStackEntry?.destination?.route )?:TaskScreen.TaskHomeScreen


    Scaffold(
        topBar ={ TopTaskBar(
            navigateToCreate={navController.navigate(TaskScreen.CreateTaskScreen.route)},
            current=currentRoute,
            searchInput=search,
            onChange={
                    it-> search=it!!
                (taskUiState.value as TasksUiState.Success).tasks.filter { it.title.contains(search) }
                     },
            navigateUp={navController.navigateUp()},
            canNavigateUp = navController.previousBackStackEntry !=null,
            modifier=if(currentRoute.route == TaskScreen.TaskHomeScreen.route)
                Modifier.padding(10.dp,30.dp,10.dp,10.dp)
            else
            Modifier.fillMaxWidth()
        )}

    ) {pad->
            when(taskUiState.value){
                is TasksUiState.Loading-> LoadingScreen(modifier.fillMaxSize())

                is TasksUiState.Success->
                    NavHost(navController = navController,
                        startDestination = TaskScreen.TaskHomeScreen.route ){
                        composable(TaskScreen.TaskHomeScreen.route){

                                    TaskHomeScreen(
                                        tasks = (taskUiState.value as TasksUiState.Success).tasks,
                                        todayTask=taskViewModel.getTodayTask(),
                                        countActive =taskViewModel.countActiveFilterDate(),
                                        countUrgent = taskViewModel.countUrgentTask(),
                                        navigateToSearch = { navController.navigate(TaskScreen.TaskSearchScreen.route) },
                                        clickItemDetail = {it-> navController.navigate(TaskScreen.TaskDetailScreen.withArgs(it)) },
                                        modifier = Modifier.padding(pad)
                                    )

                        }
                        composable(TaskScreen.TaskSearchScreen.route){
                            SearchTaskScreen(
                                searchInput=search,
                                tasks = (taskUiState.value as TasksUiState.Success).tasks.filter { it.title.contains(search) },
                                clickItemDetail = {it-> navController.navigate(TaskScreen.TaskDetailScreen.withArgs(it))},
                                modifier = Modifier.padding(pad))
                        }
                        composable(
                            TaskScreen.TaskDetailScreen.route +"/{tasksId}",
                            arguments = listOf(
                                navArgument("tasksId"){
                                    type= NavType.StringType
                                    nullable=false
                                }
                            )
                            ){pd->
                            TaskDetailScreen(
                                task= taskViewModel.getDetail(pd.arguments?.getString("tasksId")),
                                deleteTask ={
                                    taskViewModel.deleteTask(pd.arguments?.getString("tasksId"))
                                    taskViewModel.getTasks()
                                    navController.navigateUp()

                                            },
                                navigateToEdit={brg->navController
                                    .navigate(TaskScreen.EditTaskScreen
                                        .withArgs(brg))
                                               },

                            modifier = Modifier.padding(pad))
                        }
                        composable(TaskScreen.CreateTaskScreen.route){
                            CreateScreen(createTask = {t->
                                taskViewModel.addTask(t)
                                taskViewModel.getTasks()
                                navController.navigateUp()
                                                      },navigateUp = {navController.navigateUp()}, modifier = Modifier.padding(pad))
                        }
                        composable(
                            TaskScreen.EditTaskScreen.route + "/{tasksId}",
                                    arguments = listOf(
                                    navArgument("tasksId"){
                                        type= NavType.StringType
                                        nullable=false
                                    }
                                    )
                        ){
                            pd->
                            EditScreen(
                                editTask={tsk->
                                         taskViewModel.editTask(tsk!!)
                                        taskViewModel.getTasks()
                                        navController.navigateUp()
                                },
                                task= taskViewModel.getDetail(pd.arguments?.getString("tasksId")),navigateUp = {navController.navigateUp()}, modifier = Modifier.padding(pad))
                        }
                    }

                    is TasksUiState.Error -> ErrorScreen(modifier.fillMaxSize())
            }


        }




}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopTaskBar(navigateToCreate:()->Unit,
                       onChange:(String?)->Unit,
                       searchInput:String,
                       modifier: Modifier,
                       navigateUp:()->Unit,
                       canNavigateUp:Boolean,
                       current:TaskScreen){

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
                IconButton(onClick = { navigateToCreate() }) {
                    Icon(
                        modifier = Modifier.size(36.dp),
                        tint = Color.Black,
                        painter = painterResource(R.drawable.add),
                        contentDescription = stringResource(
                            id = R.string.notif_button
                        ),

                        )


                }
                else if(canNavigateUp && current ==TaskScreen.TaskSearchScreen)
                    SearchBar(onClick = null,onChange=onChange, inputKey = searchInput, modifier = Modifier
                        .width(325.dp)
                        .padding(0.dp, 5.dp) )


            }
        })
}

@Composable
private fun TaskHomeScreen(
    tasks:List<Task>,
    countActive:Int,
    countUrgent:Int,
    todayTask:List<Task>,
    navigateToSearch:()->Unit,
    clickItemDetail:(String)->Unit,

    modifier: Modifier){
    Column(modifier = modifier
        .padding(20.dp, 15.dp)
        .fillMaxHeight()) {
         SearchBar(onClick={

            navigateToSearch()
         },onChange={},inputKey = "Search for tasks", modifier = Modifier
             .fillMaxWidth()
             .padding(0.dp, 5.dp))
        Spacer(modifier = Modifier.height(20.dp))
        TaskCardSection(countActive=countActive,countUrgent=countUrgent)
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
            items(todayTask.size){
                TaskItemCard(
                    task=todayTask[it]
                    ,modifier = Modifier
                        .fillMaxWidth()
                        .height(110.dp)
                        .clickable { clickItemDetail(todayTask[it].id.toString()) })
            }
        }



    }
}

@Composable
fun TaskCardSection(countActive:Int,countUrgent: Int){
    Column(
        modifier=Modifier.height(140.dp)
    ){
        Row(
            modifier= Modifier.fillMaxHeight(),
            horizontalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            TaskCard(cardType = TaskType.Regular, count =countActive,modifier=Modifier.weight(1f) )
            TaskCard(cardType = TaskType.Important, count =countUrgent,modifier=Modifier.weight(1f) )
        }
    }
}



//
//@Preview(showBackground = true)
//@Composable
//private fun TaskAppPreview(){
//    TaskApp( modifier= Modifier.fillMaxSize())
//}