package softwaregroup.com.ease_ui

import android.content.Context
import android.support.annotation.CallSuper
import android.view.View
import android.view.ViewGroup
import softwaregroup.com.ease_ui.util.dpToPx

@DslMarker
annotation class ViewDsl

@ViewDsl
abstract class ViewBuilder<Y : View> {
    // todo fixme - should the Explicit set of width and height be enforced (like in XML) or keep default values?
    var widthDp = ViewGroup.LayoutParams.WRAP_CONTENT
    var heightDp = ViewGroup.LayoutParams.WRAP_CONTENT

    internal abstract fun createView(context: Context): Y

    protected fun getLayoutParams(context: Context) = ViewGroup.LayoutParams(
            if (heightDp < 0) heightDp else dpToPx(heightDp.toFloat(), context),
            if (widthDp < 0) widthDp else dpToPx(widthDp.toFloat(), context)
    )

    //todo - if implementation tries to set LPs, it'd be a shit-storm to figure out *WHY*
    @CallSuper
    internal open fun build(context: Context) : Y {
        val view = createView(context)
        view.layoutParams = getLayoutParams(context)
        return view
    }
}

fun linearLayout(context: Context, attrs: LinerLayoutBuilder.() -> Unit) = LinerLayoutBuilder(context).apply(attrs).build(context)
// the duplicate use of the context object is unfortunate, but doesn't make a functional difference

