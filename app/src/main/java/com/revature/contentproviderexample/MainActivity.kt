package com.revature.contentproviderexample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import com.revature.contentproviderexample.ui.theme.ContentProviderExampleTheme
import com.revature.contentproviderexample.view.DisplayUsersScreen
import com.revature.contentproviderexample.viewmodel.UsersViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val usersViewModel by lazy { 
            ViewModelProvider(this).get(
                UsersViewModel::class.java)}
        
        setContent {
            ContentProviderExampleTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    DisplayUsersScreen(userViewModel = usersViewModel)
                    
                }
            }
        }
    }
}