package com.imaginato.homeworkmvvm.ui.login

import org.junit.Assert
import org.junit.Before
import org.junit.Test

class LoginViewTest {

    private lateinit var viewModal : LoginViewModel

    @Before
    fun setUp() {
        viewModal = LoginViewModel()
    }

    @Test
    fun testEmptyUserName() {
        val username = "username"
        Assert.assertTrue(!username.isNullOrBlank())
    }

    @Test
    fun testEmptyPassWord() {
        val username = "1111111"
        Assert.assertTrue(!username.isNullOrBlank())
    }

    @Test
    fun testIsValidPassWord() {
        val password = "1111111"
        Assert.assertTrue(password.length>=8)
    }

}