package com.softwaregroup.underthekotlintree.ui

import android.app.Activity
import android.os.Bundle
import android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN
import com.softwaregroup.underthekotlintree.util.toast

abstract class BaseActivity: Activity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(FLAG_FULLSCREEN, FLAG_FULLSCREEN)
    }

    override fun onBackPressed() {
        if (isTaskRoot){
            toast("Add a 'double-click to exit' mechanism!")
        }
        super.onBackPressed()
    }

}
