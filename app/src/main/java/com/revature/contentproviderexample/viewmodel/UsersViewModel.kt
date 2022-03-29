package com.revature.contentproviderexample.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.revature.contentproviderexample.model.data.UserItem
import com.revature.contentproviderexample.model.data.UserRepository
import kotlinx.coroutines.launch

class UsersViewModel(app:Application): AndroidViewModel(app) {

    private val userRepo:UserRepository =
        UserRepository(app)

    fun fetchAllUsers() : LiveData<List<UserItem>> {
        return userRepo.allUsers
    }

    fun insertUser(user:UserItem) {
        viewModelScope.launch {
            userRepo.insertUser(user)
        }
    }

    fun deleteUserById(id:Int){
        viewModelScope.launch {
            userRepo.deleteUserById(id)
        }
    }

    fun updateUserById(
        id:Int,
        sName:String? = null,
        sEmail:String? = null,
        nUserId:Int? = null ) {
        viewModelScope.launch {
            userRepo.updateUserById(id = id,
                sName = sName,
                sEmail = sEmail,
                nUserId = nUserId)
        }
    }


}