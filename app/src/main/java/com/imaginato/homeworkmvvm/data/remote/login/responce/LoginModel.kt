package com.imaginato.homeworkmvvm.data.remote.login.responce

data class LoginModel(
	val data: Data? = null,
	val errorMessage: String? = null,
	val errorCode: String? = null
)

data class Data(
	val isDeleted: Boolean? = null,
	val userName: String? = null,
	val userId: String? = null
)

