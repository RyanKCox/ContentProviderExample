package com.revature.contentproviderexample.view

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.getSystemService
import com.revature.contentproviderexample.RevatureContentProvider
import com.revature.contentproviderexample.model.data.UserItem
import com.revature.contentproviderexample.viewmodel.UsersViewModel
import androidx.lifecycle.LiveData


@SuppressLint("Range")
@Composable
fun DisplayUsersScreen(userViewModel:UsersViewModel){

    val scaffoldState = rememberScaffoldState()
    val context = LocalContext.current

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = { TopAppBar(title = {Text("Users")})},
        content = {

            val userList = userViewModel.fetchAllUsers().observeAsState(arrayListOf())

            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Spacer(Modifier.size(5.dp))

                var sNewName by rememberSaveable { mutableStateOf("") }
                var sResult by rememberSaveable { mutableStateOf("") }

                TextField(
                    value = sNewName,
                    onValueChange = {sNewName = it},
                    modifier = Modifier
                        .padding(5.dp),
                    label = {
                        Text("Enter Name:")
                    },
                    textStyle = MaterialTheme.typography.body1
                )

                Spacer(Modifier.size(5.dp))

                //Add customer button
                Button(
                    modifier = Modifier
                        .fillMaxWidth(.6f),
                    onClick = {
//                        userViewModel.insertUser(
//                            UserItem(
//                                id = 0,
//                                email = "$sNewName@random.com",
//                                name = sNewName,
//                                userId = 10
//                        ))

                        val values= ContentValues()

                        values.put(RevatureContentProvider.name,sNewName)

                        context.contentResolver.insert(RevatureContentProvider.CONTENT_URI ,values)

                        sNewName = ""

                        Toast.makeText(
                            context,
                            "User Created",
                            Toast.LENGTH_SHORT
                        ).show()

                }) {
                    Text(text = "Add Customer")

                }

                Spacer(Modifier.size(5.dp))

                Button(
                    modifier = Modifier
                        .fillMaxWidth(.6f),
                    onClick = {

                        val cursor = context.contentResolver.query(
                            RevatureContentProvider.CONTENT_URI,
                            null,null,null,null
                        )
                        if ( cursor!!.moveToFirst()){

                            val strBuild = StringBuilder()
                            var sArray = arrayListOf<String>()

                            while (!cursor.isAfterLast){

                                //nameList.add(cursor.getString(cursor.getColumnIndex("name")))

                                sResult = "${cursor.getString(
                                    cursor.getColumnIndex("id"))}-${cursor.getString(cursor.getColumnIndex("name"))}"
                                cursor.moveToNext()


                                Log.d("Data","$sResult")
                            }
                        }
                        else {
                            Log.d("Data","No Records Found")
                        }
                    }) {
                    Text(text = "Show Records")

                }

                Spacer(Modifier.size(5.dp))

//                LazyColumn(
//                    modifier = Modifier
//                        .fillMaxHeight(.7f),
//                    horizontalAlignment = Alignment.CenterHorizontally){
//                    items(nameList.size){
//                        nameCard(sName = nameList[it])
//                        //userCard(user = it, userViewModel)
//                    }
//                }
            }
        }
    )
}

@Composable
fun nameCard(sName:String){
    Card(
        modifier = Modifier
            .fillMaxWidth(.9f)
            .padding(5.dp),
        content = {

            Row{
                Text("Name:")
                Spacer(modifier = Modifier.size(20.dp))
                Text(sName)
            }

        }
    )
}

@Composable
fun userCard(user:UserItem, userViewModel:UsersViewModel){

    Card(
        modifier = Modifier
            .fillMaxWidth(.9f)
            .padding(5.dp),
        content = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ){
                Column {

                    Text("Name:")

                    Spacer(Modifier.size(5.dp))

                    Text("Email:")

                    Spacer(Modifier.size(5.dp))

                    Text("User ID:")
                }
                Spacer(Modifier.size(20.dp))

                Column {

                    Text("${user.name}")

                    Spacer(Modifier.size(5.dp))

                    Text("${user.email}")

                    Spacer(Modifier.size(5.dp))

                    Text("${user.userId}")

                }

                Image(
                    Icons.Default.Close,
                    contentDescription = null,
                    modifier = Modifier
                        .clickable {
                            userViewModel.deleteUserById(user.id)
                        }
                        .fillMaxWidth(),
                    alignment = Alignment.TopEnd
                )

            }
        }
    )
}