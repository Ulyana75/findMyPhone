package com.ulyanaab.findmyphone.model

import com.ulyanaab.findmyphone.model.objects.UserModel
import com.ulyanaab.findmyphone.model.objects.UserResponse

interface RepositoryUser {

    fun sendData(user: UserModel, callback: () -> Unit)

}