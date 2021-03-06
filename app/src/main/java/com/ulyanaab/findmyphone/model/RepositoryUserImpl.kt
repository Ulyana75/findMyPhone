package com.ulyanaab.findmyphone.model

import com.ulyanaab.findmyphone.model.objects.UserModel
import com.ulyanaab.findmyphone.model.remote.RemoteDataStorage
import com.ulyanaab.findmyphone.model.remote.RetrofitRemoteDataStorage

class RepositoryUserImpl : RepositoryUser {

    private val remoteDataStorage: RemoteDataStorage = RetrofitRemoteDataStorage()

    override fun sendData(user: UserModel, onSuccess: () -> Unit, onFailure: () -> Unit) {
        remoteDataStorage.sendUser(user, onSuccess, onFailure)
    }

}