package com.softwaregroup.underthekotlintree.util

import android.app.Activity
import android.content.Intent
import android.support.annotation.StringRes
import android.support.design.widget.Snackbar
import android.widget.Toast
import com.softwaregroup.underthekotlintree.model.LoginData
import com.softwaregroup.underthekotlintree.net.HttpCallResponse
import com.softwaregroup.underthekotlintree.net.RpcException

/**
 * Non-specific utility methods and common-use extension-functions go here.
 * Cheers 😊
 */

enum class ToastDuration(val value: Int) {
    LONG(Toast.LENGTH_LONG), SHORT(Toast.LENGTH_SHORT)
}

/** Show a toast with passed message and length ([Toast.LENGTH_LONG] by default) */
@JvmOverloads
fun Activity.toast(message: String, length: ToastDuration = ToastDuration.LONG) = Toast.makeText(this, message, length.value).show()

fun Activity.toast(@StringRes messageResId: Int, length: ToastDuration = ToastDuration.LONG) {
    Toast.makeText(this, getString(messageResId), length.value).show()
}

/** Show error ui elements */
fun Activity.showErrorMessage(message: String) {
    // todo - impl proper
    val snek: Snackbar = Snackbar.make(window.decorView, message, Snackbar.LENGTH_INDEFINITE)
    snek.setAction(android.R.string.ok) { snek.dismiss() }.show()
}

fun Activity.showHttpErrorMessage(response: HttpCallResponse<out Any>) {
    if( response.exception is RpcException )
        showErrorMessage(response.exception.message!!)
    else
        showErrorMessage(getString(response.errorCode?.messageStringId!!))
}

fun Activity.startActivity(clazz: Class<out Activity>) {
    this.startActivity(Intent(this, clazz))
}