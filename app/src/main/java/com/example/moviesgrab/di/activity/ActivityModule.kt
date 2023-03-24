package com.example.moviesgrab.di.activity

import android.app.Activity
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

class ActivityModule {


    @Module
    @InstallIn(ActivityComponent::class)
    abstract class ActivityModule {


        companion object {
            @Provides
            fun appCompatActivity(activity: Activity): AppCompatActivity = activity as AppCompatActivity

            @Provides
            fun layoutInflater(activity: Activity) = LayoutInflater.from(activity)

            @Provides
            fun fragmentManager(activity: AppCompatActivity) = activity.supportFragmentManager
        }

    }



}