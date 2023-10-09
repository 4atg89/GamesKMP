package com.atg.gameskmp.android

import android.os.Bundle
import com.atg.gameskmp.EnterAndroid
import moe.tlaster.precompose.lifecycle.PreComposeActivity
import moe.tlaster.precompose.lifecycle.setContent

class MainActivity : PreComposeActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { EnterAndroid() }
    }
}
