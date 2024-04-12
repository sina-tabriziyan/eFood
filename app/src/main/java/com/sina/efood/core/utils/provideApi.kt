package com.sina.efood.core.utils

import retrofit2.Retrofit

inline fun <reified T> provideApi(retrofit: Retrofit): T {
    return retrofit.create(T::class.java)
}