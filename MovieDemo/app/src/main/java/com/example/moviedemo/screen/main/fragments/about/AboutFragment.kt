package com.example.moviedemo.screen.main.fragments.about

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.moviedemo.base.BaseFragment
import com.example.moviedemo.databinding.FragmentAboutBinding
import javax.inject.Inject

class AboutFragment : BaseFragment() {
    @Inject
    lateinit var viewModel: AboutViewModel

    lateinit var binding: FragmentAboutBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentAboutBinding.inflate(inflater, container, false)

        binding.webview.apply {
            webViewClient = MyWebViewClient(context)
            loadUrl(viewModel.BASE_URL)
            settings.javaScriptEnabled = true
        }
        return binding.root

    }


}