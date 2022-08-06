package org.android.turnaround.presentation.login

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.Dialog
import android.content.Context
import android.net.http.SslError
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import org.android.turnaround.R
import org.android.turnaround.databinding.FragmentAddressInfoInputBinding
import org.android.turnaround.presentation.base.BaseFragment
import org.android.turnaround.presentation.util.EventObserver

@AndroidEntryPoint
class AddressInfoInputFragment :
    BaseFragment<FragmentAddressInfoInputBinding>(R.layout.fragment_address_info_input) {

    private val loginViewModel: LoginViewModel by viewModels()
    val dialog: Dialog by lazy { Dialog(requireContext()) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = loginViewModel
        setListeners()
        initSuccessSetInfo()
    }

    private fun setListeners() {
        binding.etAddress.setOnClickListener {
            initAddressWebView()
        }
        binding.btnStart.setOnClickListener {
            loginViewModel.postUserSet()
        }
    }

    private fun initSuccessSetInfo() {
        loginViewModel.successSetInfo.observe(viewLifecycleOwner, EventObserver {
            if (it) findNavController().navigate(R.id.action_addressInfoInputFragment_to_homeFragment)
        })
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