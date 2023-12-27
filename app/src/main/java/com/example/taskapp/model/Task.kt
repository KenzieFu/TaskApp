package com.example.taskapp.model

enum class TaskType{
    Regular,
    Important
}

data class Task(
    val id :Int,
    val title: String,
    val description : String,
    val date : String,
    val type :String
)
