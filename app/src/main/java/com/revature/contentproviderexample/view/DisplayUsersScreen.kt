package com.revature.contentproviderexample.view

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import com.revature.contentproviderexample.viewmodel.UsersViewModel
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.revature.contentproviderexample.R
import com.revature.contentproviderexample.model.data.UserItem

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
                LazyColumn(
                    modifier = Modifier
                        .fillMaxHeight(.7f),
                    horizontalAlignment = Alignment.CenterHorizontally){
                    items(userList.value){
                        userCard(user = it, userViewModel)
                    }
                }
                Button(
                    modifier = Modifier
                        .fillMaxWidth(.6f),
                    onClick = {
                        userViewModel.insertUser(
                            UserItem(
                                id = 0,
                                email = "random@random.com",
                                name = "Random User",
                                userId = 10
                        ))

                        Toast.makeText(
                            context,
                            "Do Things",
                            Toast.LENGTH_SHORT
                        ).show()

                }) {
                    Text(text = "Add Customer")

                }
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