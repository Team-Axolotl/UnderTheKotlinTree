package softwaregroup.com.ease_ui

import android.content.Context
import android.view.View
import android.view.ViewGroup
import softwaregroup.com.ease_ui.util.dpToPx
import java.lang.ref.WeakReference

@DslMarker
annotation class ViewDsl

@ViewDsl
abstract class ViewBuilder<Y : View>(context: Context) {
    private val contextRef = WeakReference(context)
    protected fun getContext() = contextRef.get()!!

    open var widthDp: Int = ViewGroup.LayoutParams.WRAP_CONTENT
    open var heightDp: Int = ViewGroup.LayoutParams.WRAP_CONTENT
    internal abstract fun createView(): Y

    internal abstract fun setViewProperties(view: Y)

    internal fun build(): Y {
        val view = createView()
        setLayoutParams(view)
        setViewProperties(view)
        return view
    }

    protected fun setLayoutParams(view: Y) {
        view.layoutParams = ViewGroup.LayoutParams(
                if (heightDp < 0) heightDp else dpToPx(heightDp.toFloat(), getContext()),
                if (widthDp < 0) widthDp else dpToPx(widthDp.toFloat(), getContext())
        )
    }
}

fun linearLayout(context: Context, attrs: LinerLayoutBuilder.() -> Unit) = LinerLayoutBuilder(context).apply(attrs).build()
// the duplicate use of the context object is unfortunate, but doesn't make a functional difference

