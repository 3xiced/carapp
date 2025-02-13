package com.taxi.app

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

object NetworkUtils {
    suspend fun isInternetAvailable(): Boolean {
        return withContext(Dispatchers.IO) {  // Выполняем в фоновом потоке
            try {
                val url = URL("https://www.google.com")
                val connection = url.openConnection() as HttpURLConnection
                connection.connectTimeout = 2000
                connection.readTimeout = 2000
                connection.requestMethod = "HEAD"
                connection.responseCode == HttpURLConnection.HTTP_OK
            } catch (e: IOException) {
                false
            }
        }
    }
}