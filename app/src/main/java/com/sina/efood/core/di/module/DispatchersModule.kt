package com.sina.efood.core.di.module

import com.sina.efood.core.di.annotation.CpuDispatcher
import com.sina.efood.core.di.annotation.DefaultDispatcher
import com.sina.efood.core.di.annotation.IODispatcher
import com.sina.efood.core.di.annotation.MainDispatcher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
object DispatchersModule {
    @Provides
    @IODispatcher
    fun providesIODispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    @MainDispatcher
    fun providesMainDispatcher(): CoroutineDispatcher = Dispatchers.Main

    @Provides
    @DefaultDispatcher
    fun providesDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

    @Provides
    @CpuDispatcher
    fun providesCpuDispatcher(): CoroutineDispatcher = Dispatchers.Unconfined
}
