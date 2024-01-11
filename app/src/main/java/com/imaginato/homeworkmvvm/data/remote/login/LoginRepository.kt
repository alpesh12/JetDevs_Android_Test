package com.imaginato.homeworkmvvm.data.remote.login

import com.imaginato.homeworkmvvm.data.remote.login.responce.LoginModel
import kotlinx.coroutines.flow.Flow
import java.util.HashMap

interface LoginRepository {

    suspend fun getLoginData(body: HashMap<String, Any>): Flow<Pair<LoginModel?, String?>>

}