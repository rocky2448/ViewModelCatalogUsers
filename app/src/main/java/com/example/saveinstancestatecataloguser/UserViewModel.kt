package com.example.saveinstancestatecataloguser

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UserViewModel: ViewModel() {
    val users: MutableList<User> = mutableListOf()

    val currentUser: MutableLiveData<List<User>> by lazy {
        MutableLiveData<List<User>>(mutableListOf())
    }

    init {
        currentUser.value = users
    }

    fun addUser(user: User) {
        users.add(user)
    }
}