package org.autojs.autojs.ui.main.web

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import androidx.fragment.app.Fragment
import org.autojs.autojs.ui.widget.SwipeRefreshWebView
import org.autojs.autojs.ui.widget.fillMaxSize

class EditorAppManager : Fragment() {

    val swipeRefreshWebView by lazy {
        val context = requireContext()
        SwipeRefreshWebView(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return swipeRefreshWebView.apply {
            loadHomeDocument(this.webView)
            fillMaxSize()
        }
    }

    companion object {
        const val TAG = "EditorAppManager"
        const val DocumentSourceKEY = "DocumentSource"

        private var saveStatus: SharedPreferences? = null

        @Synchronized
        fun getSaveStatus(context: Context): SharedPreferences {
            if (saveStatus == null) {
                saveStatus = context.getSharedPreferences(TAG, Context.MODE_PRIVATE)
            }
            return saveStatus!!
        }

        /**
         * 打开文档
         */
        fun loadHomeDocument(webView: WebView) {
            // 读取保存的 文档源 类型
            val documentSource = try {
                val name = getSaveStatus(webView.context)
                    .getString(DocumentSourceKEY, DocumentSource.DOC_V1.name)

                if (name != null) {
                    DocumentSource.valueOf(name)
                } else {
                    DocumentSource.DOC_V1
                }
            } catch (e: Exception) {
                DocumentSource.DOC_V1
            }
            // 切换到对应的文档源
            switchDocument(webView, documentSource)
        }

        /**
         * 切换文档源
         */
        fun switchDocument(webView: WebView, documentSource: DocumentSource) {
            if (documentSource.isLocal) {
                // 使用 WebViewAssetLoader 加载本地文件
                webView.webViewClient = WebViewClient(webView.context, documentSource.uri)
                webView.loadUrl("https://appassets.androidplatform.net")
            } else {
                // 加载网络地址
                webView.loadUrl(documentSource.uri)
            }
            // 保存 文档源 类型
            getSaveStatus(webView.context).edit()
                .putString(DocumentSourceKEY, documentSource.name)
                .apply()
        }
    }
}