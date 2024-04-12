package com.sina.efood.core.di.module

import android.content.Context
import com.sina.efood.core.remote.connectivity.NetworkListener
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(FragmentComponent::class)
object AppModule {
    @Provides
    fun provideNetworkListener(@ApplicationContext context: Context) = NetworkListener(context)
}