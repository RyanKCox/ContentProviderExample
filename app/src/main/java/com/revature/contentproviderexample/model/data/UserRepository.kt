package com.revature.contentproviderexample.model.data

import android.app.Application
import androidx.lifecycle.LiveData

class UserRepository(app:Application) {

    private var userDao: UserDao

    init {
        var database = UserDatabase.getDatabase(app)
        userDao = database.userDao()
    }

    val allUsers: LiveData<List<UserItem>> =
        userDao.fetchAllUsers()

    suspend fun deleteUserById(id:Int){
        userDao.deleteUserById(id)
    }

    suspend fun insertUser(user:UserItem){
        userDao.insertUser(user)
    }

    suspend fun updateUserById(
        id:Int,
        sName:String? = null,
        sEmail:String? = null,
        nUserId:Int? = null ) {
        if (sName != null)
            userDao.updateUserNameById(id, sName)
        if (sEmail != null)
            userDao.updateUserEmailById(id, sEmail)
        if (nUserId != null)
            userDao.updateUserIdById(id, nUserId)
    }
}