package com.ulyanaab.findmyphone.model

import com.ulyanaab.findmyphone.model.objects.UserModel

interface RepositoryUser {

    fun sendData(user: UserModel, onSuccess: () -> Unit, onFailure: () -> Unit)

}