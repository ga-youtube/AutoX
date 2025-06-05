package org.autojs.phonefarm.ui.main.web

enum class DocumentSource(val sourceName: String, val uri: String, val isLocal: Boolean = false) {
    DOC_V1("在线文档", "https://autox-community.github.io/AutoX_Docs/"),
    //DOC_V1_LOCAL("本地文档", "docs/v1", true)

}