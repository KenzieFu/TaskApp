package com.example.taskapp.ui.util

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.graphics.Paint.Align
import android.icu.util.Calendar
import android.os.Build
import android.util.Log
import android.widget.Button
import android.widget.DatePicker
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonDefaults

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.taskapp.model.Task
import com.example.taskapp.model.TaskType
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskForm(onClick:(Task)->Unit,task: Task?, navigateUp:()->Unit){
    val mContext = LocalContext.current
    val mCalendar = Calendar.getInstance()
    // Declaring integer values
    // for year, month and day
    val mYear: Int
    val mMonth: Int
    val mDay: Int
    // Fetching current year, month and day
    mYear = mCalendar.get(Calendar.YEAR)
    mMonth = mCalendar.get(Calendar.MONTH)
    mDay = mCalendar.get(Calendar.DAY_OF_MONTH)
    val mHour = mCalendar[Calendar.HOUR_OF_DAY]
    val mMinute = mCalendar[Calendar.MINUTE]

    mCalendar.time = Date()

    var date by remember { mutableStateOf<String>(task?.date?.substring(0,10) ?:"") }

    // Declaring DatePickerDialog and setting
    // initial values as current values (present year, month and day)
    val mDatePickerDialog = DatePickerDialog(
        mContext,
        { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
            date = "$mYear-${mMonth+1}-$mDayOfMonth"
        }, mYear, mMonth, mDay
    )

    var time by remember { mutableStateOf<String>(task?.date?.substring(11,16)?:"") }

    // Creating a TimePicker dialod
    val mTimePickerDialog = TimePickerDialog(
        mContext,
        {_, mHour : Int, mMinute: Int ->
            time = "${mHour.toString().padStart(2,'0')}:${mMinute.toString().padStart(2,'0')}"
        }, mHour, mMinute, false
    )

    var title by remember { mutableStateOf<String>(task?.title?:"") }
    var desc by remember { mutableStateOf<String>(task?.description?:"") }

    var current =if(task?.type=="Regular" || task?.type ==null) TaskType.Regular else TaskType.Important
    var taskType by remember { mutableStateOf<TaskType>(current) }
    Column(
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        //Title
       OutlinedTextField(
           value = title,
           onValueChange ={title=it},
           maxLines = 1,
           placeholder = {
               Text("Title")

           },
           modifier = Modifier.background(Color.White)
       )
        //Desc
        OutlinedTextField(
            modifier= Modifier
                .defaultMinSize(0.dp, 200.dp)
                .background(Color.White),
            value = desc,
            onValueChange ={desc=it},
            singleLine = false,
            maxLines = 10,
            placeholder = {
                Text("Description")
            }

        )
        //Type of Task
        Text(
            "Determine Type of Task",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
            )
        Column(
            modifier = Modifier.padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            enumValues<TaskType>().forEach { type ->
                Row(verticalAlignment =Alignment.CenterVertically) {
                    RadioButton(
                        selected = (type == taskType),
                        onClick = { taskType = type }
                    )
                    Text(
                        text = type.name,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }
            Text(
                buildAnnotatedString {
                    append("The Selected Value is: ")
                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.Bold,
                            color = Color.Magenta)
                    ) {
                        append(taskType.name)
                    }
                }
            )
        }

        //Date
        TextButton(
            onClick = {
            mDatePickerDialog.show()
        }, colors = ButtonDefaults.buttonColors(containerColor = Color(0XFF0F9D58)) ) {
            Text(text = "Open Date Picker", color = Color.White)
        }
        // Displaying
        // the mDate value in the Text
        Text(text = "Selected Date: ${date}", fontSize = 24.sp, textAlign = TextAlign.Center)
        //Time
        TextButton(onClick = { mTimePickerDialog.show() }, colors = ButtonDefaults.buttonColors(containerColor = Color(0XFF0F9D58))) {
            Text(text = "Open Time Picker", color = Color.White)
        }

        // Display selected time
        Text(text = "Selected Time: ${time}", fontSize = 24.sp)
        
        
        //Submit
        Spacer(modifier = Modifier.height(10.dp))
        Row {
            OutlinedButton(onClick = { navigateUp() }) {
                Text(text = "Back")
            }
            OutlinedButton(onClick = {
                val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
                val parsedDate=LocalDateTime.parse(date+" "+time,dateFormatter)

                // parse date to YYYY:MM:DD hh:mm:ss Format
                val dateBuilder = StringBuilder()
                dateBuilder.append(parsedDate.year).append("-").append(parsedDate.monthValue.toString().padStart(2,'0')).append("-").append(parsedDate.dayOfMonth.toString().padStart(2,'0'))
                    .append(" ").append(parsedDate.hour.toString().padStart(2,'0')).append(":").append(parsedDate.minute.toString().padStart(2,'0')).append(":").append("00")

                val inputDate = dateBuilder.toString()
                val inputTitle= title.trim()
                val inputDescription=desc.trim()
                val inputdate=dateBuilder.toString()
                val inputType=if(taskType ==TaskType.Important) "Important" else "Regular"
                Log.d("inputDate",inputDate.toString())
                Log.d("inputType",inputType)
                val inputTask =Task(
                    id = task?.id ?:0,
                    title=inputTitle,
                    description = inputDescription,
                    date = inputdate,
                    type = inputType
                )
                onClick(inputTask)
            }) {
                Text(text = "Submit")
            }
        }
    }
}




//
//@Preview(showBackground = true)
//@Composable
//fun TaskFormPreview(){
//    Column(
//        modifier = Modifier.fillMaxSize()
//    ) {
//        TaskForm(navigateUp = {})
//    }
//
//}