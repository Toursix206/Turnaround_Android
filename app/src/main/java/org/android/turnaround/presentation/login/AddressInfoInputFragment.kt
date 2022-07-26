package org.android.turnaround.presentation.login

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.Dialog
import android.content.Context
import android.net.http.SslError
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import org.android.turnaround.R
import org.android.turnaround.databinding.FragmentAddressInfoInputBinding
import org.android.turnaround.presentation.base.BaseFragment

@AndroidEntryPoint
class AddressInfoInputFragment :
    BaseFragment<FragmentAddressInfoInputBinding>(R.layout.fragment_address_info_input) {

    private val loginViewModel:LoginViewModel by viewModels()
    //private val handler: Handler by lazy { Handler(Looper.getMainLooper()) }
    val dialog: Dialog by lazy { Dialog(requireContext()) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = loginViewModel
        setListeners()
    }

    private fun setListeners() {
        binding.etAddress.setOnClickListener {
            initAddressWebView()
        }
    }

    private fun initAddressWebView() {
        val newWebView = WebView(requireContext()).apply {
            settings.javaScriptEnabled = true
            settings.javaScriptCanOpenWindowsAutomatically = true
            settings.setSupportMultipleWindows(true)
            settings.domStorageEnabled = true
            addJavascriptInterface(
                WebAppInterface(
                    requireContext()
                ), "Android"
            )

        }
        dialog.setContentView(newWebView)
        val params = dialog.window!!.attributes.apply {
            width = ViewGroup.LayoutParams.MATCH_PARENT
            height = ViewGroup.LayoutParams.MATCH_PARENT
        }

        dialog.window!!.attributes = params

        newWebView.webChromeClient = object : WebChromeClient() {
            override fun onJsAlert(
                view: WebView,
                url: String,
                message: String,
                result: JsResult
            ): Boolean {
                super.onJsAlert(view, url, message, result)
                return true
            }

            override fun onCloseWindow(window: WebView?) {
                dialog.dismiss()
            }

            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            override fun onPermissionRequest(request: PermissionRequest) {
                request.grant(request.resources)
            }

        }
        newWebView.webViewClient = object : WebViewClient() {
            @SuppressLint("WebViewClientOnReceivedSslError")
            override fun onReceivedSslError(
                view: WebView,
                handler: SslErrorHandler,
                error: SslError
            ) {
                // SSL 에러가 발생해도 계속 진행
                handler.proceed()
            }

            override fun onPageFinished(view: WebView, url: String) {
                newWebView.loadUrl("javascript:sample2_execDaumPostcode();")
            }

        }

        newWebView.loadUrl("http://3.39.58.48:8081/address.html")
        dialog.show()

    }

    inner class WebAppInterface(private val mContext: Context) {
        @JavascriptInterface
        fun getAddress(roadAdd: String?) {
            loginViewModel.address.postValue(roadAdd)
            dialog.dismiss()
        }

    }


}