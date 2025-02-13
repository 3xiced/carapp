package com.taxi.app
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.content.Intent
import android.os.Bundle
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import android.widget.ImageView
import android.widget.TextView
import kotlinx.coroutines.*

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val titleText = findViewById<TextView>(R.id.titleText)
        val subtitleText = findViewById<TextView>(R.id.subtitleText)
        val logoImage = findViewById<ImageView>(R.id.logoImage)

        // Анимация появления
        ObjectAnimator.ofPropertyValuesHolder(
            titleText,
            PropertyValuesHolder.ofFloat("alpha", 0f, 1f),
            PropertyValuesHolder.ofFloat("translationX", -50f, 0f)
        ).apply {
            duration = 800
            interpolator = AccelerateDecelerateInterpolator()
            start()
        }

        ObjectAnimator.ofPropertyValuesHolder(
            subtitleText,
            PropertyValuesHolder.ofFloat("alpha", 0f, 1f),
            PropertyValuesHolder.ofFloat("translationX", -50f, 0f)
        ).apply {
            duration = 1000
            startDelay = 300
            interpolator = AccelerateDecelerateInterpolator()
            start()
        }

        ObjectAnimator.ofFloat(logoImage, "alpha", 0f, 1f).apply {
            duration = 1200
            startDelay = 600
            start()
        }

        // Запускаем проверку интернета через корутину
        CoroutineScope(Dispatchers.Main).launch {
            delay(3000) // Ждем завершения анимации
            val isConnected = withContext(Dispatchers.IO) { NetworkUtils.isInternetAvailable() }
            print(isConnected);
            if (isConnected) {
                val sharedPrefs = getSharedPreferences("app_prefs", MODE_PRIVATE)
//                val isOnboardingComplete = sharedPrefs.getBoolean("onboarding_complete", false)
                val isOnboardingComplete = false;
                val nextActivity = if (isOnboardingComplete) MainActivity::class.java else OnboardingActivity::class.java
                startActivity(Intent(this@SplashActivity, nextActivity))
            } else {
                startActivity(Intent(this@SplashActivity, NoInternetActivity::class.java))
            }
            finish()
        }
    }
}