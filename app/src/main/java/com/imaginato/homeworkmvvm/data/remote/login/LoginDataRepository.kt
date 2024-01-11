package com.imaginato.homeworkmvvm.data.remote.login

import com.imaginato.homeworkmvvm.data.remote.login.responce.LoginModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class LoginDataRepository constructor(
    private var api: LoginApi
) : LoginRepository {
    companion object {
        private const val URL_GET_PUBLIC_IP = "https://private-222d3-homework5.apiary-mock.com/api/login"
        private const val HEADER_X_ACC = "X-Acc"
    }

    override suspend fun getLoginData(body: HashMap<String, Any>): Flow<Pair<LoginModel?, String?>> = flow {
        val response = api.loginAsync(URL_GET_PUBLIC_IP, body)
        val loginModel = response.body()
        val xAccHeaderValue = response.headers()[HEADER_X_ACC]
        emit(loginModel to xAccHeaderValue)
    }.flowOn(Dispatchers.IO)
}