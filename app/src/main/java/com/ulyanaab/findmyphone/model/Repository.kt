package com.ulyanaab.findmyphone.model

interface Repository {

    fun sendData(data: List<PhoneMetrics>, callback: () -> Unit)

}