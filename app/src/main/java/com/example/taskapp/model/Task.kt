package com.example.taskapp.model

enum class TaskType{
    Regular,
    Important
}

data class Task(
    val id :Int,
    val title: String,
    val content : String,
    val date : String,
    val taskType :TaskType,
)
