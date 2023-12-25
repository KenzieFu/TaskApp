package com.example.taskapp.ui.util

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.graphics.Paint.Align
import android.icu.util.Calendar
import android.widget.Button
import android.widget.DatePicker
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
import com.example.taskapp.model.TaskType
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskForm(navigateUp:()->Unit){
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

    var date by remember { mutableStateOf<String>("") }

    // Declaring DatePickerDialog and setting
    // initial values as current values (present year, month and day)
    val mDatePickerDialog = DatePickerDialog(
        mContext,
        { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
            date = "$mDayOfMonth/${mMonth+1}/$mYear"
        }, mYear, mMonth, mDay
    )

    var time by remember { mutableStateOf<String>("") }

    // Creating a TimePicker dialod
    val mTimePickerDialog = TimePickerDialog(
        mContext,
        {_, mHour : Int, mMinute: Int ->
            time = "$mHour:$mMinute"
        }, mHour, mMinute, false
    )

    var title by remember { mutableStateOf<String>("") }
    var desc by remember { mutableStateOf<String>("") }


    var taskType by remember { mutableStateOf<TaskType>(TaskType.Regular) }
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
            OutlinedButton(onClick = { /*TODO*/ }) {
                Text(text = "Submit")
            }
        }
    }
}





@Preview(showBackground = true)
@Composable
fun TaskFormPreview(){
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TaskForm(navigateUp = {})
    }

}