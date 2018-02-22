package com.softwaregroup.underthekotlintree.util

import android.app.Activity
import android.widget.Toast

/**
 * Non-specific utility methods and common-use extension-functions go here.
 * Cheers 😊
 */

enum class ToastDuration(val value: Int) {
    LONG(Toast.LENGTH_LONG), SHORT(Toast.LENGTH_SHORT)
}

/** Show a toast with passed message and length ([Toast.LENGTH_LONG] by default) */
fun Activity.toast(message: String, length: ToastDuration = ToastDuration.LONG) = Toast.makeText(this, message, length.value).show()