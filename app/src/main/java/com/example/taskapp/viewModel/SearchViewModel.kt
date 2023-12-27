package com.example.taskapp.viewModel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SearchViewModel : ViewModel(){
    private var _tasksUiState = MutableStateFlow<TasksUiState>(TasksUiState.Loading)
    var tasksUiState: StateFlow<TasksUiState> = _tasksUiState
}
