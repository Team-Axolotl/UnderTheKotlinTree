package softwaregroup.com.ease_ui

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.annotation.CallSuper
import android.view.View
import android.view.ViewGroup
import org.intellij.lang.annotations.Identifier
import softwaregroup.com.ease_ui.util.dpToPx
import java.lang.ref.WeakReference

@DslMarker
annotation class ViewDsl

@ViewDsl
abstract class AbstractViewBuilder<Y : View>(context: Context) {
    private val contextRef = WeakReference(context)
    protected fun getContext() = contextRef.get()!!

    var id: Int = -1

    open var widthDp: Int = ViewGroup.LayoutParams.WRAP_CONTENT
    open var heightDp: Int = ViewGroup.LayoutParams.WRAP_CONTENT

    open var background: Drawable? = null

    internal abstract fun createView(): Y

    internal fun build(): Y {
        val view = createView()
        setViewProperties(view)
        return view
    }

    @CallSuper
    internal open fun setViewProperties(view: Y){
        view.id = id
        view.background = background
        view.layoutParams = ViewGroup.LayoutParams(
                if (heightDp < 0) heightDp else dpToPx(heightDp.toFloat(), getContext()),
                if (widthDp < 0) widthDp else dpToPx(widthDp.toFloat(), getContext())
        )
    }
}

fun linearLayout(context: Context, attrs: LinerLayoutBuilder.() -> Unit) = LinerLayoutBuilder(context).apply(attrs).build()
// the duplicate use of the context object is unfortunate, but doesn't make a functional difference

