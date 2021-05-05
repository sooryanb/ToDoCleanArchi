package com.example.todocleanarchi.data.repository

import androidx.lifecycle.LiveData
import com.example.todocleanarchi.data.ToDoDao
import com.example.todocleanarchi.data.models.ToDoData

class ToDoRepository(private val toDoDoa: ToDoDao) {

    val getAllData: LiveData<List<ToDoData>> = toDoDoa.getAllData()

    suspend fun insertData(toDoData: ToDoData){
        toDoDoa.insertData(toDoData)
    }

    suspend fun updateData(toDoData: ToDoData){
        toDoDoa.updateData(toDoData)
    }

    suspend fun deleteItem(toDoData: ToDoData){
        toDoDoa.deleteItem(toDoData)
    }

    suspend fun deleteAll(){
        toDoDoa.deleteAll()
    }

}