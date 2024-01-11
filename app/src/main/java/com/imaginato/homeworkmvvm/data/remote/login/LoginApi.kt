package com.imaginato.homeworkmvvm.data.remote.login

import com.imaginato.homeworkmvvm.data.remote.login.responce.LoginModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Url

interface LoginApi {
    @POST
    suspend fun loginAsync(@Url url: String, @Body body: HashMap<String, Any>): Response<LoginModel>

}