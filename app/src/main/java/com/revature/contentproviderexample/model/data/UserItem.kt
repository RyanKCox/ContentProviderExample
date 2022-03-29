package com.revature.contentproviderexample.model.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class UserItem(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "email")
    val email:String? = null,

    @ColumnInfo(name = "name")
    val name:String? = null,

    @ColumnInfo(name = "user_id")
    val userId:Int? = null
    )
