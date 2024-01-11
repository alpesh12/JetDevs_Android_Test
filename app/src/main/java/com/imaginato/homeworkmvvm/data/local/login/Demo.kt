package com.imaginato.homeworkmvvm.data.local.demo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_db")
data class Demo constructor(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val token: String
)
