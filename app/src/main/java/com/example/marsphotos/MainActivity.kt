// MainActivity.kt
package com.example.marsphotos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.marsphotos.data.LoginComponentProvider
import com.example.marsphotos.data.LoginViewModel
import com.example.marsphotos.ui.MarsPhotosApp
import com.example.marsphotos.ui.theme.MarsPhotosTheme
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    @Inject lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        (applicationContext as LoginComponentProvider)
            .provideLoginComponent()
            .inject(this)

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            MarsPhotosTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    MarsPhotosApp()
                }
            }
        }
    }
}
