package com.taxi.app

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class InviteFriendActivity : AppCompatActivity() {

    private lateinit var tvReferralCode: TextView
    private lateinit var btnCopyCode: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_invite_friend)

        tvReferralCode = findViewById(R.id.tvReferralCode)
        btnCopyCode = findViewById(R.id.btnCopyCode)

        btnCopyCode.setOnClickListener {
            val referralText = tvReferralCode.text.toString().replace("Ваш реферальный код: ", "")
            copyToClipboard(referralText)
            Toast.makeText(this, "Код скопирован: $referralText", Toast.LENGTH_SHORT).show()
        }
    }

    private fun copyToClipboard(text: String) {
        val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("Referral Code", text)
        clipboard.setPrimaryClip(clip)
    }
}
