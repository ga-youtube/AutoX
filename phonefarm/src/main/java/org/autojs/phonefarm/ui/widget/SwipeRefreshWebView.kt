package org.autojs.phonefarm.ui.widget

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.KeyEvent
import android.webkit.WebSettings
import android.webkit.WebView
import org.autojs.phonefarm.theme.widget.ThemeColorSwipeRefreshLayout

class SwipeRefreshWebView : ThemeColorSwipeRefreshLayout {

    val webView = WebView(context)

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    init {
        isEnabled = false
        webView.apply {
            fillMaxSize()
            setup()
        }
        addView(webView)
    }

    override fun dispatchKeyEvent(event: KeyEvent): Boolean {
        if (event.keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack()
            return true
        }
        return super.dispatchKeyEvent(event)
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun WebView.setup() {
        settings.apply {
            javaScriptEnabled = true  //设置支持Javascript交互
            javaScriptCanOpenWindowsAutomatically = true //支持通过JS打开新窗口
            allowFileAccess = true //设置可以访问文件
            allowFileAccessFromFileURLs = true;
            allowUniversalAccessFromFileURLs = true;
            allowContentAccess = true;
            defaultTextEncodingName = "utf-8"//设置编码格式
            setSupportMultipleWindows(false)
            layoutAlgorithm = WebSettings.LayoutAlgorithm.NORMAL
            setSupportZoom(true) //支持缩放，默认为true。是下面那个的前提。
            builtInZoomControls = true //设置内置的缩放控件。若为false，则该WebView不可缩放
            displayZoomControls = false //设置原生的缩放控件，启用时被leakcanary检测到内存泄露
            useWideViewPort = true //让WebView读取网页设置的viewport，pc版网页
            loadWithOverviewMode = false
            loadsImagesAutomatically = true //设置自动加载图片
            blockNetworkImage = false
            blockNetworkLoads = false;
            setNeedInitialFocus(true);
            saveFormData = true;
            domStorageEnabled = true
            databaseEnabled = true   //开启 database storage API 功能
            pluginState = WebSettings.PluginState.ON
            if (Build.VERSION.SDK_INT >= 26) {
                safeBrowsingEnabled = false
            }
            mediaPlaybackRequiresUserGesture = false;
            // 5.0以上允许加载http和https混合的页面(5.0以下默认允许，5.0+默认禁止)
            mixedContentMode =
                WebSettings.MIXED_CONTENT_ALWAYS_ALLOW;
        }
    }

}
