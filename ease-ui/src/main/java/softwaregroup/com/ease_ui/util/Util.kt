package softwaregroup.com.ease_ui.util

import android.content.Context
import android.util.DisplayMetrics
import android.util.TypedValue
import java.util.*

/**
 * Convert DP (Density-independent Pixels) to a real pixel value.
 * ---
 * Romain Guy himself, blessed be He, gave the formula:
https://stackoverflow.com/questions/5255184/android-and-setting-width-and-height-programmatically-in-dp-units#comment5918126_5255256
 */
fun dpToPx(dp: Float, displayMetrics: DisplayMetrics): Int {
    return (dp * displayMetrics.density + 0.5f).toInt()
}

fun dpToPx(dp: Float, context: Context) = dpToPx(dp, context.resources.displayMetrics)

fun pxToDp(px: Int, displayMetrics: DisplayMetrics): Float {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, px.toFloat(), displayMetrics)
}

fun pxToDp(px: Int, context: Context) = pxToDp(px, context.resources.displayMetrics)

// spare parts //

interface Mappable<VT> {
    fun getKey(): String
    fun getValue(): VT

    fun getEntry() = AbstractMap.SimpleEntry<String, VT>(getKey(), getValue())
    fun getPair() = Pair(getKey(), getValue())
}