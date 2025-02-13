package com.taxi.app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager2.widget.ViewPager2

class OnboardingFragment : Fragment() {
    private var imageRes: Int = 0
    private var title: String? = null
    private var description: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            imageRes = it.getInt(ARG_IMAGE)
            title = it.getString(ARG_TITLE)
            description = it.getString(ARG_DESCRIPTION)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_onboarding, container, false)

        val imageView = view.findViewById<ImageView>(R.id.imageView)
        val titleTextView = view.findViewById<TextView>(R.id.titleTextView)
        val descriptionTextView = view.findViewById<TextView>(R.id.descriptionTextView)

        imageView.setImageResource(imageRes)
        titleTextView.text = title
        descriptionTextView.text = description

        return view
    }

    companion object {
        private const val ARG_IMAGE = "image"
        private const val ARG_TITLE = "title"
        private const val ARG_DESCRIPTION = "description"

        fun newInstance(imageRes: Int, title: String, description: String) =
            OnboardingFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_IMAGE, imageRes)
                    putString(ARG_TITLE, title)
                    putString(ARG_DESCRIPTION, description)
                }
            }
    }
}