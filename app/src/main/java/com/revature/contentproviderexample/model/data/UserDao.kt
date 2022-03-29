package com.revature.contentproviderexample.model.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {

    @Query("SELECT * FROM user")
    fun fetchAllUsers(): LiveData<List<UserItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user:UserItem)

    @Query("DELETE FROM user WHERE id= :id")
    suspend fun deleteUserById(id:Int)

    @Query("UPDATE user SET name = :sName WHERE id= :id")
    suspend fun updateUserNameById(id:Int, sName:String)

    @Query("UPDATE user SET user_id = :userId WHERE id= :id")
    suspend fun updateUserIdById(id:Int, userId:Int)

    @Query("UPDATE user SET email = :sEmail WHERE id= :id")
    suspend fun updateUserEmailById(id:Int, sEmail:String)
}