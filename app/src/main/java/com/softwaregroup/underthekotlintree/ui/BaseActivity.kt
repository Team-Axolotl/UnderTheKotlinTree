package com.softwaregroup.underthekotlintree.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN
import android.widget.Toast
import com.softwaregroup.underthekotlintree.R
import com.softwaregroup.underthekotlintree.storage.clearMemStorage

abstract class BaseActivity : AppCompatActivity() {

    private var exitToast: Toast? = null
    private var backPressed: Boolean = false
    private var exitClickHandler: Handler = Handler()

    @SuppressLint("ShowToast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(FLAG_FULLSCREEN, FLAG_FULLSCREEN)

        exitToast = Toast.makeText(this, R.string.message_double_press_to_exit, Toast.LENGTH_SHORT)
    }

    override fun onBackPressed() {
        if (isTaskRoot) {
            if (backPressed) exit()

            exitToast!!.show()
            backPressed = true
            exitClickHandler.postDelayed({ exitToast!!.cancel(); backPressed = false; }, 1500)
        }
    }

    private fun exit() {
        clearMemStorage()
        finish()
    }

}
