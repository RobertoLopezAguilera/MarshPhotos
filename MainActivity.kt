/*
 * Copyright (C) 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.marsphotos

import android.app.Activity //
import android.app.Application //
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.marsphotos.data.LoginComponent
import com.example.marsphotos.data.LoginComponentProvider //
import com.example.marsphotos.ui.MarsPhotosApp
import com.example.marsphotos.ui.theme.MarsPhotosTheme
import jakarta.inject.Inject
import jakarta.inject.Singleton


class LoginActivity: Activity() {

    // You want Dagger to provide an instance of LoginViewModel from the Login graph
    @Inject lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        // Gets appComponent from MyApplication available in the base Gradle module
        val appComponent = (applicationContext as MyApplication).appComponent

        // Creates a new instance of LoginComponent
        // Injects the component to populate the @Inject fields
       // DaggerLoginComponent.factory().create(appComponent).inject(this)

        super.onCreate(savedInstanceState)

        // Now you can access loginViewModel
    }
}

// LoginViewModel depends on UserRepository that is scoped to AppComponent
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository
) {  }
// UserRepository's dependencies
class UserLocalDataSource @Inject constructor() {  }
class UserRemoteDataSource @Inject constructor() {  }

// UserRepository is scoped to AppComponent
@Singleton
class UserRepository @Inject constructor(
    private val localDataSource: UserLocalDataSource,
    private val remoteDataSource: UserRemoteDataSource
) {  }

class MyApplication: Application(), LoginComponentProvider {
    // Reference to the application graph that is used across the whole app
    val appComponent = DaggerApplicationComponent.create()
   //Causa error el create

    override fun provideLoginComponent(): LoginComponent {
        return appComponent.loginComponent().create()
    }
}

class DaggerApplicationComponent {

}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        var loginComponent = (applicationContext as LoginComponentProvider)
            .provideLoginComponent()

        loginComponent.inject(this)
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            MarsPhotosTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    MarsPhotosApp()
                }
            }
        }
    }
}
