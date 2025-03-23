package com.taxi.app

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment

class SettingsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val itemTheme = view.findViewById<LinearLayout>(R.id.item_theme)
        itemTheme.setOnClickListener {
            // Получаем текущий режим
            val currentNightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
            if (currentNightMode == Configuration.UI_MODE_NIGHT_NO) {
                // Если светлая тема, переключаем на темную
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                // Если темная, переключаем на светлую
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
            // Перезапускаем фрагмент или активность, чтобы применить изменения
            requireActivity().recreate()
        }

        val itemProfile = view.findViewById<LinearLayout>(R.id.item_profile)
        itemProfile.setOnClickListener {
            startActivity(Intent(requireContext(), ProfileActivity::class.java))
        }

        val itemNotification = view.findViewById<LinearLayout>(R.id.item_notifications)
        itemNotification.setOnClickListener {
            startActivity(Intent(requireContext(), NotificationsActivity::class.java))
        }

        val itemHelp = view.findViewById<LinearLayout>(R.id.item_help)
        itemHelp.setOnClickListener {
            startActivity(Intent(requireContext(), HelpActivity::class.java))
        }

        val itemFriend = view.findViewById<LinearLayout>(R.id.item_invite)
        itemFriend.setOnClickListener {
            startActivity(Intent(requireContext(), InviteFriendActivity::class.java))
        }
    }
}
