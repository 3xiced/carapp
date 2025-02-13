package com.taxi.app

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2

class OnboardingActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager2
    private lateinit var nextButton: Button
    private lateinit var skipButton: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)

        viewPager = findViewById(R.id.viewPager)
        nextButton = findViewById(R.id.nextButton)
        skipButton = findViewById(R.id.skipButton)

        val fragments = listOf(
            OnboardingFragment.newInstance(
                R.drawable.carsharing, "Аренда автомобилей", "Открой для себя удобный и доступный способ передвижения"
            ),
            OnboardingFragment.newInstance(
                R.drawable.safety, "Безопасно и удобно", "Арендуй автомобиль и наслаждайся его удобством"
            ),
            OnboardingFragment.newInstance(
                R.drawable.sale, "Лучшие предложения", "Выбирай понравившееся среди сотни доступных автомобилей"
            )
        )

        val adapter = OnboardingPagerAdapter(this, fragments)
        viewPager.adapter = adapter

        nextButton.setOnClickListener {
            if (viewPager.currentItem < fragments.size - 1) {
                viewPager.currentItem += 1
            } else {
                finishOnboarding()
            }
        }

        skipButton.setOnClickListener {
            finishOnboarding()
        }
    }

    private fun finishOnboarding() {
        getSharedPreferences("app_prefs", MODE_PRIVATE).edit()
            .putBoolean("onboarding_complete", true)
            .apply()

        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}