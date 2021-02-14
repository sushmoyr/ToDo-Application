package com.sushmoyr.todoapplication.data.repository

import androidx.lifecycle.LiveData
import com.sushmoyr.todoapplication.data.ToDoDao
import com.sushmoyr.todoapplication.data.model.ToDoData

class ToDoRepository(private val toDoDao: ToDoDao) {
    val getAllData: LiveData<List<ToDoData>> = toDoDao.getAllData()

    suspend fun insertData(toDoData: ToDoData) {
        toDoDao.insertData(toDoData)
    }
}