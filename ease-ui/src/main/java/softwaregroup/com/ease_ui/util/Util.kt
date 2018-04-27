package softwaregroup.com.ease_ui.util

import android.content.Context

/**
 * Convert DP (Density-independent Pixels) to a real pixel value.
 * ---
 * Romain Guy himself, blessed be He, gave the formula:
   https://stackoverflow.com/questions/5255184/android-and-setting-width-and-height-programmatically-in-dp-units#comment5918126_5255256
 */
fun dpToPx(dp: Float, context: Context): Int {
    return (dp * context.resources.displayMetrics.density + 0.5f).toInt()
}

// spare parts //

//interface Mappable<VT>{
//    fun getKey() : String
//    fun getValue() : VT
//
//    fun getEntry() = AbstractMap.SimpleEntry<String, VT>(getKey(), getValue())
//    fun getPair() = Pair(getKey(), getValue())
//}