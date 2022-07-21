package org.android.turnaround.presentation.login

import android.annotation.TargetApi
import android.app.Dialog
import android.content.Context
import android.net.http.SslError
import android.os.Build
import android.os.Bundle
import android.os.Message
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import android.widget.Toast
import org.android.turnaround.R
import org.android.turnaround.databinding.FragmentAddressInfoInputBinding
import org.android.turnaround.presentation.base.BaseFragment


class AddressInfoInputFragment : BaseFragment<FragmentAddressInfoInputBinding>(R.layout.fragment_address_info_input) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.etAddress.setOnClickListener{
            initAddressWebView()
        }
    }

    private fun initAddressWebView() {
        binding.wvDaumAddress.settings.apply {
            javaScriptEnabled = true
            javaScriptCanOpenWindowsAutomatically = true
            setSupportMultipleWindows(true)
            domStorageEnabled = true
        }
        binding.wvDaumAddress.addJavascriptInterface(WebAppInterface(requireContext()),"Android")
        binding.wvDaumAddress.webChromeClient = chromeClient
        binding.wvDaumAddress.webViewClient = webViewClient
        binding.wvDaumAddress.loadUrl("file:///android_asset/daum.html")

    }
    private val webViewClient = object  : WebViewClient() {

        override fun shouldOverrideUrlLoading(
            view: WebView?,
            request: WebResourceRequest?
        ): Boolean {
            return false
        }

        override fun onLoadResource(view: WebView?, url: String?) {
            super.onLoadResource(view, url)
            binding.wvDaumAddress.loadUrl("javascript:sample2_execDaumPostcode();")
        }

    }

    private val chromeClient = object : WebChromeClient() {

        override fun onCreateWindow(view: WebView?, isDialog: Boolean, isUserGesture: Boolean, resultMsg: Message?): Boolean {

            val newWebView = WebView(requireContext())

            newWebView.settings.javaScriptEnabled = true

            val dialog = Dialog(requireContext())

            dialog.setContentView(newWebView)

            val params = dialog.window!!.attributes

            params.width = ViewGroup.LayoutParams.MATCH_PARENT
            params.height = ViewGroup.LayoutParams.MATCH_PARENT
            dialog.window!!.attributes = params
            dialog.show()

            newWebView.webChromeClient = object : WebChromeClient() {
                override fun onJsAlert(view: WebView, url: String, message: String, result: JsResult): Boolean {
                    super.onJsAlert(view, url, message, result)
                    return true
                }

                override fun onCloseWindow(window: WebView?) {
                    dialog.dismiss()
                }
            }

            (resultMsg!!.obj as WebView.WebViewTransport).webView = newWebView
            resultMsg.sendToTarget()

            return true
        }
    }

    inner class WebAppInterface(private val mContext: Context) {
        @JavascriptInterface
        fun getAddress(roadAdd: String?) {
            Log.d("TAG", "processDATA: ${roadAdd}")
        }

    }


}