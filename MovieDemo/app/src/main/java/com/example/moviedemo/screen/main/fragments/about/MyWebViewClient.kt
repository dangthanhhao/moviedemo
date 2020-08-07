package com.example.moviedemo.screen.main.fragments.about

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.webkit.WebView
import android.webkit.WebViewClient

class MyWebViewClient(val context: Context) : WebViewClient() {
    //start intent to other browser if they navigate to other website in WebView
    override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
        if (Uri.parse(url).host.equals("www.themoviedb.org")) {
            return false
        }
        Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply { context.startActivity(this) }
        return true
    }
}
