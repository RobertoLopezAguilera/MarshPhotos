package com.example.marsphotos.data

import com.example.marsphotos.LoginActivity
import com.example.marsphotos.MainActivity
import com.example.marsphotos.model.MarsPhoto
import com.example.marsphotos.network.MarsApi
import com.example.marsphotos.network.MarsApiService
import com.google.firebase.firestore.core.ActivityScope
import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import javax.inject.Inject
import javax.inject.Singleton

interface MarsPhotosRepository {
    suspend fun getMarsPhotos(): List<MarsPhoto>
}

interface LoginComponentProvider {
    fun provideLoginComponent(): LoginComponent
}

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun loginComponent(): LoginComponent.Factory
    fun marsPhotosRepository(): MarsPhotosRepository
}

@Subcomponent
interface LoginComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): LoginComponent
    }

    fun inject(activity: MainActivity)
    fun inject(activity: LoginActivity)
}

/*@Contract(pure = true)
@ActivityScope*/
class LoginViewModel @Inject constructor(
    private val userRepository: MarsPhotosRepository
) {  }

class NetworkMarsPhotosRepository(
    private val marsApiService: MarsApiService
) : MarsPhotosRepository {
    /** Fetches list of MarsPhoto from marsApi*/
    override suspend fun getMarsPhotos(): List<MarsPhoto> = marsApiService.getPhotos()
}

@Module
object AppModule {

    @Provides
    @Singleton
    fun provideMarsApiService(): MarsApiService {
        return MarsApi.retrofitService
    }

    @Provides
    @Singleton
    fun provideMarsPhotosRepository(
        marsApiService: MarsApiService
    ): MarsPhotosRepository {
        return NetworkMarsPhotosRepository(marsApiService)
    }
}
