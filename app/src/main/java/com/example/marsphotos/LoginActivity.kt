package com.example.marsphotos

import android.app.Activity
import android.os.Bundle
import com.example.marsphotos.data.LoginComponentProvider
import com.example.marsphotos.data.LoginViewModel
import javax.inject.Inject

class LoginActivity : Activity() {

    // Inyección del ViewModel con Dagger
    @Inject
    lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Obtener el componente de la aplicación y crear el subcomponente
        val loginComponent = (applicationContext as LoginComponentProvider)
            .provideLoginComponent()

        // Inyectar dependencias en esta actividad
        loginComponent.inject(this)

        // Ya puedes usar loginViewModel
        println("LoginViewModel inyectado: $loginViewModel")
    }
}
