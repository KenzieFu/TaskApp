package com.example.taskapp.network

import com.example.taskapp.model.Response
import com.example.taskapp.model.Task
import retrofit2.Call

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded

import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query


private const val BASE_URL =
        "http://10.0.2.2/taskApi/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()


interface TasksApiClient{
    @GET("read.php")
    suspend fun getNotes(): List<Task>

    @GET("detail.php")
    suspend fun getDetails(
        @Query("tasksId") tasksId: String?
    ):Task

    @FormUrlEncoded
    @POST("delete.php")
    suspend fun deleteTasks(
        @Field("tasksId") notesId: String?
    ): Response

    @FormUrlEncoded
    @POST("update.php")
    suspend fun editTasksDetail(
        @Field("tasksId") tasksId: String?,
        @Field("title") title: String?,
        @Field("description") content: String?,
        @Field("date") date: String?,
        @Field("type") type : String?,
    ): Response

    @FormUrlEncoded
    @POST("add.php")
    suspend fun addTasksDetail(
        @Field("title") title: String?,
        @Field("description") content: String?,
        @Field("date") date: String?,
        @Field("type") type : String?,
    ): Response
}

object TasksApi{
    val retrofitService : TasksApiClient by lazy {
        retrofit.create(TasksApiClient::class.java)
    }
}