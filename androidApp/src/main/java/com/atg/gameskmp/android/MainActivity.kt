package com.atg.gameskmp.android

import android.os.Bundle
import com.atg.gameskmp.EnterAndroid
import com.atg.gameskmp.appModule
import moe.tlaster.precompose.lifecycle.PreComposeActivity
import moe.tlaster.precompose.lifecycle.setContent
import org.koin.core.context.startKoin

class MainActivity : PreComposeActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startKoin { modules(appModule()) }
        setContent { EnterAndroid() }
    }
}
