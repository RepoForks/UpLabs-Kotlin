package com.kevicsalazar.uplabs.repository

import com.kevicsalazar.uplabs.BuildConfig
import com.kevicsalazar.uplabs.PerApp
import com.kevicsalazar.uplabs.repository.ws.WebServicePosts
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named

/**
 * @author Kevin Salazar
 * @link kevicsalazar.com
 */
@Module
class WebServiceModule {

    @Provides @PerApp fun provideOkHttpClientBuilder(): OkHttpClient.Builder {
        val builder = OkHttpClient().newBuilder()
        builder.readTimeout(15, TimeUnit.SECONDS)
        builder.connectTimeout(5, TimeUnit.SECONDS)
        if (BuildConfig.DEBUG) {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(interceptor)
        }
        return builder
    }

    @Provides @PerApp fun provideOkHttpClient(builder: OkHttpClient.Builder) = builder.addNetworkInterceptor { chain ->
        chain.proceed(chain.request().newBuilder()
                .addHeader("Accept-Charset", "utf-8")
                .addHeader("Accept", "application/json")
                .build())
    }.build()!!

    @Provides @PerApp @Named("uplabs") fun provideUplabsRetrofit(client: OkHttpClient) = Retrofit.Builder()
            .baseUrl("https://www.uplabs.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .client(client)
            .build()!!

    @Provides @PerApp fun provideWebServicePosts(@Named("uplabs") retrofit: Retrofit) = retrofit.create(WebServicePosts.Service::class.java)!!

}
