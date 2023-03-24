package com.example.moviesgrab.di.app

import android.content.Context
import androidx.room.Room
import com.example.moviesgrab.db.SavedArticlesDB
import com.example.moviesgrab.di.ForRetrofit
import com.example.moviesgrab.networking.TMDBApi
import com.example.moviesgrab.networking.UrlProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @AppScope
    @ForRetrofit
    fun retrofit_build(urlProvider: UrlProvider): Retrofit {
        return Retrofit.Builder()
            .baseUrl(urlProvider.getBaseUrl())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


    @AppScope
    @Provides
    fun urlProvider() = UrlProvider()

    @Provides
    @AppScope
    fun TMDBApi(@ForRetrofit retrofit: Retrofit) = retrofit.create(TMDBApi::class.java)


    @Singleton // Tell Dagger-Hilt to create a singleton accessible everywhere in ApplicationCompenent (i.e. everywhere in the application)
    @Provides
    fun provideYourDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        SavedArticlesDB::class.java,
        "saved_items_db.db"
    ).build() // The reason we can construct a database for the repo

    @Singleton
    @Provides
    fun provideYourDao(db: SavedArticlesDB) = db.getSavedItemDao() // The reason we can implement a Dao for the database



}