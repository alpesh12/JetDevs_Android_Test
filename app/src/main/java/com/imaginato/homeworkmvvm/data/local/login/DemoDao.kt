package com.imaginato.homeworkmvvm.data.local.login

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.imaginato.homeworkmvvm.data.local.demo.Demo

@Dao
interface DemoDao {

    // Fetches all User Details
    @Query("SELECT * FROM user_db")
    fun getAllUsers(): Demo

    // Insert User to DataTable
    @Insert
    fun insertDemo(user: Demo)

}