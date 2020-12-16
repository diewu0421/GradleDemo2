package com.example.gradledemo

import android.app.Application
import com.example.patch_lib.Hotfix

/**
 * 浙江集商优选电子商务有限公司
 * @author zenglw
 * @date   12/15/20 11:07 PM
 */
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        Hotfix.installPatch(this, "/sdcard/patch.jar")
    }
}