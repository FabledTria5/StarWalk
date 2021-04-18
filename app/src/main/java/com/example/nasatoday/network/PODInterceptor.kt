package com.example.nasatoday.network

import okhttp3.Interceptor
import okio.IOException

class PODInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain) = chain.proceed(chain.request())
}