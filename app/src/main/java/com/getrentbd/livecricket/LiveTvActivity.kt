package com.getrentbd.livecricket

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.webkit.*
import androidx.appcompat.app.AppCompatActivity
import org.jsoup.Jsoup
import java.io.IOException

class LiveTvActivity : AppCompatActivity() {
    private lateinit var webView: WebView
    private var link: String? = null
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_live_tv)

        webView = findViewById(R.id.webView)

        link = intent.getStringExtra("link")
        val webSettings = webView.settings
        webSettings.javaScriptEnabled = true
        webView.settings.javaScriptEnabled = true
        webView.loadUrl(link!!)
        //getparsedData(link);
    }

    private fun getparsedData(url: String) {
        try {
            val document = Jsoup.connect(url).get()
            //document.getElementsByClass("td-search-wrap-mob").remove();
//            document.getElementsByClass("col-md-12 col-sm-12").remove();
//            document.getElementsByClass("pre-footer").remove();
//            document.getElementsByClass("footer").remove();
//            document.getElementsByClass("header").remove();
            val ws = webView.settings
            ws.javaScriptEnabled = true
            webView.loadDataWithBaseURL(url, document.toString(), "text/html", "utf-8", "")
            webView.webViewClient = MyWebviewClient()
            webView.settings.javaScriptEnabled = true
            webView.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
                if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
                    webView.goBack()
                    return@OnKeyListener true
                }
                false
            })
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    inner class MyWebviewClient : WebViewClient() {
        override fun onPageStarted(view: WebView, url: String, favicon: Bitmap) {
            super.onPageStarted(view, url, favicon)
            webView.visibility = View.VISIBLE
        }

        override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
            return super.shouldOverrideUrlLoading(view, request)
        }

        override fun onPageFinished(view: WebView, url: String) {
            super.onPageFinished(view, url)
            webView.visibility = View.VISIBLE
        }

        override fun onReceivedError(
            view: WebView,
            request: WebResourceRequest,
            error: WebResourceError
        ) {
            super.onReceivedError(view, request, error)
        }
    }
}