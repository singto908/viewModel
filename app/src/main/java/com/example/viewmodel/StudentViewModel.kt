package com.example.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

class StudentViewModel: ViewModel() {
    var data =  mutableStateListOf<StudentModel>()

    fun addStudent(name:String, studentId:String){
        var count:Int = data.count()
        data.add(
            StudentModel(count++,name,studentId)
        )
    }
}