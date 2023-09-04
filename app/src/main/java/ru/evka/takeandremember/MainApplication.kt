package ru.evka.takeandremember

import android.app.Application
import androidx.work.Configuration


class MainApplication : Application(), Configuration.Provider {
    override fun getWorkManagerConfiguration(): Configuration =
        Configuration.Builder()
            .setMinimumLoggingLevel(/*if (BuildConfig.DEBUG) */android.util.Log.DEBUG/* else android.util.Log.ERROR*/)
            .build()
}