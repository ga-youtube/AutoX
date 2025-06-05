package org.autojs.phonefarm.ui.build

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import org.autojs.phonefarm.build.ApkKeyStore
import org.autojs.phonefarm.build.ApkSigner

/**
 * @author wilinz
 * @date 2022/5/23
 */
class SignManageViewModel : ViewModel() {
    val keyStoreList = mutableStateListOf<ApkKeyStore>().apply { addAll(ApkSigner.loadKeyStore()) }

    fun refresh(){
        keyStoreList.apply {
            clear()
            addAll(ApkSigner.loadKeyStore())
        }
    }
}