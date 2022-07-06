package com.dicoding.todoapp.data

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

//TODO 1 : Define a local database table using the schema in app/schema/tasks.json

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    var id: Int = 0,
    @NonNull
    @ColumnInfo(name = "title")
    var title: String="",
    @NonNull
    @ColumnInfo(name = "description")
    var description: String="",
    @NonNull
    @ColumnInfo(name = "dueDateMillis")
    var dueDateMillis: Long=0,
    @NonNull
    @ColumnInfo(name = "completed")
    var isCompleted: Boolean = false
)
