package com.example.taskapp.viewModel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import com.example.taskapp.model.Task
import androidx.lifecycle.viewModelScope
import com.example.taskapp.model.TaskType
import com.example.taskapp.network.TasksApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import java.time.LocalDate
import java.time.format.DateTimeFormatter


sealed interface TasksUiState {
    data class Success(val tasks:List<Task> ) : TasksUiState
    object Error : TasksUiState
    object Loading : TasksUiState
}
class TasksViewModel : ViewModel(){
//    var _tasksUiState: TasksUiState by mutableStateOf(TasksUiState.Loading)
//    private set
    private var _tasksUiState = MutableStateFlow<TasksUiState>(TasksUiState.Loading)
    var tasksUiState: StateFlow<TasksUiState> = _tasksUiState
    init{
        getTasks()
    }

    fun getTasks(){
        _tasksUiState.value = TasksUiState.Loading
        viewModelScope.launch{
            try {
                val listResult = TasksApi.retrofitService.getNotes()

                _tasksUiState.value = TasksUiState.Success(listResult)
                Log.d("Error", (_tasksUiState.value as TasksUiState.Success).tasks.toString())
            }
            catch (e:IOException){
                Log.d("Error",e.toString())
                _tasksUiState.value = TasksUiState.Error
            }
        }
    }

   fun getDetail(tasksId: String?):Task?{

       val tasks =getListTask().find { it.id == tasksId?.toInt() }
       Log.d("Res",tasks.toString())
       return tasks
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun getTodayTask():List<Task>{
        val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

        var today = (_tasksUiState.value as TasksUiState.Success).tasks
            today = today.filter {
                val date = LocalDate.parse(it.date, dateFormatter)
                filterDate(date)
            }
        return today

        }

    @RequiresApi(Build.VERSION_CODES.O)
    fun filterDate(date:LocalDate):Boolean{
        val currentDate = LocalDate.now()
        return (date.year==(currentDate.year) && date.month ==currentDate.month && date.dayOfMonth == currentDate.dayOfMonth)

    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun countActiveFilterDate():Int{
        val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

        var active = getListTask()
        active = active.filter {
            val date = LocalDate.parse(it.date, dateFormatter)
            filterActiveTask(date)
        }

        return active.count()

    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun countUrgentTask():Int{
        val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        var urgent =getListTask()
        urgent = urgent.filter {
            val date = LocalDate.parse(it.date, dateFormatter)
            Log.d("Check",it.type+" "+TaskType.Important.name)
            (filterActiveTask(date) && (it.type ==TaskType.Important.name))
        }
        return urgent.count()
    }

    fun getListTask():List<Task>{
        return (_tasksUiState.value as TasksUiState.Success).tasks
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun filterActiveTask(date:LocalDate):Boolean{
        val currentDate = LocalDate.now()
        val compare = date.isAfter(currentDate) || date.isEqual(currentDate)
        return compare
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun editTask(task:Task){

        viewModelScope.launch {
            try {

                val res=TasksApi.retrofitService.editTasksDetail(
                    tasksId = task.id.toString(),
                    title =task.title,
                    content= task.description,
                    date=task.date,
                    type=task.type
                )
                getTasks()

            }
            catch (e : IOException){
                    Log.d("HELLLO",e.toString())
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun addTask(task: Task){
        viewModelScope.launch {
            try {
                val res = TasksApi.retrofitService.addTasksDetail(
                    title = task.title,
                    content = task.description,
                    date = task.date,
                    type = task.type
                )

                getTasks()



            }catch (e:IOException){

            }
        }
    }

    fun deleteTask(taskId:String?){
        viewModelScope.launch {
            try {
                val res = TasksApi.retrofitService.deleteTasks(taskId)
                getTasks()
            }catch (e:IOException){

            }


        }
    }

    }




