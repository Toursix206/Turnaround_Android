package org.android.turnaround.presentation.login

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import androidx.fragment.app.DialogFragment
import org.android.turnaround.R
import org.android.turnaround.databinding.FragmentAddressSearchDialogBinding


class AddressSearchDialogFragment : DialogFragment() {

    lateinit var binding : FragmentAddressSearchDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddressSearchDialogBinding.inflate(inflater,container,false)
        return binding.root
    }

    private fun initAddressWebView() {
        binding.wvDaumAddress.settings.apply {
            javaScriptEnabled = true
            javaScriptCanOpenWindowsAutomatically = true
            setSupportMultipleWindows(true)
            domStorageEnabled = true
        }
        binding.wvDaumAddress.addJavascriptInterface(
            WebAppInterface(
                requireContext()
            ),"Android")
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
            view?.loadUrl("javascript:sample2_execDaumPostcode();")
        }

    }

    private val chromeClient = object : WebChromeClient() {
        override fun onPermissionRequest(request: PermissionRequest?) {
            super.onPermissionRequest(request)
        }
    }

    override fun onStart() {
        super.onStart()
        setLayout()
    }

    private fun setLayout() {
        requireNotNull(dialog).apply {
            requireNotNull(window).apply {
                setLayout(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
            }
        }
    }
    inner class WebAppInterface(private val mContext: Context) {
        @JavascriptInterface
        fun getAddress(roadAdd: String?) {
            Log.d("TAG", "processDATA: ${roadAdd}")
        }

    }


}