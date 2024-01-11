package com.imaginato.homeworkmvvm.ui.login

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.imaginato.homeworkmvvm.data.remote.login.LoginRepository
import com.imaginato.homeworkmvvm.data.remote.login.responce.LoginModel
import com.imaginato.homeworkmvvm.ui.base.BaseViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.inject


@KoinApiExtension
class LoginViewModel(
) : BaseViewModel() {
    private val repository: LoginRepository by inject()
    private var _resultLiveData: MutableLiveData<LoginModel?> = MutableLiveData()
    private var _headerData: MutableLiveData<String?> = MutableLiveData()
    private var _progress: MutableLiveData<Boolean> = MutableLiveData()
    var user: MutableLiveData<LoginModel>? = null
    private var errorMessage: MutableLiveData<String>? = null
    val progress: LiveData<Boolean>
        get() {
            return _progress
        }

    val resultLiveData: LiveData<LoginModel?>
        get() {
            return _resultLiveData
        }
    val headerData: LiveData<String?>
        get() {
            return _headerData
        }
    /**
     * Do something and handle business logic here
     * */

    fun getLoginData(body: HashMap<String, Any>) {
        viewModelScope.launch {
            repository.getLoginData(body)
                .onStart {
                    _progress.value = true
                }
                .catch {
                    _progress.value = false
                }
                .onCompletion {
                }
                .collect { (loginModel, headers) ->
                    _progress.value = false
                    _resultLiveData.value = loginModel
                    _headerData.value = headers
                }
        }

    }


    suspend fun login(body: HashMap<String, Any>?) {
        try {
            body?.let {
                val loggedInUser: Flow<Pair<LoginModel?, String?>> = repository.getLoginData(body )
                loggedInUser .onStart {
                        _progress.value = true
                    }
                    .catch {
                        _progress.value = false
                    }
                    .onCompletion {
                    }
                    .collect { (loginModel, headers) ->
                        user?.setValue(loginModel)
                    }
                errorMessage!!.setValue(null)
            }

        } catch (e: Exception) {
            user!!.setValue(null)
            errorMessage!!.setValue(e.message)
        }
    }


}